package ru.tai.configuration;


import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import java.util.Objects;
import java.util.Scanner;

@ComponentScan("ru.tai")
@Configuration
//@PropertySource("classpath:application.yaml")
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Bean
    public Scanner firstScanner() {
        return new Scanner(System.in);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
//        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
//        yamlPropertiesFactoryBean.setResources(new ClassPathResource("application.yaml"));
//        configurer.setProperties(Objects.requireNonNull(yamlPropertiesFactoryBean.getObject()));
        configurer.setLocation(new ClassPathResource("application.properties"));
        return configurer;
    }
}
