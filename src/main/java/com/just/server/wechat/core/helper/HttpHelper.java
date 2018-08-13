package com.just.server.wechat.core.helper;

import okhttp3.OkHttpClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * HTTP工具类，支持HTTPS
 *
 * @author wanghao
 */
public class HttpHelper {

    private static Logger logger = LoggerFactory.getLogger(HttpHelper.class);

    private static SSLSocketFactory sslSocketFactory;
    private static HostnameVerifier hostnameVerifier;
    private static Retrofit.Builder builder;
    //    private static boolean retrofitInit;
    private static ReentrantLock lock = new ReentrantLock();
    //    private static Retrofit instance;
    private static Map<String, Retrofit> retrofitMap = new HashMap<>();

    private HttpHelper() {

    }

    /**
     * 初始化Retrofit，主要是为了屏蔽自签名HTTPS证书的授信问题
     */
    private static void initRetrofit() {
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                logger.info("nothing to to in checkClientTrusted");
            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                logger.info("nothing to to in checkServerTrusted");
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());
            hostnameVerifier = (s, sslSession) -> true;
            sslSocketFactory = sslContext.getSocketFactory();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        OkHttpClient okHttpClient = new OkHttpClient.Builder().sslSocketFactory(sslSocketFactory, trustManager).hostnameVerifier(hostnameVerifier).build();

        builder = new Retrofit.Builder().client(okHttpClient).addConverterFactory(GsonConverterFactory.create());

    }

    /**
     * 生成Retrofit实例，使用线程同步lock锁机制
     */
    private static Retrofit buildRetrofit(String baseUrl) {
        try {
            lock.lock();
            if (StringUtils.isNotEmpty(baseUrl)) {
                if (retrofitMap.get(baseUrl) == null) {
                    initRetrofit();
                    retrofitMap.put(baseUrl, builder.baseUrl(baseUrl).build());
                }
                return retrofitMap.get(baseUrl);
            }
        } finally {
            lock.unlock();
        }
        return null;
    }

    /**
     * 根据baseUrl生成对应的interface
     *
     * @param baseUrl
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T buildInterface(String baseUrl, Class<T> clazz) {
        return buildRetrofit(baseUrl).create(clazz);
    }
}
