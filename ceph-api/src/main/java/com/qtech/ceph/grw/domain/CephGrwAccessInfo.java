package com.qtech.ceph.grw.domain;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/07/17 11:41:31
 * desc   :  TODO
 */

//@Configuration("cephGrwAccess")
//@ConfigurationProperties("ceph") // pojo整体注入
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
