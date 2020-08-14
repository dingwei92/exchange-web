package com.topsci.qh.webmanagement.Pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: dingwei
 * @Date: 2020/07/10/18:35
 * @Description:
 **/
@Component
@ConfigurationProperties(prefix="myProps") //接收application.yml中的myProps下面的属性
public class MyProps {
    private String kafkaPath;

    public String getKafkaPath() {
        return kafkaPath;
    }

    public void setKafkaPath(String kafkaPath) {
        this.kafkaPath = kafkaPath;
    }
}