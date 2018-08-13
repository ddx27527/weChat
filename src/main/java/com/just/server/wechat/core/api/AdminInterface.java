package com.just.server.wechat.core.api;

import com.just.server.wechat.core.model.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wangkx
 * Date: 2018/08/13
 * Time: 14:38
 * Description: No Description
 */
public interface AdminInterface {

    /**
     * 获取kong里的所有服务
     *
     * @return
     */
    @GET("services")
    Call<KongServiceListModel> getServices();

    /**
     * 获取所有plugin插件
     *
     * @return
     */
    @GET("plugins")
    Call<KongPluginListModel> getPlugins();

    /**
     * 根据路由id获取所有插件
     *
     * @param routeId
     * @return
     */
    @GET("plugins")
    Call<KongPluginListModel> getPluginsByRouteId(@Query("route_id") String routeId);

    /**
     * 新增服务
     *
     * @param name
     * @param url
     * @return
     */
    @FormUrlEncoded
    @POST("services")
    Call<KongServiceItemModel> addService(@Field("name") String name, @Field("url") String url);

    /**
     * 删除服务
     *
     * @param serviceName
     * @return
     */
    @DELETE("services/{serviceName}")
    Call<Map> deleteService(@Path("serviceName") String serviceName);

    /**
     * 删除路由
     *
     * @param routeId
     * @return
     */
    @DELETE("routes/{routeId}")
    Call<Map> deleteRoute(@Path("routeId") String routeId);

    /**
     * 删除upstream
     *
     * @param upstreamName
     * @return
     */
    @DELETE("upstreams/{upstreamName}")
    Call<Map> deleteUpstream(@Path("upstreamName") String upstreamName);

    /**
     * 删除target
     *
     * @param upstreamName
     * @param targetId
     * @return
     */
    @DELETE("upstreams/{upstreamName}/targets/{targetId}")
    Call<Map> deleteTarget(@Path("upstreamName") String upstreamName, @Path("targetId") String targetId);

    /**
     * 删除plugin
     *
     * @param pluginId
     * @return
     */
    @DELETE("plugins/{pluginId}")
    Call<Map> deletePlugin(@Path("pluginId") String pluginId);

    /**
     * 获取所有路由
     *
     * @return
     */
    @GET("routes")
    Call<KongRouteListModel> getRoutes();

    /**
     * 获取某个服务下的所有路由
     *
     * @param serviceName
     * @return
     */
    @GET("services/{serviceName}/routes")
    Call<KongRouteListModel> getRoutesByService(@Path("serviceName") String serviceName);

    /**
     * 根据upstream的名字获取当前upstream下的所有target的健康状态，upstream的名字和service的名字是一致的
     *
     * @param upstreamName
     * @return
     */
    @GET("upstreams/{name}/health")
    Call<KongTargetListModel> getTargetHealth(@Path("name") String upstreamName);

    /**
     * 新增路由
     *
     * @param paths
     * @param serviceId
     * @return
     */
    @FormUrlEncoded
    @POST("routes")
    Call<KongRouteItemModel> addRoute(@Field("paths[]") String paths, @Field("service.id") String serviceId, @Field("strip_path") String stripPath);

    /**
     * 给路由加插件
     *
     * @param routeId
     * @param pluginName
     * @return
     */
    @FormUrlEncoded
    @POST("routes/{routeId}/plugins")
    Call<Map> addPluginWithRoute(@Path("routeId") String routeId, @Field("name") String pluginName);

    /**
     * 获取所有upstream，每个upstream对应一个service
     *
     * @return
     */
    @GET("upstreams")
    Call<KongUpstreamListModel> getUpstreams();

