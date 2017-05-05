package com.github.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * test controller
 */
@Controller
@RequestMapping("test")
public class HelloController {
    /**
     * test controller
     * @param name
     * @return
     */
    @RequestMapping(value = "/hello")
    @ResponseBody
    public String hello(@RequestParam String name) {
        String response = "hello\t" + name;
        return response;
    }
}
