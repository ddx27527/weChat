package com.just.server.wechat.core.model

/**
 * Created with IntelliJ IDEA.
 * User: wangkx
 * Date: 2018/08/13
 * Time: 14:42
 * Description: No Description
 */
data class KongServiceListModel(val data: List<KongServiceItemModel>, val next: String)

data class KongServiceItemModel(val id: String, val name: String, val host: String, val port: String, val path: String)
data class KongRouteListModel(val data: List<KongRouteItemModel>, val next: String)
data class KongRouteItemModel(val id: String, val paths: List<String>, val service: KongIdModel, val regex_priority: String?)
data class KongPluginListModel(val data: List<KongPluginItemModel>, val next: String)
data class KongPluginItemModel(val id: String, val name: String, val service_id: String?, val consumer_id: String?, val route_id: String?)
data class KongIdModel(val id: String)
data class KongUpstreamListModel(val data: List<KongUpstreamItemModel>, val next: String)
data class KongUpstreamItemModel(val id: String, val name: String)
data class KongTargetListModel(val data: List<KongTargetItemModel>, val total: String)
data class KongTargetItemModel(val id: String, val target: String, val health: String)
data class KongConsumerModel(val id: String, val custom_id: String)
data class KongJwtModel(val id: String, val key: String, val secret: String)
/**
 * 用户登录返回的数据
 */
data class UserLoginVo(val token: String, val name: String)

/**
 * API网关返回的数据
 */
data class ApiVo(val serviceName: String, val routes: List<ApiRouteVo>, val targets: List<ApiTargetVo>)

data class ApiRouteVo(val path: String, val plugins: String)
data class ApiTargetVo(val target: String, val status: String)

/**
 * 首页返回的统计数据
 */
data class HomeData(val running: Int, val containers: Int, val days: Int, val hours: Int, val mins: Int, val secs: Int, val conn: Int,
                    val conc: Int, val subOn: List<Int>, val subDown: List<Int>, val midOn: List<Int>, val midDown: List<Int>, val runningData: HomeRunningData)

data class HomeRunningData(var kongRunning: Boolean,
                           var cassandraRunning: Boolean,
                           var mariadbRunning: Boolean,
                           var mongodbRunning: Boolean,
                           var emqRunning: Boolean,
                           var rabbitmqRunning: Boolean,
                           var rtmpRunning: Boolean,
                           var guiBackRunning: Boolean,
                           var libAuthRunning: Boolean,
                           val libAppRunning: Boolean,
                           var libApiRunning: Boolean,
                           var libDeviceRunning: Boolean,
                           var libRuleRunning: Boolean,
                           var libShadowRunning: Boolean,
                           var libMediaRunning: Boolean)