package com.muping.payroll.service.impl;

import com.muping.payroll.domain.Employee;
import com.muping.payroll.domain.LoginInfo;
import com.muping.payroll.domain.Message;
import com.muping.payroll.domain.VerificationEmailCode;
import com.muping.payroll.mapper.EmployeeMapper;
import com.muping.payroll.mapper.LoginInfoMapper;
import com.muping.payroll.mapper.MessageMapper;
import com.muping.payroll.mapper.VerificationEmailCodeMapper;
import com.muping.payroll.query.EmployeeQueryObject;
import com.muping.payroll.query.PageResult;
import com.muping.payroll.service.IEmployeeService;
import com.muping.payroll.utils.DateUtil;
import com.muping.payroll.utils.MD5;
import com.muping.payroll.utils.MupingConst;
import com.muping.payroll.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.internet.MimeMessage;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private LoginInfoMapper loginInfoMapper;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private VerificationEmailCodeMapper verificationEmailCodeMapper;

    @Override
    public PageResult<Employee> list(EmployeeQueryObject qo) {
        //查询总数
        Long count = employeeMapper.queryCount(qo);

        if (count == 0) {
            return PageResult.getEmpty();
        }

        //查询结果集
        List<Employee> employees = employeeMapper.queryList(qo);
        PageResult<Employee> result = new PageResult<Employee>(qo.getCurPage(), qo.getPageSize(), employees, count.intValue());
        return result;
    }

    @Override
    public void save(Employee employee, Long[] ids1) {
        //创建一个LoginInfo对象
        LoginInfo loginInfo = new LoginInfo();
        //创建用户名
        String s = null;
        while (true) {
            s = UUID.randomUUID().toString().substring(0, 7);
            //查询该用户名是否已经存在
            Long count = loginInfoMapper.queryCountByUserName(s);
            if (count == 0L) {
                break;
            }
        }
        loginInfo.setState(LoginInfo.STATE_NORMAL);
        loginInfo.setUserName(s);
        loginInfo.setUserType(employee.getUserType());
        loginInfo.setPassword(MD5.encode(s));
        loginInfoMapper.insert(loginInfo);
        //保存角色
        employee.setId(loginInfo.getId());
        employee.setUserName(s);
        employee.setState(LoginInfo.STATE_NORMAL);
        //处理工资
        handlerSalary(employee);
        employeeMapper.insert(employee);
        //处理关系
        if (ids1 != null) {
            for (int i = 0; i < ids1.length; i++) {
                employeeMapper.handlerRelationWithRole(employee.getId(), ids1[i]);
            }
        }
        //创建站内信，发送给新用户
        Message message = new Message();
        message.setSend(UserContext.getCurrentUser());
        message.setReceive(loginInfo);
        message.setTitle("绑定邮箱和修改密码通知");
        message.setDate(new Date());
        message.setContent("<p>你好，欢迎使用<font color=\"#ff0000\">PayRoll-工资管理系统</font>！</p><p>" +
                "<img src=\"http://img.t.sinajs.cn/t35/style/images/common/face/ext/normal/0b/tootha_thumb.gif\">，" +
                "我是美美的张含韵。<br></p><p><img style=\"max-width: 100%; width: 503.1px; height: 377.1px;\" " +
                "src=\"http://wx4.sinaimg.cn/mw690/45dfc99dly1fes6c2uvwcj22io1w01l0.jpg\" class=\"\"><br></p><p>" +
                "由于你是第一次使用此账号登录系统。请 尽快进行<a href=\"http://xxxx\" target=\"_blank\" style=\"color: rgb(0, 255, 0);\">" +
                "邮箱绑定</a><font color=\"#000000\">(猛戳我)</font>和<font color=\"#00ff00\"><a href=\"http://xxx\" target=\"_blank\">" +
                "修改密码</a></font>(猛戳我)。以免忘记密码，密码被别人窃取，影响你的财富安全。" +
                "<img src=\"http://img.t.sinajs.cn/t35/style/images/common/face/ext/normal/6e/panda_thumb.gif\"></p><p>。</p><p><br></p>");
        messageMapper.insert(message);
    }

    //处理工资
    private void handlerSalary(Employee employee) {
        if (employee.getUserType() == LoginInfo.USERTYPE_ADMIN) {
            employee.setMonthSalary(MupingConst.INIT_MONTH_ADMIN_SALARY);
        }
        if (employee.getUserType() == LoginInfo.USERTYPE_HOUR) {
            employee.setHourSalary(MupingConst.INIT_HOUR_SALARY);
        }
        if (employee.getUserType() == LoginInfo.USERTYPE_ENTRUST) {
            employee.setMonthSalary(MupingConst.INIT_MONTH_SALARY);
            employee.setEntrustSalary(MupingConst.INIT_ENTRUST_SALARY);
        }
        if (employee.getUserType() == LoginInfo.USERTYPE_SALARY) {
            employee.setMonthSalary(MupingConst.INIT_MONTH_SALARY);
        }
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public Employee selectById(Long id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(Employee employee, Long[] ids1) {
        //查询出数据库完整用户信息
        Employee findEmployee = employeeMapper.selectByPrimaryKey(employee.getId());
        //删除关联属性
        //删除角色
        employeeMapper.deleteRoleRalation(employee.getId());
        findEmployee.setUserType(employee.getUserType());
        //先清空所有工资信息
        findEmployee.setMonthSalary(BigDecimal.ZERO);
        findEmployee.setHourSalary(BigDecimal.ZERO);
        findEmployee.setEntrustSalary(BigDecimal.ZERO);
        //设置工资信息
        handlerSalary(findEmployee);
        employeeMapper.updateByPrimaryKey(findEmployee);
        //更新关联属性
        //处理关系
        if (ids1 != null) {
            for (int i = 0; i < ids1.length; i++) {
                employeeMapper.handlerRelationWithRole(employee.getId(), ids1[i]);
            }
        }
    }

    @Override
    public void changeState(Long id) {
        Employee employee = employeeMapper.selectByPrimaryKey(id);
        employee.setState(LoginInfo.STATE_DELETE);
        employeeMapper.updateByPrimaryKey(employee);
        //获取LoginInfo
        LoginInfo loginInfo = loginInfoMapper.selectByPrimaryKey(id);
        loginInfo.setState(LoginInfo.STATE_DELETE);
        loginInfoMapper.updateByPrimaryKey(loginInfo);
    }

    @Override
    public void bindEmail(String email) {
        VerificationEmailCode code=null;
        //查询此邮箱是否已经被绑定(开发阶段为了简单就不做验证了）
       /* Long count=this.employeeMapper.queryByEmail(email);
        if(count>0L){
            throw new RuntimeException("改邮箱已经被绑定");
        }*/
        //=====================================================
        //发送邮件
        try {
            JavaMailSenderImpl sender = new JavaMailSenderImpl();
            sender.setHost("localhost");
            MimeMessage mimeMessage = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            String uuid = UUID.randomUUID().toString();
            helper.setTo(email);
            helper.setFrom("admin@muping.com");
            helper.setSubject("PayRoll-邮箱绑定验证邮件");
            helper.setText("<html><head></head><body>" +
                    "<p>你好<img src=\"http://img.t.sinajs.cn/t35/style/images/common/face/ext/normal/0b/tootha_thumb.gif\">，" +
                    "PayRoll-工资管理系统邮箱绑定验证邮件，请点击一下连接完成邮箱绑定" +
                    "<a href=\"http://localhost/verificationEmailCode.do?key=" + uuid + "\" target=\"_blank\">" +
                    "http://localhost/verificationEmailCode.do?key=" + uuid + "</a>。</p><p><br></p></body></html", true);
            sender.setUsername("admin");
            sender.setPassword("admin");
            Properties properties = new Properties();
            properties.put("mail.smtp.aut", "true");
            properties.put("mail.smtp.timeou", "25000");
            properties.put("mail.smtp.aut", true);
            sender.setJavaMailProperties(properties);
            sender.send(mimeMessage);
            //创建验证码对象
            code = new VerificationEmailCode();
            code.setKey(uuid);
            code.setEmail(email);
            code.setUser(UserContext.getCurrentUser().getId());
            code.setDate(new Date());
        } catch (Exception e) {
            //邮件发送失败
            throw new RuntimeException(e);
        }
        //发送成功
        this.verificationEmailCodeMapper.insert(code);
    }

    @Override
    public void validateCode(String key) {
        VerificationEmailCode code = this.verificationEmailCodeMapper.queryByKey(key);
        //没有验证码
        if(code==null){
            throw new RuntimeException("状态码失效！");
        }
        Employee employee = this.employeeMapper.selectByPrimaryKey(code.getUser());
        if(employee.getEmailState()==Employee.EMAIL_BIND){
            throw new RuntimeException("该用户已经绑定邮箱！");
        }
        if(!code.getKey().equals(key)){
            throw new RuntimeException("状态码失效！");
        }
        //邮件过期
        if(DateUtil.getSecondsByLast(code.getDate(),new Date())>MupingConst.EMAIL_LOSE){
            throw new RuntimeException("邮件失效，请重新发送！");
        }
        //可以绑定
        employee.setEmailState(Employee.EMAIL_BIND);
        employee.setEmail(code.getEmail());
        this.employeeMapper.updateByPrimaryKey(employee);
    }

    @Override
    public void updateInformation(Long id, String userName, String realName, String phone, int payMethod,String bank,String address) {
        //判断用户名是否已经存在
        LoginInfo info = this.loginInfoMapper.queryByUserNameNotMyself(userName,id);
        if(info!=null){
            throw new RuntimeException("用户名已经存在！请重新输入！");
        }
        Employee employee = this.employeeMapper.selectByPrimaryKey(id);
        if(StringUtils.hasLength(userName)){
            employee.setUserName(userName);
        }
        if(StringUtils.hasLength(realName)){
            employee.setRealName(realName);
        }
        if(StringUtils.hasLength(phone)){
            employee.setPhone(phone);
        }
        if(StringUtils.hasLength(bank)){
            employee.setBank(bank);
        }
        if(StringUtils.hasLength(address)){
            employee.setAddress(address);
        }
        employee.setPayMethod(payMethod);
        employeeMapper.updateByPrimaryKey(employee);
        //获取登录对象
        LoginInfo loginInfo = this.loginInfoMapper.selectByPrimaryKey(id);
        loginInfo.setUserName(userName);
        loginInfoMapper.updateByPrimaryKey(loginInfo);
    }

    @Override
    public void sendUpdatePasswordCode() {
        //查询员工
        Employee employee = employeeMapper.selectByPrimaryKey(UserContext.getCurrentUser().getId());
        String email = employee.getEmail();
        //=====================================================
        //发送邮件
        try {
            JavaMailSenderImpl sender = new JavaMailSenderImpl();
            sender.setHost("localhost");
            MimeMessage mimeMessage = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            String uuid = UUID.randomUUID().toString().substring(0,4);
            helper.setTo(email);
            helper.setFrom("admin@muping.com");
            helper.setSubject("PayRoll-修改密码验证邮件");
            helper.setText("<html><head></head><body><p>你正在修改密码，验证码为"+uuid+",请确认是你操作。如果不是你的操作请忽略！</p></body></html", true);
            sender.setUsername("admin");
            sender.setPassword("admin");
            Properties properties = new Properties();
            properties.put("mail.smtp.aut", "true");
            properties.put("mail.smtp.timeou", "25000");
            properties.put("mail.smtp.aut", true);
            sender.setJavaMailProperties(properties);
            sender.send(mimeMessage);
            //存储验证码
            UserContext.putCode(uuid);
        } catch (Exception e) {
            //邮件发送失败
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePassword(String code, String password) {
        //获取存储的code
        String s = UserContext.getCode();
        //验证码一致
        if(s.equalsIgnoreCase(code)){
            LoginInfo loginInfo = this.loginInfoMapper.selectByPrimaryKey(UserContext.getCurrentUser().getId());
            loginInfo.setPassword(MD5.encode(password));
            loginInfoMapper.updateByPrimaryKey(loginInfo);
            return;
        }
        throw new RuntimeException("验证码不一致");
    }
}
