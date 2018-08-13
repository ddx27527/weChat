package com.just.server.wechat.core.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wangkx
 * Date: 2018/08/13
 * Time: 14:32
 * Description: No Description
 */
@Component
@ConfigurationProperties(prefix = "kong")
public class KongConfig {

    private String adminUrl;
    private String proxyUrl;
    private String serviceName;
    private String serviceUrl;
    private List<KongRouteConfig> routes = new ArrayList<>();
    private String upstreamName;
    private String healthCheckPath;
    private String healthCheckConcurrency;
    private String healthCheckTimeout;
    private String healthCheckHealthyInterval;
    private String healthCheckHealthySuccesses;
    private String healthCheckUnhealthyInterval;
    private String healthCheckUnhealthyTimeouts;
    private String target;

    public String getAdminUrl() {
        return adminUrl;
    }

    public void setAdminUrl(String adminUrl) {
        this.adminUrl = adminUrl;
    }

    public String getProxyUrl() {
        return proxyUrl;
    }

    public void setProxyUrl(String proxyUrl) {
        this.proxyUrl = proxyUrl;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public List<KongRouteConfig> getRoutes() {
        return routes;
    }

    public void setRoutes(List<KongRouteConfig> routes) {
        this.routes = routes;
    }

    public String getUpstreamName() {
        return upstreamName;
    }

    public void setUpstreamName(String upstreamName) {
        this.upstreamName = upstreamName;
    }

    public String getHealthCheckPath() {
        return healthCheckPath;
    }

    public void setHealthCheckPath(String healthCheckPath) {
        this.healthCheckPath = healthCheckPath;
    }

    public String getHealthCheckConcurrency() {
        return healthCheckConcurrency;
    }

    public void setHealthCheckConcurrency(String healthCheckConcurrency) {
        this.healthCheckConcurrency = healthCheckConcurrency;
    }

    public String getHealthCheckTimeout() {
        return healthCheckTimeout;
    }

    public void setHealthCheckTimeout(String healthCheckTimeout) {
        this.healthCheckTimeout = healthCheckTimeout;
    }

    public String getHealthCheckHealthyInterval() {
        return healthCheckHealthyInterval;
    }

    public void setHealthCheckHealthyInterval(String healthCheckHealthyInterval) {
        this.healthCheckHealthyInterval = healthCheckHealthyInterval;
    }

    public String getHealthCheckHealthySuccesses() {
        return healthCheckHealthySuccesses;
    }

    public void setHealthCheckHealthySuccesses(String healthCheckHealthySuccesses) {
        this.healthCheckHealthySuccesses = healthCheckHealthySuccesses;
    }

    public String getHealthCheckUnhealthyInterval() {
        return healthCheckUnhealthyInterval;
    }

    public void setHealthCheckUnhealthyInterval(String healthCheckUnhealthyInterval) {
        this.healthCheckUnhealthyInterval = healthCheckUnhealthyInterval;
    }

    public String getHealthCheckUnhealthyTimeouts() {
        return healthCheckUnhealthyTimeouts;
    }

    public void setHealthCheckUnhealthyTimeouts(String healthCheckUnhealthyTimeouts) {
        this.healthCheckUnhealthyTimeouts = healthCheckUnhealthyTimeouts;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public static class KongRouteConfig {
        private String path;
        private String plugin;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getPlugin() {
            return plugin;
        }

        public void setPlugin(String plugin) {
            this.plugin = plugin;
        }

    }
}
