package com.qtech.service.config;

import com.qtech.service.config.interceptor.ApiKeyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/12/26 16:46:15
 * desc   :  CORS 的核心在于目标后台服务必须允许跨域请求。如果你有对后台服务的控制权限，以下是解决方法：
 * <p>
 * 2.1 Spring Boot 中的 CORS 配置
 * 在 Spring Boot 中，可以全局配置 CORS：全局允许跨域（最常见）
 * <p>
 * 显式指定允许的域名
 * 将通配符 * 替换为允许跨域的具体域名，例如：
 * <p>
 * <p>
 * Spring Boot 项目中设置了 allowCredentials(true)，但同时在 allowedOrigins 中使用了通配符 "*"。当允许跨域请求携带凭证（如 Cookies 或 HTTP Authentication）时，
 * allowedOrigins 不能使用 "*"，因为通配符与凭证模式不兼容。
 * <p>
 * java
 * 复制代码
 * import org.springframework.context.annotation.Configuration;
 * import org.springframework.web.servlet.config.annotation.CorsRegistry;
 * import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
 *
 * @Configuration public class WebConfig implements WebMvcConfigurer {
 * @Override public void addCorsMappings(CorsRegistry registry) {
 * registry.addMapping("/**") // 匹配所有接口
 * .allowedOrigins("http://localhost:8080", "http://your-frontend.com") // 具体的前端域名
 * .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
 * .allowedHeaders("*")
 * .allowCredentials(true); // 允许发送 Cookie 或其他凭证
 * }
 * }
 * allowedOrigins：列出允许访问的域名，多个域名用逗号分隔。
 * allowCredentials(true)：表示允许携带 Cookie 或其他凭证。
 * 使用 allowedOriginPatterns
 * 如果你的 Spring Boot 版本是 2.4 或更高，可以使用 allowedOriginPatterns 代替 allowedOrigins，支持更灵活的匹配：
 * <p>
 * java
 * 复制代码
 * @Configuration public class WebConfig implements WebMvcConfigurer {
 * @Override public void addCorsMappings(CorsRegistry registry) {
 * registry.addMapping("/**")
 * .allowedOriginPatterns("http://*.example.com", "http://localhost:*") // 支持通配符
 * .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
 * .allowedHeaders("*")
 * .allowCredentials(true); // 允许发送 Cookie 或其他凭证
 * }
 * }
 * allowedOriginPatterns 支持通配符，例如 http://*.example.com 可以匹配所有子域名。
 * <p>
 * 检查是否需要允许凭证
 * 如果你不需要发送凭证（如 Cookie 或认证信息），可以将 allowCredentials 设置为 false，并继续使用 allowedOrigins("*")：
 * <p>
 * java
 * 复制代码
 * @Configuration public class WebConfig implements WebMvcConfigurer {
 * @Override public void addCorsMappings(CorsRegistry registry) {
 * registry.addMapping("/**")
 * .allowedOrigins("*") // 允许所有域名
 * .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
 * .allowedHeaders("*")
 * .allowCredentials(false); // 不允许发送凭证
 * }
 * }
 * 更新前端的请求设置
 * 如果你启用了 allowCredentials(true)，前端在发送请求时也需要确保 credentials 配置正确：
 * <p>
 * 在 Axios 中：
 * <p>
 * javascript
 * 复制代码
 * axios.defaults.withCredentials = true;
 * 在 Fetch API 中：
 * <p>
 * javascript
 * 复制代码
 * fetch('http://your-backend.com/api', {
 * credentials: 'include',
 * });
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private ApiKeyInterceptor apiKeyInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*") // 允许所有域名
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").allowedHeaders("*").allowCredentials(false); // 不允许发送凭证
    }

    /**
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiKeyInterceptor)
                .addPathPatterns("/im/protected/**");  // 保护的路径
    }
}

