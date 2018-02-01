package com.hiveview.base.util.http;

import okhttp3.Interceptor;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: mikejiang
 * Date: 2017/8/8
 * Time: 下午2:16
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class OkHttpLogInterceptor implements Interceptor {

    public Logger logger = LoggerFactory.getLogger(OkHttpLogInterceptor.class);

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        logger.info("okHttp3 request:" + request.toString());
        long t1 = System.nanoTime();
        okhttp3.Response response = chain.proceed(chain.request());
        long t2 = System.nanoTime();
        logger.info("okHttp3 :" + String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        logger.info("okHttp3 response body:" + content);
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
}