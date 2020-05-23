package com.dreams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication   // 该注解用户表示这是一个 SpringBoot 项目
public class HelloWorldMain
{
    public static void main(String[] args)
    {
        SpringApplication.run(HelloWorldMain.class,args);
    }
}
