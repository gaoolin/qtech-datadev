package com.qtech.ceph.object.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotBlank;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/07/17 11:41:31
 * desc   :  连接Ceph集群实体类
 */

//@Configuration("cephGrwAccess")
//@ConfigurationProperties("ceph") // pojo整体注入
/*
public class CephGrwAccessInfo implements InitializingBean {

    private String accessKey;
    private String secretKey;
    private String domainIp;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!StringUtils.hasText(accessKey)){
            accessKey="smsQueue";
        }
        if (!StringUtils.hasText(secretKey)){
            secretKey="smsExchange";
        }
        if (!StringUtils.hasText(domainIp)){
            domainIp="domainIp";
        }
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getDomainIp() {
        return domainIp;
    }

    public void setDomainIp(String domainIp) {
        this.domainIp = domainIp;
    }

    @Override
    public String toString() {
        return "CephGrwAccessInfo{" +
                "accessKey='" + accessKey + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", domainIp='" + domainIp + '\'' +
                '}';
    }
}
*/

@Configuration
@ConfigurationProperties(prefix = "ceph")
@Validated
public class CephGrwAccessInfo {

    @NotBlank(message = "Access key must not be blank")
    private String accessKey;

    @NotBlank(message = "Secret key must not be blank")
    private String secretKey;

    @NotBlank(message = "Domain IP must not be blank")
    private String endpoint;

    @PostConstruct
    public void initializeDefaults() {
        if (accessKey == null || accessKey.trim().isEmpty()) {
            accessKey = "defaultAccessKey";
        }
        if (secretKey == null || secretKey.trim().isEmpty()) {
            secretKey = "defaultSecretKey";
        }
        if (endpoint == null || endpoint.trim().isEmpty()) {
            endpoint = "defaultEndpoint";
        }
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public String toString() {
        return "CephGrwAccessInfo{" +
                "accessKey='" + accessKey + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", endpoint='" + endpoint + '\'' +
                '}';
    }
}