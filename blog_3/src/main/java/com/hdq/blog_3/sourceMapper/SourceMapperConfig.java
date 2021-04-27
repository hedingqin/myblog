package com.hdq.blog_3.sourceMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//配置文件访问路径
@Configuration
public class SourceMapperConfig implements WebMvcConfigurer {

    private String fileSavePath = "E:/picture/";
//      String fileSavePath="/opt/findBugWeb/picture/";
    /**
     * 配置资源映射
     * 意思是：如果访问的资源路径是以“/images/”开头的，
     * 就给我映射到本机的“E:/images/”这个文件夹内，去找你要的资源
     * 注意：E:/images/ 后面的 “/”一定要带上
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/opt/findBugWeb/picture/**").addResourceLocations("file:"+fileSavePath);
        registry.addResourceHandler("/picture/**").addResourceLocations("file:"+fileSavePath);
    }
}
