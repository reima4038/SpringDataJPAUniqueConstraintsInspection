package com.example.demo.controller;

import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
public class DemoRestController {

    private final BookService service;

    @Autowired
    public DemoRestController(BookService service) {
        this.service = service;
    }

    @PostMapping(value = "/book", params = "register")
    public void register() {
        service.register("Test data was registered.");
    }

    @PostMapping(value = "/book", params = "update")
    public void update(@PathParam("id") String id) {
        service.update(id,"Test data is updated.");
    }

    @PostMapping(value = "/book", params = "evilUpdate")
    public void evilUpdate(@PathParam("id") String id) {
        service.update(id,"Test data is updated with evil method.");
    }

}
