package org.example.dockerdbexample.jdbc;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class DataInitializer {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() throws Exception {
        String sql = Files.readString(Paths.get("E:\\IdeaIU\\Git\\DockerDBExample\\src\\main\\resources\\data.sql"));
        jdbcTemplate.execute(sql);
    }
}
