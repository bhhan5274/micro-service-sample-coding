package io.github.bhhan.example.order.web;

import io.github.bhhan.example.common.swagger.CommonSwaggerConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Created by hbh5274@gmail.com on 2021-01-13
 * Github : http://github.com/bhhan5274
 */

@Configuration
@Import({CommonSwaggerConfiguration.class})
public class OrderWebConfiguration {
    @Bean
    public HttpMessageConverters customConverters(){
        MappingJackson2HttpMessageConverter additional = new MappingJackson2HttpMessageConverter();
        return new HttpMessageConverters(additional);
    }
}
