package ru.tai.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import ru.tai.CommandLineWorker;
import ru.tai.InitDataCommandLineWorker;
import ru.tai.service.CommandLineScanner;
import ru.tai.service.DataInitializer;

@Configuration
//@PropertySource("classpath:application.yaml")
@PropertySource("classpath:application.properties")
@Profile("init")
public class InitDataAppConfig {

    @Autowired
    private DataInitializer dataInitializer;

    @Autowired
    private CommandLineScanner commandLineScanner;

//    @Bean
//    CommandLineWorker commandLineWorker() {
//        return new InitDataCommandLineWorker(commandLineScanner, dataInitializer);
//    }
}
