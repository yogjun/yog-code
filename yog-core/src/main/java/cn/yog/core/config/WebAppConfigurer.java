package cn.yog.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
		        //.allowedOrigins("http://192.168.89.89")
                .allowedMethods("GET", "POST","DELETE","PUT")
                .allowCredentials(false).maxAge(3600);
    }
}

