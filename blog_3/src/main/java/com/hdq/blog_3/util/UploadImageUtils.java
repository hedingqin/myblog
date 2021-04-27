package com.hdq.blog_3.util;


import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class UploadImageUtils {


//    文件上传
    public static String upload(MultipartFile file){
        if(file.isEmpty()){
            return "";
        }
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUID.randomUUID().toString().replaceAll("-","") + suffixName;
//        windows下
        String filePath="E:/picture/";
        //centos下
//        String filePath="/opt/findBugWeb/picture/";
        File dest = new File(filePath+fileName);
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdirs();
        }
        try{
            file.transferTo(dest);
        }catch (IOException e){
            e.printStackTrace();
        }
        return filePath.substring(filePath.indexOf("/"))+fileName;
    }
}
