package com.topsci.qh.webmanagement.Service.Normal;

import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.topsci.qh.webmanagement.Pojo.UploadFiles;
import com.topsci.qh.webmanagement.Resources.BasicService;
import com.topsci.qh.webmanagement.Tools.FileTool;

/**
 * @ClassName: ProtocolService  
 * @Description: TODO 系统协议service
 * @author tgeng
 * @date 2016-12-22 下午1:10:15  
 *
 */
@Service
@SuppressWarnings("unchecked")
public class ProtocolService extends BasicService {
	private Logger log = LoggerFactory.getLogger(ProtocolService.class);
	
    @Resource
    private FileTool fileTool;

    public void saveProtocol(UploadFiles protocol, MultipartFile file, HttpServletRequest request) {
    	protocol.setDocPath(fileTool.saveProtocolDoc(file, file.getOriginalFilename()));
    	protocol.setUuid(UUID.randomUUID().toString().replaceAll("-",""));
    	protocol.setDocName(file.getOriginalFilename());
    	protocol.setUploadTime(LocalDateTime.now());
        /*save(protocol);
        oprRecord.logOpr(protocol, null, "上传协议", request);*/
    }

    public List<UploadFiles> getProtocols() {
       /* String hql =  "from UploadFiles sp where sp.fileType = '"+UploadFiles.Protocol+"' order by sp.uploadTime desc";
        return findByHQL(hql);*/
        return null;
    }

    public UploadFiles getProtocol(String uuid) {
        /*return get(UploadFiles.class, uuid);*/
        return null;
    }

    public void deleteProtocol(String uuid, HttpServletRequest request) {
    	UploadFiles tmp = getProtocol(uuid);
       /* evictObj(tmp);
        execUpdateHQL("delete from UploadFiles sp where sp.uuid = ?",uuid);
        oprRecord.logOpr(null, tmp, "删除协议" + uuid, request);*/
    }

    
    public String checkRepeated(String uuid, String docName) {
    	/*if(StringUtils.isNotEmpty(docName) || StringUtils.isNotEmpty(uuid))
        {
            String hql = "";
            int result;
            if(StringUtils.isEmpty(uuid))
            {
                hql = "from UploadFiles sp where sp.docName = ?";
                result = getCount(hql,docName);
            }
            else {
                hql = "from UploadFiles sp where sp.uuid != ? and sp.docName = ?";
                result = getCount(hql,uuid,docName);
            }

            if(result > 0)
            {
                return "{\"result\":\"false\"}";
            }
            else
            {
                return "{\"result\":\"true\"}";
            }
        }
        return "{\"result\":\"false\"}";*/
        return null;
    }

    public Map<String, Object> getDoc(String uuid) {
        Map<String, Object> result = new HashMap<>();
        UploadFiles protocol = getProtocol(uuid);
        if (protocol == null) {
            return result;
        }
        String filepath = protocol.getDocPath();
        File f = new File(filepath);
        byte[] b = null;
        try {
            b = FileUtils.readFileToByteArray(f);
        } catch (Exception ex) {
            log.warn("读取文件{}错误!{}", filepath, ex);
        }
        result.put("b", b);
        result.put("f", protocol.getDocName());
        return result;
    }
}
