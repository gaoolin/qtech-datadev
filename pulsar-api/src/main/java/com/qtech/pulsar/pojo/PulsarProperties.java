package com.qtech.pulsar.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
@ConfigurationProperties(prefix = "pulsar")
public class PulsarProperties {

    /**
     * 集群name
     */
    private String tenant;

    /**
     * 命名空间tdc
     */
    private String namespace;

    /**
     * topicMap
     */
    private Map<String, String> topicMap;

    /**
     * 订阅
     */
    private Map<String, String> subMap;

    /**
     * 接入地址
     */
    private String serviceUrl;

    /**
     * 角色tdc的token
     */
    private String token;


    /**
     * 开关 on:Consumer可用 ||||| off:Consumer断路
     */
    private String onOff;

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public Map<String, String> getTopicMap() {
        return topicMap;
    }

    public void setTopicMap(Map<String, String> topicMap) {
        this.topicMap = topicMap;
    }

    public Map<String, String> getSubMap() {
        return subMap;
    }

    public void setSubMap(Map<String, String> subMap) {
        this.subMap = subMap;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOnOff() {
        return onOff;
    }

    public void setOnOff(String onOff) {
        this.onOff = onOff;
    }

    @Override
    public String toString() {
        return "PulsarProperties{" +
                "tenant='" + tenant + '\'' +
                ", namespace='" + namespace + '\'' +
                ", topicMap=" + topicMap +
                ", subMap=" + subMap +
                ", serviceUrl='" + serviceUrl + '\'' +
                ", token='" + token + '\'' +
                ", onOff='" + onOff + '\'' +
                '}';
    }
}
