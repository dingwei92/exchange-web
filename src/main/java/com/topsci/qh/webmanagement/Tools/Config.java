package com.topsci.qh.webmanagement.Tools;

import com.topsci.qh.webmanagement.ExchageWebApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;

/**
 * Created by lzw.
 * 16-7-2
 */
@Component
public class Config {
    private Logger log = LoggerFactory.getLogger(Config.class);
    private Date lastupdate;
    private final int refreshtime = 10;//每10分钟刷新一次
    private static Properties p = null;
    private static Config config;

    private String showCatalog;
    private String tmpPathExcel;
    private String tmpPathDocs;
    private String tmpPicFile;
    private String forget_pass;
    private String init_username;
    private String kfkIp;
    private String sendTopic;
    private String requestTopic;
    private String logShowNum;
    private String sso_id;
    private String sso_auth_path;
    private String sso_password_path;
    private String email_smtp;
    private String email_port;
    private String email_account;
    private String email_user;
    private String email_pwd;
    private String sms_authcode;
    private String sms_ip;

    public Config() {
        refreshProps();
        config = this;
    }

    public static Config getConfig() {
        return config;
    }

    public void loadProperty() {
        try {
            //String path = this.getClass().getClassLoader().getResource("").toURI().getPath() + "config.properties";
            InputStream stream = ExchageWebApplication.class.getClassLoader().getResourceAsStream("config.properties" );
            p.load(stream);
            //p.load(new InputStreamReader(new FileInputStream(path), "UTF-8"));
        } catch (Exception e1) {
            log.warn("读取配置文件错误", e1);
        }
    }

    private void refreshProps() {
        if (lastupdate == null) {
            p = new Properties();
            loadProperty();
            setProps();
            lastupdate = new Date();
        } else {
            int minute = (new Long(new Date().getTime() - lastupdate.getTime()).intValue()) / (60 * 1000);
            if (p == null || minute > refreshtime) {
                p = new Properties();
                loadProperty();
                setProps();
                lastupdate = new Date();
            }
        }
    }

    private void setProps() {
        showCatalog = String.valueOf(p.get("showCatalog"));
        tmpPathExcel = String.valueOf(p.get("tmpPathExcel"));
        tmpPathDocs = String.valueOf(p.get("tmpPathDocs"));
        tmpPicFile = String.valueOf(p.get("tmpPathFile"));
        forget_pass = String.valueOf(p.get("forget_pass"));
        init_username = String.valueOf(p.get("init_username"));
        kfkIp = String.valueOf(p.get("kfkIp"));
        sendTopic = String.valueOf(p.get("sendTopic"));
        requestTopic = String.valueOf(p.get("requestTopic"));
        logShowNum = String.valueOf(p.get("logShowNum"));
        sso_id = String.valueOf(p.get("sso_id"));
        sso_auth_path = String.valueOf(p.get("sso_auth_path"));
        sso_password_path = String.valueOf(p.get("sso_password_path"));
        email_smtp = String.valueOf(p.get("email_smtp"));
        email_port = String.valueOf(p.get("email_port"));
        email_account = String.valueOf(p.get("email_account"));
        email_user = String.valueOf(p.get("email_user"));
        email_pwd = String.valueOf(p.get("email_pwd"));
        sms_authcode = String.valueOf(p.get("sms_authcode"));
        sms_ip = String.valueOf(p.get("sms_ip"));
    }

    public String getTmpPicFile() {
        return tmpPicFile;
    }

    public String getShowCatalog() {
        refreshProps();
        return showCatalog;
    }

    public String getTmpPathExcel() {
        refreshProps();
        return tmpPathExcel;
    }

    public String getTmpPathDocs() {
        refreshProps();
        return tmpPathDocs;
    }

    public String getForget_pass() {
        refreshProps();
        return forget_pass;
    }

    public String getInit_username() {
        refreshProps();
        return init_username;
    }

    public String getKfkIp() {
        refreshProps();
        return kfkIp;
    }

    public String getSendTopic() {
        refreshProps();
        return sendTopic;
    }

    public String getRequestTopic() {
        refreshProps();
        return requestTopic;
    }

    public String getLogShowNum() {
        refreshProps();
        return logShowNum;
    }

    public String getSso_id() {
        refreshProps();
        return sso_id;
    }

    public String getSso_auth_path() {
        refreshProps();
        return sso_auth_path;
    }

    public String getSso_password_path() {
        refreshProps();
        return sso_password_path;
    }

    public String getEmail_smtp() {
        return email_smtp;
    }

    public String getEmail_port() {
        return email_port;
    }

    public String getEmail_account() {
        return email_account;
    }

    public String getEmail_user() {
        return email_user;
    }

    public String getEmail_pwd() {
        return email_pwd;
    }

    public String getSms_authcode() {
        return sms_authcode;
    }

    public String getSms_ip() {
        return sms_ip;
    }
}