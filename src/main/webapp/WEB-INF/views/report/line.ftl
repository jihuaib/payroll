<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">

    <link rel="stylesheet" href="/js/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/mp_common.css">
    <script type="text/javascript" src="/js/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="/js/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/Highcharts-4.0.4/js/highcharts.js"></script>

    <script type="text/javascript">

        $(function () {
            $('#container').highcharts({
                title: {
                    text: '员工工资线性图',
                    x: -20
                },
                subtitle: {
                    text: 'salary',
                    x: -20
                },
                xAxis: {
                    categories: ${titles}
                },
                yAxis: {
                    title: {
                        text: 'RMB(元)'
                    },
                    plotLines: [{
                        value: 0,
                        width: 1,
                        color: '#808080'
                    }]
                },
                tooltip: {
                    valueSuffix: '元'
                },
                legend: {
                    layout: 'vertical',
                    align: 'right',
                    verticalAlign: 'middle',
                    borderWidth: 0
                },
                series: [{
                    name: '工资',
                    data: ${salarys}
                }]
            });
        });


        $(function () {
            $("#form_pic #pic").change(function () {
                var picVal = $("#pic option:selected").val();
               if(picVal=="线形图"){
                   window.location.href="/line.do";
               }
                if(picVal=="柱状图"){
                    window.location.href="/column.do";
                }
                if(picVal=="饼图"){

                }
            });
        });

    </script>
</head>
<body>
<div>
    <form method="POST" id="form_pic">
        <div class="row">
            <select id="pic" style="width: 200px;float: right;margin-right: 30px;margin-top: 20px;" class="form-control">
                <option>线形图</option>
                <option>柱状图</option>
                <option>饼图</option>
            </select>
        </div>
    </form>
</div>
<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>

</body>
</html>

