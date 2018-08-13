package com.just.server.wechat.core.listener;

import com.just.server.wechat.core.api.AdminInterface;
import com.just.server.wechat.core.configure.KongConfig;
import com.just.server.wechat.core.helper.HttpHelper;
import com.just.server.wechat.core.model.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import retrofit2.Response;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: wangkx
 * Date: 2018/08/13
 * Time: 14:31
 * Description: No Description
 */
@Component
public class RegisterKongApiListener {

    private static final Logger logger = LoggerFactory.getLogger(RegisterKongApiListener.class);

    @Autowired
    private KongConfig kongConfig;

    /**
     * 新版本的kong，提供了service和route两个新概念，取消了之前我们使用的api
     * <br>
     * 其本质也就是简化了对微服务的支持方式，原来没有服务的概念，只有一个个孤立的api，
     * 现在通过路由定位服务，通过服务找上游目标，并提供负载均衡和健康检查，俨然已发展成一套完整的微服务解决方案
     * <br>
     * 流程说明：
     * 获取Kong里注册的所有服务，如果没有本机配置的服务，新增当前服务
     * 所有所有路由，如果没有当前配置的路由，新增路由
     * 获取所有的upstream，如果没有当前配置的upstream，新增当前upstream
     * 获取所遇的target，如果没有当前配置的target，新增当前target
     * 流程结束
     * <br>
     * 配置的参数需要根据实际情况进行调整，可以在web管理端提供配置的入口
     */
    @EventListener({ContextStartedEvent.class})
    public void onContextStartedEvent() {
        logger.info("系统开始启动，请稍后，开启自动注册...");
        AdminInterface adminInterface = HttpHelper.buildInterface(kongConfig.getAdminUrl(), AdminInterface.class);
        try {

            // 清空所有数据，包括kong下面的所有服务、路由、上游、目标、插件，主要是方便内部调试使用的
//             clearKongData();

            // 获取所有服务
            KongServiceItemModel service = null;
            KongServiceListModel kongServiceListModel = adminInterface.getServices().execute().body();
            // 遍历所有服务
            boolean foundService = false;
            if (kongServiceListModel.getData() != null) {
                for (KongServiceItemModel serviceItem : kongServiceListModel.getData()) {
                    if (serviceItem.getName().equals(kongConfig.getServiceName())) {
                        foundService = true;
                        service = serviceItem;
                        break;
                    }
                }
            }

            if (!foundService) {
                // 新增服务
                service = adminInterface.addService(kongConfig.getServiceName(), kongConfig.getServiceUrl()).execute().body();
            }

            // 新增route
            KongRouteListModel kongRouteListModel = adminInterface.getRoutes().execute().body();
            for (KongConfig.KongRouteConfig routeConfig : kongConfig.getRoutes()) {
                // 遍历所有路由
                boolean foundRoute = false;
                KongRouteItemModel route = null;
                for (KongRouteItemModel kongRouteItemModel : kongRouteListModel.getData()) {
                    if (kongRouteItemModel.getPaths().contains(routeConfig.getPath())) {
                        foundRoute = true;
                        route = kongRouteItemModel;
                        break;
                    }
                }
                if (!foundRoute) {
                    // 新增路由
                    route = adminInterface.addRoute(routeConfig.getPath(), service.getId(), "false").execute().body();
                }
                // 配置插件
                if (StringUtils.isNotEmpty(routeConfig.getPlugin())) {
                    String[] plugins = routeConfig.getPlugin().split(",");
                    for (String plugin : plugins) {
                        boolean foundPlugin = false;
                        KongPluginListModel kongPluginListModel = adminInterface.getPluginsByRouteId(route.getId()).execute().body();
                        for (KongPluginItemModel kongPluginItemModel : kongPluginListModel.getData()) {
                            if (kongPluginItemModel.getName().equals(plugin)) {
                                foundPlugin = true;
                                break;
                            }
                        }
                        if (!foundPlugin) {
                            adminInterface.addPluginWithRoute(route.getId(), plugin).execute();
                        }
                    }
                }

            }

            // 新增upstream和target
            KongUpstreamListModel kongUpstreamListModel = adminInterface.getUpstreams().execute().body();
            boolean foundUpstream = false;
            for (KongUpstreamItemModel kongUpstreamItemModel : kongUpstreamListModel.getData()) {
                if (kongUpstreamItemModel.getName().equals(kongConfig.getUpstreamName())) {
                    foundUpstream = true;
                    break;
                }
            }
            if (!foundUpstream) {
                adminInterface.addUpstream(kongConfig.getUpstreamName(), kongConfig.getHealthCheckPath(), kongConfig.getHealthCheckConcurrency(), kongConfig.getHealthCheckTimeout(), kongConfig.getHealthCheckHealthyInterval(), kongConfig.getHealthCheckHealthySuccesses(), kongConfig.getHealthCheckUnhealthyInterval(), kongConfig.getHealthCheckUnhealthyTimeouts()).execute();
            }

            KongTargetListModel kongTargetListModel = adminInterface.getTargetWithUpstream(kongConfig.getUpstreamName()).execute().body();
            boolean foundTarget = false;
            for (KongTargetItemModel kongTargetItemModel : kongTargetListModel.getData()) {
                if (kongTargetItemModel.getTarget().equals(kongConfig.getTarget())) {
                    foundTarget = true;
                    break;
                }
            }

            if (!foundTarget) {
                adminInterface.addTarget(kongConfig.getUpstreamName(), kongConfig.getTarget()).execute();
            }

            logger.info("系统启动完毕...");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 清空kong的所有数据，调试时使用，生产环境下勿用
     *
     * @throws IOException
     */
    private void clearKongData() throws IOException {
        logger.warn("请注意，开始执行Kong的清理才做，会清空所有service、route、plugin、upstream、target，不会清空consumer");
        AdminInterface adminInterface = HttpHelper.buildInterface(kongConfig.getAdminUrl(), AdminInterface.class);
        KongServiceListModel services = adminInterface.getServices().execute().body();
        Response response;

        // plugin
        KongPluginListModel kongPluginListModel = adminInterface.getPlugins().execute().body();
        for (KongPluginItemModel kongPluginItemModel : kongPluginListModel.getData()) {
            response = adminInterface.deletePlugin(kongPluginItemModel.getId()).execute();
        }

        // route
        KongRouteListModel kongRouteListModel = adminInterface.getRoutes().execute().body();
        for (KongRouteItemModel kongRouteItemModel : kongRouteListModel.getData()) {
            response = adminInterface.deleteRoute(kongRouteItemModel.getId()).execute();
        }

        // upstream
        KongUpstreamListModel kongUpstreamListModel = adminInterface.getUpstreams().execute().body();
        for (KongUpstreamItemModel kongUpstreamItemModel : kongUpstreamListModel.getData()) {
            // target
            KongTargetListModel kongTargetListModel = adminInterface.getTargetWithUpstream(kongUpstreamItemModel.getName()).execute().body();
            for (KongTargetItemModel kongTargetItemModel : kongTargetListModel.getData()) {
                response = adminInterface.deleteTarget(kongUpstreamItemModel.getName(), kongTargetItemModel.getId()).execute();
            }
            response = adminInterface.deleteUpstream(kongUpstreamItemModel.getName()).execute();

        }

        // service
        if (services.getData() != null && services.getData().size() > 0) {
            for (KongServiceItemModel serviceItem : services.getData()) {
                response = adminInterface.deleteService(serviceItem.getName()).execute();
            }
        }
        logger.warn("清理完毕");
    }
}
