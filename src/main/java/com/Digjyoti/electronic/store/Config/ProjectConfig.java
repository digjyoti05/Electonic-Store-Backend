package com.Digjyoti.electronic.store.Config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Configuration
public class ProjectConfig {
    @Bean
    public ModelMapper mapper(){
        return new ModelMapper();
    }
}
