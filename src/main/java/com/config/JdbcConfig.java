package com.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@Component
@PropertySource("application.properties")
public class JdbcConfig {
    @Value("${url}")
    private String url;
    @Value("${user}")
    private String user;
    @Value("${password}")
    private String password;
    @Value("${driver}")
    private String driver;

    public Statement getStat() {
        return connect();
    }

    private Statement connect() {
        Statement stat = null;
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, password);
             stat = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stat;
    }
}