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
    public String test() {
        return testService.testJtaAtomikos();
    }

    @ResponseBody
    @RequestMapping("/test1")
    public String test1(){
        return testService.test1();
    }

    @ResponseBody
    @RequestMapping("/test2")
    public String test2(){
        return testService.test2();
    }

    @ResponseBody
    @RequestMapping("/query")
    public String query() {
        return testService.query();
    }
}
