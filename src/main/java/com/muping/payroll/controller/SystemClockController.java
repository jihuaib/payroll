package com.muping.payroll.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SystemClockController {

    @RequestMapping("systemClock")
    public String systemClock(){
        return "systemClock/info";
    }
}
