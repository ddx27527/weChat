//package com.just.server.wechat.core.configure;
//
//import retrofit2.Call;
//import retrofit2.http.*;
//
//import java.util.Map;
//
///**
// * Created with IntelliJ IDEA.
// * User: wangkx
// * Date: 2018/08/13
// * Time: 15:13
// * Description: No Description
// */
//public interface CliInterface {
//
//    /**
//     * 新增API
//     *
//     * @param name        API名称 命名方式为类名+"."+方法名
//     * @param uris        uri地址，默认一个http请求对应对一个uri地址
//     * @param methods
//     * @param upstreamUrl
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("apis")
//    Call<Map> addApi(@Field("name") String name, @Field("uris") String uris, @Field("methods") String methods, @Field("upstream_url") String upstreamUrl);
//
//    /**
//     * 新增插件
//     *
//     * @param apiName
//     * @param pluginName
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("apis/{name}/plugins")
//    Call<Map> addPlugin(@Path("name") String apiName, @Field("name") String pluginName);
//
//    /**
//     * 生成负载均衡的前置地址
//     *
//     * @param upstream
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("upstreams")
//    Call<Map> addUpstream(@Field("name") String upstream);
//
//    /**
//     * 生成负载均衡的目标地址
//     *
//     * @param upstream
//     * @param target
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("upstreams/{name}/targets")
//    Call<Map> addTarget(@Path("name") String upstream, @Field("target") String target);
//
//    /**
//     * 获取某个upstream下所有target
//     * @param upstream
//     * @return
//     */
//    @GET("upstreams/{name}/targets")
//    Call<Map> getTarget(@Path("name") String upstream);
//}