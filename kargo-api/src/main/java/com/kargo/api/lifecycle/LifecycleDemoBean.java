package com.kargo.api.lifecycle;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LifecycleDemoBean {
    private static final Logger log = LoggerFactory.getLogger(LifecycleDemoBean.class);

    @PostConstruct
    public void demo(){
        log.info("@PostConstruct LifecycleDemoBean ");
    }
}
