package com.example.demo.controller;

import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

    private final BookService service;

    @Autowired
    public DemoController(BookService service) {
        this.service = service;
    }

    @GetMapping("/top")
    public String top() {
        return "top";
    }
}
