package com.kaylves.jeasy.weixin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.kaylves.jeasy.weixin.utils.httpclient.HttpClientUtils;

public class HttpClientUtilsTest
{
    @Test
    public void fileUploadTest()
    {
        Map<String,File> files = new HashMap<String, File>();
        
        File file = new File("d:/测试图片.jpg");
        
        files.put( "upFileInner", file );
        
        String action = "http://localhost:8080/tgsyl/file/upload.do";
        
        String u;
        try
        {
            u = HttpClientUtils.multipartPost( action , files, null );
            System.out.println("u:"+u);
        } catch ( Exception e )
        {
            e.printStackTrace();
        }
        
        
    }
    
}