    /**
     * 新增upstream
     *
     * @param name
     * @param healthCheckPath
     * @param healthCheckConcurrency
     * @param healthCheckTimeout
     * @param healthCheckHealthyInterval
     * @param healthCheckHealthySuccesses
     * @param healthCheckUnhealthyInterval
     * @param healthCheckUnhealthyTimeouts
     * @return
     */
    @FormUrlEncoded
    @POST("upstreams")
    Call<Map> addUpstream(@Field("name") String name, @Field("healthchecks.active.http_path") String healthCheckPath, @Field("healthchecks.active.concurrency") String healthCheckConcurrency, @Field("healthchecks.active.timeout") String healthCheckTimeout, @Field("healthchecks.active.healthy.interval") String healthCheckHealthyInterval, @Field("healthchecks.active.healthy.successes") String healthCheckHealthySuccesses, @Field("healthchecks.active.unhealthy.interval") String healthCheckUnhealthyInterval, @Field("healthchecks.active.unhealthy.timeouts") String healthCheckUnhealthyTimeouts);

    /**
     * 获取upstream下的所有target
     *
     * @param upstreamName
     * @return
     */
    @GET("/upstreams/{upstreamName}/targets/all/")
    Call<KongTargetListModel> getTargetWithUpstream(@Path("upstreamName") String upstreamName);

    /**
     * 新增API
     *
     * @param name        API名称 命名方式为类名+"."+方法名
     * @param uris        uri地址，默认一个http请求对应对一个uri地址
     * @param methods
     * @param upstreamUrl
     * @return
     * @deprecated
     */
    @FormUrlEncoded
    @POST("apis")
    Call<Map> addApi(@Field("name") String name, @Field("uris") String uris, @Field("methods") String methods, @Field("upstream_url") String upstreamUrl);

    /**
     * 新增插件
     *
     * @param apiName
     * @param pluginName
     * @return
     * @deprecated
     */
    @FormUrlEncoded
    @POST("apis/{name}/plugins")
    Call<Map> addPlugin(@Path("name") String apiName, @Field("name") String pluginName);

    /**
     * 生成负载均衡的前置地址
     *
     * @param upstream
     * @return
     */
    @FormUrlEncoded
    @POST("upstreams")
    Call<Map> addUpstream(@Field("name") String upstream);

    /**
     * 生成负载均衡的目标地址
     *
     * @param upstream
     * @param target
     * @return
     */
    @FormUrlEncoded
    @POST("upstreams/{name}/targets")
    Call<Map> addTarget(@Path("name") String upstream, @Field("target") String target);

    /**
     * 获取某个upstream下所有target
     *
     * @param upstream
     * @return
     */
    @GET("upstreams/{name}/targets")
    Call<Map> getTarget(@Path("name") String upstream);

    /**
     * 根据kong的userName获取对应的消费者
     * 所以消费者的id没有太大意义，消费者的userName要保证唯一，因此要加前缀用于区分
     *
     * @param userName
     * @return
     */
    @GET("consumers/{userName}")
    Call<KongConsumerModel> getConsumerByUserName(@Path("userName") String userName);

    /**
     * 创建消费者
     *
     * @param userName
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("consumers")
    Call<KongConsumerModel> addConsumer(@Field("username") String userName, @Field("custom_id") String userId);

    /**
     * 删除kong的消费者
     *
     * @param consumer
     * @return
     */
    @DELETE("consumers/{consumer}")
    Call<Map> deleteConsumer(@Path("consumer") String consumer);

    /**
     * 给消费者配置jwt认证信息
     *
     * @param userName
     * @param key
     * @param secret
     * @return
     */
    @FormUrlEncoded
    @POST("consumers/{userName}/jwt")
    Call<KongJwtModel> setConsumerJwt(@Path("userName") String userName, @Field("key") String key, @Field("secret") String secret);

    /**
     * 删除消费者的jwt认证信息，用户退出的时候需要执行本方法
     *
     * @return
     */
    @DELETE("consumers/{userName}/jwt/{jwtId}")
    Call<Map> deleteConsumerJwt(@Path("userName") String userName, @Path("jwtId") String jwtId);
}