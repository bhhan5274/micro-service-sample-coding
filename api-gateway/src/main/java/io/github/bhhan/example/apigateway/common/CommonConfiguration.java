package io.github.bhhan.example.apigateway.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Created by hbh5274@gmail.com on 2021-01-14
 * Github : http://github.com/bhhan5274
 */

@Configuration
public class CommonConfiguration {
    @Bean
    public WebClient webClient(){
        return WebClient.create();
    }
}
