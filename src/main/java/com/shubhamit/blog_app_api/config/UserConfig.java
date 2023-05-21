package com.shubhamit.blog_app_api.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

}