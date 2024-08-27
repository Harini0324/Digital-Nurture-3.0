package com.example.BookstoreAPI.metrics;

import io.micrometer.core.instrument.MeterRegistry;

@FunctionalInterface
public interface MeterRegistryCustomizer {
    void customize(MeterRegistry registry);
}
