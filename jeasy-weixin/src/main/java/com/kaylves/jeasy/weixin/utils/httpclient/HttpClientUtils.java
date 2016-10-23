
package com.kaylves.jeasy.weixin.utils.httpclient;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author kaylves
 * @version [版本号, 2015年5月13日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public final class HttpClientUtils
{

    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger( HttpClientUtils.class );


    private static final String UTF = "UTF-8";

    private static PoolingHttpClientConnectionManager  pccm;
    
    //请求器的配置
    private static RequestConfig requestConfig;
    
    //连接超时时间，默认10秒
    private static int socketTimeout = 10000;

    //传输超时时间，默认30秒
    private static int connectTimeout = 30000;

    static
    {
        
        // 多连接的线程安全的管理器
        pccm =new PoolingHttpClientConnectionManager();
        // 每个主机的最大并行链接数
        pccm.setDefaultMaxPerRoute( 200 );
        pccm.setMaxTotal( 600 );
        
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
    
    }

    /**
     * <获取HttpClient>
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年8月28日 上午10:40:04
     * @return [参数说明]
     * 
     * @return CloseableHttpClient [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static CloseableHttpClient   getHttpClient(){
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(pccm)
                .build();
        return httpClient;
    }
    
    
    
    
    /**
     * @author Kaylves
     * @time 2014-6-20 下午04:57:56
     * @param actionUrl
     * @param params
     * @return String
     * @description
     * @version 1.0
     */
    public static String post( String actionUrl, Map<String, String> params )
    {
        
        CloseableHttpClient httpClient = getHttpClient();
        
        HttpPost httpPost = new HttpPost( actionUrl );
        
        httpPost.setConfig(requestConfig);

        List<NameValuePair> list = new ArrayList<NameValuePair>();
        for ( Map.Entry<String, String> entry : params.entrySet() )
        { // 构建表单字段内容
            String key = entry.getKey();
            String value = entry.getValue();
            list.add( new BasicNameValuePair( key, value ) );
        }


        HttpResponse httpResponse;

        String responseString = "";

        logger.warn( "传入后台的URL：" + actionUrl );
        logger.warn( "传入后台的参数：" + list );

        try
        {
            httpPost.setEntity( new UrlEncodedFormEntity( list, UTF ) );
            
            httpResponse = httpClient.execute( httpPost );
            
            if ( httpResponse.getStatusLine().getStatusCode() == 200 )
            {
                responseString = EntityUtils
                        .toString( httpResponse.getEntity() );
                return responseString;
            } else if ( httpResponse.getStatusLine().getStatusCode() == 404 )
            {
                logger.warn( "actionUrl:{} not found 404!" + actionUrl );
            }
        } catch ( Exception e )
        {
            throw new RuntimeException( e );
        } finally
        {
            httpPost.releaseConnection();
        }
        return null;
    }

    public static String postJSON( String actionUrl, String data )
    {
        CloseableHttpClient httpClient = getHttpClient();

        
        HttpPost httpPost = new HttpPost( actionUrl );

        httpPost.setConfig(requestConfig);


        StringEntity myEntity = new StringEntity( data,
                ContentType.APPLICATION_JSON );// 构造请求数据
        httpPost.setEntity( myEntity );// 设置请求体

        HttpResponse httpResponse;

        String responseString = "";

        logger.warn( "接口URL:" + actionUrl );
        logger.warn( "接口参数:" + data );

        try
        {
            httpResponse = httpClient.execute( httpPost );
            if ( httpResponse.getStatusLine().getStatusCode() == 200 )
            {
                responseString = EntityUtils.toString(
                        httpResponse.getEntity(), UTF );
                logger.warn( "responseString：" + responseString );
                return responseString;
            } else if ( httpResponse.getStatusLine().getStatusCode() == 404 )
            {
                logger.warn( "actionUrl:{} not found 404!" + actionUrl );
            }
        } catch ( Exception e )
        {
            throw new RuntimeException( e );
        } finally
        {
            httpPost.releaseConnection();
        }
        return null;
    }
    
    /**
     * 多类型请求
     **/
    public static String multipartPost( String actionUrl, Map<String,File> files,Map<String,String> params )
    {
        
        CloseableHttpClient httpClient= getHttpClient();

        HttpPost httpPost = new HttpPost( actionUrl );
        httpPost.setConfig(requestConfig);
        try
        {
            
            MultipartEntityBuilder entity = MultipartEntityBuilder.create();
            entity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE); 
            
            ContentType contentType= ContentType.create("text/plain", "UTF-8");
            
            // 发送的数据
            if( params!=null )
            {
                for ( Map.Entry<String, String> entry : params.entrySet() )
                { // 构建表单字段内容
                    entity.addPart(entry.getKey(),new StringBody(entry.getValue(),contentType));
                }
            } 
            
            //文件请求
            for ( Map.Entry<String, File> entry : files.entrySet() )
            {
                // 构建表单字段内容
                //entity.addPart(entry.getKey(), new FileBody(entry.getValue()));
                entity.addPart(entry.getKey(), new FileBody(entry.getValue(),ContentType.APPLICATION_OCTET_STREAM,entry.getValue().getName()));
            }
            
            httpPost.setEntity( entity.build() );// 设置请求体
            
            
            HttpResponse httpResponse;
            
            String responseString = "";
            
            logger.warn( "接口URL:" + actionUrl );
            logger.warn( "接口参数:" + params );
            
            httpResponse = httpClient.execute( httpPost );
            
            if ( httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK )
            {
                responseString = EntityUtils.toString(httpResponse.getEntity(), UTF );
                logger.warn( "responseString：" + responseString );
                return responseString;
            } else if ( httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND )
            {
                logger.warn( "actionUrl:{} not found 404!" + actionUrl );
            }
        } catch ( Exception e )
        {
            throw new RuntimeException( e );
        } finally
        {
            httpPost.releaseConnection();
        }
        return null;
    }

    public static String postWithHeader( String data,
            Map<String, String> header, String actionUrl ) throws Exception
    {
        InputStream in = null;
        java.io.BufferedReader breader = null;
        try
        {
            URL url = new URL( actionUrl );
            HttpURLConnection connection = (HttpURLConnection)url
                    .openConnection();
            connection.setRequestProperty( "Connection", "Keep-Alive" );
            connection.setRequestProperty( "Cache-Control", "no-cache" );
            connection.setRequestProperty( "Accept", "*/*" );
            connection.setRequestProperty( "User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)" );
            connection.setRequestProperty( "Accept-Language", "zh-cn" );
            connection.setRequestProperty( "Accept-Encoding", "gzip, deflate" );
            connection.setRequestProperty( "Content-Length",
                    String.valueOf( data.length() ) );
            for ( Map.Entry<String, String> entry : header.entrySet() )
            {
                connection
                        .setRequestProperty( entry.getKey(), entry.getValue() );
            }
            connection.setConnectTimeout( 5000 );
            connection.setDoOutput( true );
            connection.connect();
            connection.getOutputStream().write( data.getBytes() );
            if ( connection.getResponseCode() == 200 )
            {
                in = connection.getInputStream();
                breader = new BufferedReader( new InputStreamReader( in,
                        "UTF-8" ) );
                String str = breader.readLine();
                StringBuffer sb = new StringBuffer();
                while ( str != null )
                {
                    sb.append( str );
                    str = breader.readLine();
                }
                return sb.toString();
            }

        } catch ( Exception e )
        {
            e.printStackTrace();
            throw e;
        } finally
        {
            if ( null != breader )
            {
                breader.close();
            }
            if ( null != in )
            {
                in.close();
            }
        }
        return "";
    }

    public static String getWithHeader( String data,
            Map<String, String> header, String actionUrl ) throws Exception
    {
        InputStream in = null;
        java.io.BufferedReader breader = null;
        try
        {
            URL url = new URL( actionUrl );
            HttpURLConnection connection = (HttpURLConnection)url
                    .openConnection();
            connection.setRequestProperty( "Connection", "Keep-Alive" );
            connection.setRequestProperty( "Cache-Control", "no-cache" );
            connection.setRequestProperty( "Accept", "*/*" );
            connection.setRequestProperty( "User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)" );
            connection.setRequestProperty( "Accept-Language", "zh-cn" );
            connection.setRequestProperty( "Accept-Encoding", "gzip, deflate" );
            connection.setRequestProperty( "Content-Length",
                    String.valueOf( data.length() ) );

            for ( Map.Entry<String, String> entry : header.entrySet() )
            {
                connection
                        .setRequestProperty( entry.getKey(), entry.getValue() );
            }
            connection.setRequestMethod( "GET" );
            connection.setConnectTimeout( 5000 );
            connection.setDoOutput( true );
            connection.connect();
            connection.getOutputStream().write( data.getBytes() );
            if ( connection.getResponseCode() == 200 )
            {
                in = connection.getInputStream();
                breader = new BufferedReader( new InputStreamReader( in,
                        "UTF-8" ) );
                String str = breader.readLine();
                StringBuffer sb = new StringBuffer();
                while ( str != null )
                {
                    sb.append( str );
                    str = breader.readLine();
                }
                return sb.toString();
            }

        } catch ( Exception e )
        {
            e.printStackTrace();
            throw e;
        } finally
        {
            if ( null != breader )
            {
                breader.close();
            }
            if ( null != in )
            {
                in.close();
            }
        }
        return "";
    }

    public static String get( String url, String model,
            Map<String, String> params )
    {
        
        CloseableHttpClient httpClient = getHttpClient();
        
        HttpGet httpGet = new HttpGet( url );
        httpGet.setConfig( requestConfig );
        
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        for ( Map.Entry<String, String> entry : params.entrySet() )
        { // 构建表单字段内容
            list.add( new BasicNameValuePair( entry.getKey(), entry.getValue() ) );
        }
        logger.warn( "传入后台的参数" + list );
        httpGet.setHeader( "User-Agent", model );
        try
        {
            
            HttpResponse httpResponse = httpClient.execute( httpGet );
            if ( httpResponse.getStatusLine().getStatusCode() == 200 )
            {
                return EntityUtils.toString( httpResponse.getEntity(), UTF );
            } else if ( httpResponse.getStatusLine().getStatusCode() == 404 )
            {
                logger.warn( "actionUrl:{} not found 404!" + url );
            }
        } catch ( Exception e )
        {
            throw new RuntimeException( e );
        } finally
        {
            httpGet.releaseConnection();
        }
        return null;
    }

    /**
     * <一句话功能简述> <功能详细描述>
     * 
     * @author kaylves
     * @time 2015年5月13日 上午9:46:08
     * @param url
     *            url传过来时不需要指定?符号
     * @param params
     * @return [参数说明]
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String get( String url, Map<String, String> params )
    {
        CloseableHttpClient httpClient = getHttpClient();

        StringBuffer actionUrl = new StringBuffer( url );
        actionUrl.append( "?" );

        for ( Map.Entry<String, String> entry : params.entrySet() )
        { // 构建表单字段内容
            actionUrl.append( entry.getKey() ).append( "=" )
                    .append( entry.getValue() ).append( "&" );
        }

        if ( params.size() > 1 )
        {
            actionUrl = actionUrl.replace( actionUrl.length() - 1,
                    actionUrl.length(), "" );
        }

        logger.warn( "actionUrl:" + actionUrl.toString() );
        HttpGet httpGet = new HttpGet( actionUrl.toString() );

        try
        {
            HttpResponse httpResponse = httpClient.execute( httpGet );
            if ( httpResponse.getStatusLine().getStatusCode() == 200 )
            {
                String result = EntityUtils.toString( httpResponse.getEntity(),
                        UTF );
                logger.warn( "result:" + result );
                return result;
            } else if ( httpResponse.getStatusLine().getStatusCode() == 404 )
            {
                logger.warn( "actionUrl:{} not found 404!" + url );
            }
        } catch ( Exception e )
        {
            throw new RuntimeException( e );
        } finally
        {
            httpGet.releaseConnection();
        }
        return null;
    }

    public static InputStream  getInputStream( String url, Map<String, String> params )
    {
        CloseableHttpClient httpClient = getHttpClient();
        
        
        StringBuffer actionUrl = new StringBuffer( url );
        actionUrl.append( "?" );

        for ( Map.Entry<String, String> entry : params.entrySet() )
        { // 构建表单字段内容
            actionUrl.append( entry.getKey() ).append( "=" )
                    .append( entry.getValue() ).append( "&" );
        }

        if ( params.size() > 1 )
        {
            actionUrl = actionUrl.replace( actionUrl.length() - 1,
                    actionUrl.length(), "" );
        }

        logger.warn( "actionUrl:" + actionUrl.toString() );
        HttpGet httpGet = new HttpGet( actionUrl.toString() );

        try
        {
            HttpResponse httpResponse = httpClient.execute( httpGet );
            if ( httpResponse.getStatusLine().getStatusCode() == 200 )
            {
            } else if ( httpResponse.getStatusLine().getStatusCode() == 404 )
            {
                logger.warn( "actionUrl:{} not found 404!" + url );
            }
        } catch ( Exception e )
        {
            throw new RuntimeException( e );
        } finally
        {
            httpGet.releaseConnection();
        }
        return null;
    }
    
    public static String get( String url )
    {
        
        CloseableHttpClient httpClient = getHttpClient();

        
        HttpGet httpGet = new HttpGet( url );
        try
        {
            
            HttpResponse httpResponse = httpClient.execute( httpGet );
            
            if ( httpResponse.getStatusLine().getStatusCode() == 200 )
            {
                return EntityUtils.toString( httpResponse.getEntity() );
            } else if ( httpResponse.getStatusLine().getStatusCode() == 404 )
            {
                logger.warn( "actionUrl:{} not found 404!" + url );
            }
        } catch ( Exception e )
        {
            throw new RuntimeException( e );
        } finally
        {
            httpGet.releaseConnection();
        }
        return null;
    }
    
    public static void main( String[] args )
    {
        String s = get( "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx4c088c26ebb49b3d&secret=9f2dc6188466636bf00e3e369196c53a" );
        System.out.println( s );
    }
}
