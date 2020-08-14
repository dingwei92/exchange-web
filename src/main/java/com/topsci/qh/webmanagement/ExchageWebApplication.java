package com.topsci.qh.webmanagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan({"com.topsci"})
@ServletComponentScan
public class ExchageWebApplication {
    private static final Logger logger = LoggerFactory.getLogger(ExchageWebApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(ExchageWebApplication.class, args);
        logger.info("====启动服务开始====");
    }

}
