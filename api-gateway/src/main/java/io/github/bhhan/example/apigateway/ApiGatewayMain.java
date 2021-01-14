package io.github.bhhan.example.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Created by hbh5274@gmail.com on 2021-01-14
 * Github : http://github.com/bhhan5274
 */

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ApiGatewayMain {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayMain.class, args);
    }
}
