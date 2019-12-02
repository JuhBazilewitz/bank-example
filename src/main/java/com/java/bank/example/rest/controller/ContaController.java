package com.java.bank.example.rest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "Conta")
public class ContaController {

    @ApiOperation(value = "Hello")
    @GetMapping(value = "/api/hello")
    public String hello(){
        return "Ok";
    }
}
