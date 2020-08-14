package com.topsci.qh.webmanagement.Tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lzw.
 * 16-7-27
 */
@Component
public class FileTool {

    @Resource
    private Config config;
    private String CATALOGDOCPATH;
    private String PROTOCOLDOCPATH;
    private String PICFILEPATH;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    private Logger log = LoggerFactory.getLogger(FileTool.class);

    public FileTool()
    {
        if(config == null)
        {
            config = new Config();
        }
        CATALOGDOCPATH = config.getTmpPathDocs();
        File f = new File(CATALOGDOCPATH);
        if(!f.exists())
        {
            if(!f.mkdirs())
            {
                log.error("创建目录{}失败!",CATALOGDOCPATH);
            }
        }
        PROTOCOLDOCPATH = config.getTmpPathExcel();
        File pf = new File(PROTOCOLDOCPATH);
        if(!pf.exists())
        {
            if(!pf.mkdirs())
            {
                log.error("创建目录{}失败!",PROTOCOLDOCPATH);
            }
        }
        PICFILEPATH = config.getTmpPicFile();
        pf = new File(PICFILEPATH);
        if(!pf.exists())
        {
            if(!pf.mkdirs())
            {
                log.error("创建目录{}失败!",PICFILEPATH);
            }
        }
    }

    public String saveCatalogDoc(InputStream is,String storename)
    {
        String path = "";
            try
            {
                path = CATALOGDOCPATH + sdf.format(new Date())+"_"+storename;
                File f = new File(path);
                f.createNewFile();
                FileOutputStream os = new FileOutputStream(path);
                int b = 0;
                while((b = is.read()) != -1)
                {
                    os.write(b);
                }
                os.flush();
                is.close();
                os.close();
            }
            catch (Exception ex)
            {
                log.error("保存文件{}出错！",storename,ex);
            }
        return path;
    }
    
    public String saveProtocolDoc(MultipartFile file,String storename)
    {
        String path = "";
        if(!file.isEmpty())
        {
            try
            {
                path = PROTOCOLDOCPATH + sdf.format(new Date())+"_"+storename;
                File f = new File(path);
                f.createNewFile();
                FileOutputStream os = new FileOutputStream(path);
                InputStream is = file.getInputStream();
                int b = 0;
                while((b = is.read()) != -1)
                {
                    os.write(b);
                }
                os.flush();
                is.close();
                os.close();
            }
            catch (Exception ex)
            {
                log.error("保存文件{}出错！",file.getOriginalFilename(),ex);
            }
        }
        return path;
    }

    public String savePicFile(MultipartFile file,String storename,String uuid)
    {
        String path = "";
        if(!file.isEmpty())
        {
            try
            {
                path = PICFILEPATH + uuid + ".tmp";
                File f = new File(path);
                f.createNewFile();
                FileOutputStream os = new FileOutputStream(path);
                InputStream is = file.getInputStream();
                int b = 0;
                while((b = is.read()) != -1)
                {
                    os.write(b);
                }
                os.flush();
                is.close();
                os.close();
                f.renameTo(new File(PICFILEPATH + uuid));
            }
            catch (Exception ex)
            {
                log.error("保存文件{}出错！",file.getOriginalFilename(),ex);
            }
        }
        return path;
    }
}
