package com.kaylves.jeasy.weixin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import net.sf.json.JSONObject;

import org.junit.Test;

import com.kaylves.jeasy.weixin.api.MaterialApi;
import com.kaylves.jeasy.weixin.entity.MediaFileType;
import com.kaylves.jeasy.weixin.entity.MediaNews;
import com.kaylves.jeasy.weixin.entity.MediaResultList;
import com.kaylves.jeasy.weixin.entity.MediaUploadResult;
import com.kaylves.jeasy.weixin.entity.MediaUsedInfo;
import com.kaylves.jeasy.weixin.exception.WeiXinException;

/**
 * <一句话功能简述>
 * <p><功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2015年5月22日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MaterialApiTest extends BaseBean
{
    
    @Test
    public void createTempMaterialTest(){
        try
        {
            File newFile = new File("d:/测试图片.jpg");
            System.out.println(newFile.length());
            MediaUploadResult result =  MaterialApi.createTempMaterial( MediaFileType.image, newFile, token_key );
            Assert.assertNotNull( result );
        } catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    @Test
    public void getTempMaterialTest(){
        String result =  MaterialApi.getTempMaterial( "quIRknyyA0tF2hXpiQLH8tB3pNnorm43Um9XJmWQO2W5IwEqb8AKtz2dCHY2k6rZ", token_key );
        Assert.assertNotNull( result );
    }
    
    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年5月22日 上午11:33:05 [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @Test
    public void createForeverMaterialTest(){
        File newFile = new File("g:/视频1.mp4");
        MediaUploadResult result =  MaterialApi.createForeverMaterial( MediaFileType.video, newFile,"标题","描述描述啊", token_key );
        Assert.assertNotNull( result );
    }
    
    @Test
    public void createForeverImageMaterialTest(){
        
        File newFile = new File("d:/测试图片.jpg");
        
        MediaUploadResult result =  MaterialApi.createForeverMaterial( MediaFileType.image, newFile, null, null, token_key );
        Assert.assertNotNull( result );
    }
    
    @Test
    public void createForeverImageTextMaterialTest(){
        MediaNews m1 = new MediaNews();
        m1.setAuthor( "ycii" );
        m1.setDigest( "摘要" );
        m1.setThumb_media_id( "JqiIDsmGnS6e-rV_MzIYxUDrU6_3EmqJYzffDgj1ZOw" );
        m1.setContent( "这是内容" );
        m1.setTitle( "标题" );
        m1.setContent_source_url(null );
        List<MediaNews> articles = new ArrayList<MediaNews>();
        articles.add( m1 );
        MediaUploadResult result =  MaterialApi.createForeverImageTextMaterial(articles, token_key );
        Assert.assertNotNull( result );
    }
    
    @Test
    public void deleteForeverMaterialTest()
    {
        try
        {
            MaterialApi.deleteForeverMaterial( "cd-kFVlL16MXye81yCql15p7EVX-SF7kqkW_JlPouQw", token_key );
        } catch ( WeiXinException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Test
    public void getMediaUsedInfoTest(){
        MediaUsedInfo result =  MaterialApi.getMediaUsedInfo( token_key );
        Assert.assertNotNull( result );
    }
    
    
    @Test
    public void getMediaListTest(){
        MediaResultList result =  MaterialApi.getMediaList( MediaFileType.image,0,1,token_key );
        System.out.println("result:"+JSONObject.fromObject( result ));
    }
}
