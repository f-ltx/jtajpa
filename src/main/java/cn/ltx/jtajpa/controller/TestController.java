package cn.ltx.jtajpa.controller;

import cn.ltx.jtajpa.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @Autowired
    private TestService testService;

    @ResponseBody
    @RequestMapping("/test")
    public String test(){
        testService.testJtaAtomikos();
        return "";
    }
}
