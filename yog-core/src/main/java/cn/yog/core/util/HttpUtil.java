package cn.yog.core.util;

import com.alibaba.fastjson.JSON;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Map.Entry;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HTTP请求工具类
 *
 * @author YOG
 * @version:V1.0
 */
public class HttpUtil {

    /**
     * Post请求：Form表单提交
     *
     * @param url
     * @param paramsMap
     * @param headerParams
     * @param charset
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String PostForm(String url, Map<String, String> paramsMap, Map<String, String> headerParams,
                                  String charset) throws ClientProtocolException, IOException {
        HttpClient          httpclient = HttpClients.createDefault();
        HttpPost            httpPost   = new HttpPost(url);
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        // 参数设置
        for (Entry<String, String> entry : paramsMap.entrySet()) {
            NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
            formparams.add(nameValuePair);
        }
        if (headerParams != null) {
            for (Entry<String, String> entry : headerParams.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        httpPost.setEntity(new UrlEncodedFormEntity(formparams, charset));
        HttpResponse response = httpclient.execute(httpPost);
        return EntityUtils.toString(response.getEntity(), charset);
    }

    /**
     * Post请求
     *
     * @param url
     * @param params
     * @param headerParams
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String PostJson(String url, Map<String, Object> params,
                                  Map<String, String> headerParams) throws ClientProtocolException, IOException {
        HttpClient   httpclient = HttpClients.createDefault();
        HttpPost     httpPost   = new HttpPost(url);
        StringEntity entity     = new StringEntity(JSON.toJSONString(params), "UTF-8");
        entity.setContentType("application/json; charset=UTF-8");
        httpPost.setEntity(entity);
        if (headerParams != null) {
            for (Entry<String, String> entry : headerParams.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        httpPost.setHeader("Content-type", "application/json; charset=UTF-8");
        HttpResponse response = httpclient.execute(httpPost);
        return EntityUtils.toString(response.getEntity(), "UTF-8");
    }

    /**
     * Post请求
     *
     * @param url
     * @param json
     * @param headerParams
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String PostJson(String url, String json,
                                  Map<String, String> headerParams) throws ClientProtocolException, IOException {
        HttpClient   httpclient = HttpClients.createDefault();
        HttpPost     httpPost   = new HttpPost(url);
        StringEntity entity     = new StringEntity(json, "UTF-8");
        entity.setContentType("application/json; charset=UTF-8");
        httpPost.setEntity(entity);
        if (headerParams != null) {
            for (Entry<String, String> entry : headerParams.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        httpPost.setHeader("Content-type", "application/json; charset=UTF-8");
        HttpResponse response = httpclient.execute(httpPost);
        return EntityUtils.toString(response.getEntity(), "UTF-8");
    }

    /**
     * Put请求
     *
     * @param url
     * @param json
     * @param headerParams
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String PutJson(String url, String json,
                                 Map<String, String> headerParams) throws ClientProtocolException, IOException {
        HttpClient   httpclient = HttpClients.createDefault();
        HttpPut      httpPut    = new HttpPut(url);
        StringEntity entity     = new StringEntity(json, "UTF-8");
        entity.setContentType("application/json; charset=UTF-8");
        httpPut.setEntity(entity);
        if (headerParams != null) {
            for (Entry<String, String> entry : headerParams.entrySet()) {
                httpPut.setHeader(entry.getKey(), entry.getValue());
            }
        }
        httpPut.setHeader("Content-type", "application/json; charset=UTF-8");
        HttpResponse response = httpclient.execute(httpPut);
        return EntityUtils.toString(response.getEntity(), "UTF-8");
    }

    /**
     * 上传文件请求
     *
     * @param url
     * @param file
     * @param fileName
     * @param headParams
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static String PostFile(String url, String fileName, File file,
                                  Map<String, String> headParams) throws ClientProtocolException, IOException {
        HttpClient             httpclient         = HttpClients.createDefault();
        HttpPost               httpPost           = new HttpPost(url);
        MultipartEntityBuilder multiEntityBuilder = MultipartEntityBuilder.create();
        multiEntityBuilder.addBinaryBody(fileName, file);
        HttpEntity httpEntity = multiEntityBuilder.build();
        httpPost.setEntity(httpEntity);
        if (headParams != null) {
            for (Entry<String, String> entry : headParams.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        HttpResponse response = httpclient.execute(httpPost);
        return EntityUtils.toString(response.getEntity(), "UTF-8");
    }

    /**
     * 上传文件请求
     *
     * @param url
     * @param is
     * @param fileName
     * @param headParams
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static String PostFile(String url, String fileName, InputStream is,
                                  Map<String, String> headParams) throws ClientProtocolException, IOException {
        HttpClient             httpclient         = HttpClients.createDefault();
        HttpPost               httpPost           = new HttpPost(url);
        MultipartEntityBuilder multiEntityBuilder = MultipartEntityBuilder.create();
        multiEntityBuilder.addBinaryBody(fileName, is);
        if (headParams != null) {
            for (Entry<String, String> entry : headParams.entrySet()) {
                multiEntityBuilder.addTextBody(entry.getKey(), entry.getValue());
            }
        }
        HttpEntity httpEntity = multiEntityBuilder.build();
        httpPost.setEntity(httpEntity);
        HttpResponse response = httpclient.execute(httpPost);
        return EntityUtils.toString(response.getEntity(), "UTF-8");
    }

    /**
     * 上传多个文件请求
     *
     * @param url
     * @param fileName
     * @param fileName
     * @param params
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static String PostFile(String url, String fileName, File[] files,
                                  Map<String, String> params) throws ClientProtocolException, IOException {
        HttpClient             httpclient         = HttpClients.createDefault();
        HttpPost               httpPost           = new HttpPost(url);
        MultipartEntityBuilder multiEntityBuilder = MultipartEntityBuilder.create();
        for (File file : files) {
            multiEntityBuilder.addBinaryBody(fileName, file);
        }
        for (Entry<String, String> entry : params.entrySet()) {
            multiEntityBuilder.addTextBody(entry.getKey(), entry.getValue(),
                    ContentType.create("application/json", Consts.UTF_8));
        }
        HttpEntity httpEntity = multiEntityBuilder.build();
        httpPost.setEntity(httpEntity);
        HttpResponse response = httpclient.execute(httpPost);
        return EntityUtils.toString(response.getEntity(), "UTF-8");
    }


    public static String PostFileWithHeader(String url, String fileName, File file, Map<String, String> params,
                                            Map<String, String> headParams) throws ClientProtocolException, IOException {
        HttpClient             httpclient         = HttpClients.createDefault();
        HttpPost               httpPost           = new HttpPost(url);
        MultipartEntityBuilder multiEntityBuilder = MultipartEntityBuilder.create();
        multiEntityBuilder.addBinaryBody(fileName, file);
        HttpEntity httpEntity = multiEntityBuilder.build();
        for (Entry<String, String> entry : headParams.entrySet()) {
            httpPost.setHeader(entry.getKey(), entry.getValue());
        }
        for (Entry<String, String> entry : params.entrySet()) {
            multiEntityBuilder.addTextBody(entry.getKey(), entry.getValue(),
                    ContentType.create("application/json", Consts.UTF_8));
        }
        httpPost.setEntity(httpEntity);
        HttpResponse response = httpclient.execute(httpPost);
        return EntityUtils.toString(response.getEntity(), "UTF-8");
    }

    /**
     * Get请求
     *
     * @param url
     * @param params
     * @param headerParams
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String Get(String url, Map<String, String> params,
                             Map<String, String> headerParams) throws ClientProtocolException, IOException {
        HttpClient    httpclient = HttpClients.createDefault();
        StringBuilder urlBuilder = new StringBuilder(url);
        if (url.indexOf("?") >= 0) {
            urlBuilder.append("&");
        } else {
            urlBuilder.append("?");
        }
        if (params != null) {
            // 创建参数队列
            int index = 1;
            int size  = params.size();
            for (Entry<String, String> entry : params.entrySet()) {
                if (index == size) {
                    urlBuilder.append(entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8"));
                } else {
                    urlBuilder.append(entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8") + "&");
                }
                index++;
            }
        }
        HttpGet httpGet = new HttpGet(urlBuilder.toString());
        if (headerParams != null) {
            for (Entry<String, String> entry : headerParams.entrySet()) {
                httpGet.setHeader(entry.getKey(), entry.getValue());
            }
        }
        HttpResponse response = httpclient.execute(httpGet);
        return EntityUtils.toString(response.getEntity(), "UTF-8");
    }

    /**
     * Get请求
     *
     * @param url
     * @param headerParams
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String Get(String url, Map<String, String> headerParams) throws ClientProtocolException, IOException {
        return Get(url, null, headerParams);
    }

}
