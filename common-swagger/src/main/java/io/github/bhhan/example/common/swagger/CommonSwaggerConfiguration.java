package io.github.bhhan.example.common.swagger;

import io.eventuate.util.spring.swagger.EventuateSwaggerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hbh5274@gmail.com on 2021-01-13
 * Github : http://github.com/bhhan5274
 */

@Configuration
public class CommonSwaggerConfiguration {
    private static final String DEFAULT_PACKAGE_NAME = "io.github.bhhan.example";

    @Bean
    public EventuateSwaggerConfig eventuateSwaggerConfig(){
        return () -> DEFAULT_PACKAGE_NAME;
    }
}
