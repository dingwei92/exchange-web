package com.topsci.qh.webmanagement.Tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by lzw on 2017/6/27.
 */
public class SMSTool {
    private static Logger logger = LoggerFactory.getLogger(SMSTool.class);
    private static Config config;

    /**
     * 使用webservice调用短信发送接口
     * @param phsnumber
     * @param smscontent
     * @return
     * @throws Exception
     */
    public static Map<String,String> returnxml(String phsnumber,String smscontent) throws Exception
    {
        /*if(config == null)
        {
            config = Config.getConfig();
        }
        Map<String,String> r = new HashMap<>();
        String result= "";
        Document document = DocumentHelper.createDocument();
        Element rootElement= document.addElement("root");
        rootElement.addElement("authkeycode").setText(config.getSms_authcode());
        rootElement.addElement("phsnumber").setText(phsnumber);
        rootElement.addElement("smscontent").setText(smscontent);
        result = document.asXML();
        try {
            String endpoint = config.getSms_ip();
            Service service = new Service();
            Call call =  service.createCall();
            call.setTargetEndpointAddress( endpoint );
            call.setOperationName(new QName("smsportal", "smsdata") );
            result = (String) call.invoke( new Object[] { result} );
            document = DocumentHelper.parseText(result);
            Element root = document.getRootElement();
            r.put("ResultCode",root.element("ResultCode").getText());
            r.put("ResultMessage",root.element("ResultMessage").getText());
        }
        catch (Exception e) {
            logger.error("发送短信至 "+phsnumber+" 错误，短信内容："+smscontent);
        }*/


        return null ;
    }
}
