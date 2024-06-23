package com.qtech.check.prometheus;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/06/22 17:18:53
 * desc   :
 */

@Component
public class ImAaParamsMetricsService {


    private final Counter customMetricCounter;

    public ImAaParamsMetricsService(MeterRegistry meterRegistry) {
        customMetricCounter = Counter.builder("custom_metric_name")
          .description("Description of custom metric")
          .tags("environment", "development")
          .register(meterRegistry);
    }

    public void incrementCustomMetric() {
        customMetricCounter.increment();
    }
}
