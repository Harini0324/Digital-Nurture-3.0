package com.example.BookstoreAPI.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Bean;

@Component
public class CustomMetricsService {

    @Bean
    public MeterRegistryCustomizer metricsCommonTags() {
        return registry -> registry.config().commonTags("application", "Online Bookstore API");
    }
    
    public void registerCustomMetrics(MeterRegistry registry) {
        registry.counter("custom_metric_total_books", "type", "counter")
                .increment(); 
    }
}
