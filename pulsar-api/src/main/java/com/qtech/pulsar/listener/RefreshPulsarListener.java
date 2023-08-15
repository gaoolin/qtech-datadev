package com.qtech.pulsar.listener;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/08/15 09:57:08
 * desc   :  在更新Nacos配置之后 Consumer会挂掉经排查发现结果是由于@RefreshScope注解导致，此注解将摧毁Bean，PulsarConsumer和Producer都将被摧毁，
 * 只是说Producer将在下⼀次调⽤中完成重启，Consumer则不能重启，因为没有调⽤，那么怎么解决呢?就是发布系列事件以刷新容器
 */

/*@Component
public class RefreshPulsarListener implements ApplicationListener {

    private static final Logger logger = LoggerFactory.getLogger(RefreshPulsarListener.class);
    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        if (event.getSource().equals("__refreshAll__")) {
            logger.info("Nacos配置中心配置修改 重启Pulsar====================================");
            logger.info("重启PulsarClient,{}", applicationContext.getBean("getPulsarClient"));
            logger.info("重启PulsarConsumer,{}", applicationContext.getBean("comment-publish-topic-consumer"));
            logger.info("重启PulsarConsumer,{}", applicationContext.getBean("reply-publish-topic-consumer"));
        }
    }
}*/
