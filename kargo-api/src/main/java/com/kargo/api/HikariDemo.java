package com.kargo.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
@Order(5)
public class HikariDemo implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(HikariDemo.class);

    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private String havuzBoyutu;

    private final JdbcTemplate jdbcTemplate;

    public HikariDemo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        int isSayisi = 20;
        ExecutorService executor = Executors.newFixedThreadPool(isSayisi);
        Long currentTimeMillis = System.currentTimeMillis();

        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < isSayisi; i++){
            futures.add(executor.submit(()->jdbcTemplate.execute("select pg_sleep(1)")));
        }

        for (var fut: futures){
            fut.get();
        }

        executor.shutdown();

        Long newCurrentTimes = System.currentTimeMillis() - currentTimeMillis;



        logger.info("Geçen Süre "+ newCurrentTimes);
        logger.info("Havuz Boyutu " + havuzBoyutu);
        logger.info("SIZINTI DENEYİ");
        jdbcTemplate.execute("select pg_sleep(5)");
    }
}