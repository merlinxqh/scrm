package com.hiveview.base.util.http;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * Created with IntelliJ IDEA.
 * User: mikejiang
 * Date: 2017/8/7
 * Time: 下午6:01
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class OkHttp3Utils {

    private static Logger logger = LoggerFactory.getLogger(OkHttp3Utils.class);
    /**
     * 全局
     */
    private static OkHttp3Utils mInstance;

    private OkHttpClient mOkHttpClient;


    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");


    private OkHttp3Utils() {
        super();
        OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder();//.addInterceptor(new OkHttpLogInterceptor());

        clientBuilder.connectTimeout(15, TimeUnit.SECONDS);
        clientBuilder.readTimeout(15, TimeUnit.SECONDS);
        clientBuilder.writeTimeout(15, TimeUnit.SECONDS);
        mOkHttpClient = clientBuilder.build();
    }


    /**
     * 单例模式  获取OkHttp实例
     *
     * @return
     */
    public static OkHttp3Utils getInstance() {
        if (mInstance == null) {
            synchronized (OkHttp3Utils.class) {
                if (mInstance == null) {
                    mInstance = new OkHttp3Utils();
                }
            }
        }
        return mInstance;
    }

    /**
     * 设置请求头
     *
     * @param headersParams
     * @return
     */
    private Headers SetHeaders(Map<String, String> headersParams) {
        Headers headers = null;
        Headers.Builder headersbuilder = new Headers.Builder();
        if (headersParams != null) {
            Iterator<String> iterator = headersParams.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                headersbuilder.add(key, headersParams.get(key));
            }
        }
        headers = headersbuilder.build();
        return headers;
    }

    /**
     * post请求参数
     *
     * @param BodyParams
     * @return
     */
    private RequestBody SetPostRequestBody(Map<String, String> BodyParams) {
        RequestBody body = null;
        FormBody.Builder formEncodingBuilder = new FormBody.Builder();
        if (BodyParams != null) {
            Iterator<String> iterator = BodyParams.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                formEncodingBuilder.add(key, BodyParams.get(key));
            }
        }
        body = formEncodingBuilder.build();
        return body;
    }


    /**
     * get方法连接拼加参数
     *
     * @param mapParams
     * @return
     */
    private String setGetUrlParams(Map<String, String> mapParams) {
        String strParams = "";
        if (mapParams != null) {
            Iterator<String> iterator = mapParams.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                strParams += "&" + key + "=" + mapParams.get(key);
            }
        }
        return strParams;
    }


    /**
     * 实现post请求
     *
     * @param reqUrl
     * @param headersParams
     * @param params
     * @param callback
     */
    public void doAsyncPost(String reqUrl, Map<String, String> headersParams, Map<String, String> params, final NetCallback callback) {
        Request.Builder RequestBuilder = new Request.Builder();
        RequestBuilder.url(reqUrl);// 添加URL地址
        RequestBuilder.method("POST", SetPostRequestBody(params));
        RequestBuilder.headers(SetHeaders(headersParams));// 添加请求头
        Request request = RequestBuilder.build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                callback.onSuccess(0, response.body().toString());
                call.cancel();
            }

            @Override
            public void onFailure(final Call call, final IOException exception) {
                callback.onFailure(-1, exception.getMessage());
                call.cancel();
            }
        });
    }


    /**
     * 实现get请求
     *
     * @param reqUrl
     * @param headersParams
     * @param params
     * @param callback
     */
    public void doAsyncGet(String reqUrl, Map<String, String> headersParams, Map<String, String> params, final NetCallback callback) {
        Request.Builder RequestBuilder = new Request.Builder();
        RequestBuilder.url(reqUrl + setGetUrlParams(params));// 添加URL地址 自行加 ?
        RequestBuilder.headers(SetHeaders(headersParams));// 添加请求头
        Request request = RequestBuilder.build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                callback.onSuccess(0, response.body().toString());
                call.cancel();
            }

            @Override
            public void onFailure(final Call call, final IOException exception) {
                callback.onFailure(-1, exception.getMessage());
                call.cancel();
            }
        });
    }


    /**
     * @Author: Post上传图片的参数
     * @Description:
     * @Date: 下午8:21 2017/8/7
     * @Param:  * @param BodyParams
     * @param filePathParams
     */
    private RequestBody SetFileRequestBody(Map<String, String> BodyParams, Map<String, String> filePathParams) {
        // 带文件的Post参数
        RequestBody body = null;
        MultipartBody.Builder MultipartBodyBuilder = new MultipartBody.Builder();
        MultipartBodyBuilder.setType(MultipartBody.FORM);
        RequestBody fileBody = null;
        if (BodyParams != null) {
            Iterator<String> iterator = BodyParams.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                MultipartBodyBuilder.addFormDataPart(key, BodyParams.get(key));
            }
        }
        if (filePathParams != null) {
            Iterator<String> iterator = filePathParams.keySet().iterator();
            String key = "";
            int i = 0;
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                i++;
                MultipartBodyBuilder.addFormDataPart(key, filePathParams.get(key));
                fileBody = RequestBody.create(MEDIA_TYPE_PNG, new File(filePathParams.get(key)));
                MultipartBodyBuilder.addFormDataPart(key, i + ".png", fileBody);
            }
        }
        body = MultipartBodyBuilder.build();
        return body;
    }


    /**
     * @Author: JiangMin
     * @Description:
     * @Date: 下午7:24 2017/8/7
     * @Param:  * @param null
     */
    public String doGet(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        if (params != null && params.size() > 0) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            sb.append("?");
            for (Map.Entry<String, String> entry : entrySet) {
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
                sb.append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        Request request = new Request.Builder().url(url + sb.toString()).get().build();
        Call call = mOkHttpClient.newCall(request);
        try {
            Response response = call.execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            logger.error("", e);
        }
        return null;
    }

    /**
     * @Author: JiangMin
     * @Description:
     * @Date: 下午7:25 2017/8/7
     * @Param:  * @param null
     */
    public String doPost(String url, Map<String, String> params) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (params != null && params.size() > 0) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                formBodyBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        Request request = new Request.Builder().url(url).post(formBodyBuilder.build()).build();
        Call call = mOkHttpClient.newCall(request);
        try {
            Response response = call.execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            logger.error("", e);
        }
        return null;
    }


    public abstract class NetCallback {
        public abstract void onFailure(int code, String msg);
        public abstract void onSuccess(int code, String content);
    }

}