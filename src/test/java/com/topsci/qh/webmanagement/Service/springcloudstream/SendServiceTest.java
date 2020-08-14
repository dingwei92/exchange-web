package com.topsci.qh.webmanagement.Service.springcloudstream;


import com.topsci.qh.webmanagement.ExchageWebApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: dingwei
 * @Date: 2020/07/03/15:56
 * @Description:
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExchageWebApplication.class)

public class SendServiceTest {
    @Autowired
    private SendService sendService;
    @Test
    public void send() {
        String msg = "hello 撒旦stream ...";
        sendService.send(msg);
    }

    @Test
    public void dynamicSend() {

    }
}