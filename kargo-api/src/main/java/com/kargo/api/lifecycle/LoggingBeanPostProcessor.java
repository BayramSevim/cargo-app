package com.kargo.api.lifecycle;

import com.kargo.api.KargoApiApplication;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class LoggingBeanPostProcessor implements BeanPostProcessor {
    private static final Logger log = LoggerFactory.getLogger(LoggingBeanPostProcessor.class);
    @Override
    public @Nullable Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.contains("Pricing") || beanName.contains("pricing") || beanName.contains("ifecycle")){
            log.info("BEFORE  pricing");
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public @Nullable Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.contains("Pricing") || beanName.contains("pricing") || beanName.contains("ifecycle")){
            log.info("AFTER pricing");
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
