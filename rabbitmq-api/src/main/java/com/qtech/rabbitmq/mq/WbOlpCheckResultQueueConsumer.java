package com.qtech.rabbitmq.mq;

import com.alibaba.fastjson2.JSON;
import com.qtech.rabbitmq.domain.WbOlpCheckResult;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/04/10 15:32:02
 * desc   :  接受消息
 */

@Slf4j
@Component
public class WbOlpCheckResultQueueConsumer {

    private static final String DORIS_USER = "root";
    private static final String DORIS_PASSWORD = "";

    // 从 application.yaml 文件中读取配置
    @Value("${DORIS_STREAM_LOAD_URL}")
    private String dorisStreamLoadUrl;

    @RabbitListener(queues = "wbOlpCheckResultQueue", ackMode = "MANUAL")
    public void receive(String msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        String DORIS_STREAM_LOAD_URL = dorisStreamLoadUrl;
        try {
            log.info("Received message: {}", msg);

            // 解析为数组
            List<WbOlpCheckResult> wbOlpCheckResultsList = JSON.parseArray(msg, WbOlpCheckResult.class);

            // 将数据转换为CSV格式
            StringBuilder csvData = new StringBuilder();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (WbOlpCheckResult result : wbOlpCheckResultsList) {
                csvData.append(result.getSimId()).append(',')
                        .append(result.getDt() != null ? dateFormat.format(result.getDt()) : "").append(',')
                        .append(result.getCode()).append(',')
                        .append(result.getDescription()).append('\n');
            }

            // 执行Stream Load
            HttpPost httpPost = new HttpPost(DORIS_STREAM_LOAD_URL);
            httpPost.setHeader("label", UUID.randomUUID().toString());
            httpPost.setHeader("Expect", "100-continue");
            String auth = DORIS_USER + ":" + DORIS_PASSWORD;
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
            String authHeader = "Basic " + new String(encodedAuth);
            httpPost.setHeader(new BasicHeader("Authorization", authHeader));

            HttpEntity entity = new StringEntity(csvData.toString(), ContentType.create("text/csv", "UTF-8"));
            httpPost.setEntity(entity);

            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse response = httpClient.execute(httpPost)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    log.info("Stream Load Success: {}", response.getStatusLine());
                    channel.basicAck(deliveryTag, false);
                } else {
                    log.error("Stream Load Failed: {}", response.getStatusLine());
                    String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
                    log.error("Response: {}", responseString);
                    channel.basicReject(deliveryTag, false);
                }
            }
        } catch (Exception e) {
            log.error("Failed to process message: {} - Error: {}", msg, e.getMessage(), e);
            channel.basicReject(deliveryTag, false); // false 表示丢弃
        }
    }
}
