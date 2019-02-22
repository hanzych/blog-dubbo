package com.yfancy.common.base.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * http工具类
 */
public class HttpClientUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class.getName());

    public static String sendGet(String url) throws IOException {
        LOGGER.info("request url info : {}", url);
        HttpGet request = new HttpGet(url );
        return send(request);
    }

    public static String sendPost(String url, String param) throws IOException {
        LOGGER.info("request url info : {}", url);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);// 创建httpPost
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        String charSet = "UTF-8";
        StringEntity entity = new StringEntity(param, charSet);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = null;

        try {

            response = httpclient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                String jsonString = EntityUtils.toString(responseEntity);
                return jsonString;
            }
            else{
                LOGGER.error("请求返回:"+state+"("+url+")");
            }
        }
        finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static String send(HttpRequestBase request) throws IOException {
        String message = "";
        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) ...");
        request.setHeader("accept", "*/*");
        request.setHeader("connection", "Keep-Alive");
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = httpclient.execute(request);
        HttpEntity entity = response.getEntity();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        if (entity != null) {
            long length = entity.getContentLength();
            if (length != -1 && length < 2048) {
                message = EntityUtils.toString(entity);
            } else {
                InputStream in = entity.getContent();
                byte[] data = new byte[4096];
                int count;
                while ((count = in.read(data, 0, 4096)) != -1) {
                    outStream.write(data, 0, count);
                }
                message = new String(outStream.toByteArray(), "UTF-8");
            }
        }
        LOGGER.info(">>>>>>>>>>>>>>>>>response message info : {}", message);
        return message;
    }


    /**
     * http上传
     * @param url
     * @param urlPath
     * @return
     * @throws Exception
     */
    public static String upload(String url,String urlPath) throws Exception{
        String result = null;
        String fileName = urlPath.substring(urlPath.lastIndexOf("/") + 1);
        // 获取网络图片
        URL mediaUrl = new URL(urlPath);
        HttpURLConnection meidaConn = (HttpURLConnection) mediaUrl.openConnection();
        meidaConn.setDoOutput(true);
        meidaConn.setRequestMethod("GET");
        URL urlObj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
        con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false); // post方式不能使用缓存
        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
        // 设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        // 请求正文信息
        // 第一部分：
        StringBuilder sb = new StringBuilder();
        sb.append("--"); // 必须多两道线
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"media\";filename=\"" + fileName + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");
        byte[] head = sb.toString().getBytes("utf-8");
        // 获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        // 输出表头
        out.write(head);
        // 文件正文部分
        // 把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(meidaConn.getInputStream());
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();
        // 结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
        out.write(foot);
        out.flush();
        out.close();
        meidaConn.disconnect();
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            LOGGER.info("发送POST请求出现异常！ {}", e);
            e.printStackTrace();
            throw new IOException("数据读取异常");
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
//        JSONObject jsonObj = JSON.parseObject(result);
//        LOGGER.info("getMediaId jsonObj: {}", jsonObj);
//        return jsonObj.getString("media_id");
        return result;
    }
}