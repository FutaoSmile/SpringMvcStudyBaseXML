package com.futao.springmvcdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author futao
 * Created on 2019-03-15.
 */
@RestController
@RequestMapping("hello")
public class HelloController {

    @GetMapping("say")
    public String say(@RequestParam("content") String content) {
        return content;
    }
}
