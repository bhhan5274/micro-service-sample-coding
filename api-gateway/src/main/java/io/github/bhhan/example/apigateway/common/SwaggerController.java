package io.github.bhhan.example.apigateway.common;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hbh5274@gmail.com on 2021-01-14
 * Github : http://github.com/bhhan5274
 */

@RestController
public class SwaggerController {
    @GetMapping("/swagger-ui.html")
    public Resource getFile(){
        return new ClassPathResource("META-INF/swagger-ui/index.html");
    }
}
