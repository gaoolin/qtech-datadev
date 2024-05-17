package com.qtech.message.config.apiKey;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/17 10:00:51
 * desc   :
 */

@Configuration
@ConfigurationProperties(prefix = "exempt")
public class ExemptPathsConfig {

    private List<String> exemptPaths;

    public List<String> getExemptPaths() {
        return exemptPaths;
    }

    public void setPaths(List<String> paths) {
        this.exemptPaths = paths;
    }
}
