package ru.tai.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import ru.tai.CommandLineWorker;
import ru.tai.DataInitCommandLineWorker;
import ru.tai.service.CommandLineScanner;
import ru.tai.service.DataInitializer;
import ru.tai.storage.ContactStorage;

@Configuration
@PropertySource("classpath:application.yaml")
@Profile("init")
public class InitDataAppConfig {

    @Autowired
    private ContactStorage contactStorage;

    @Autowired
    private DataInitializer dataInitializer;

    @Autowired
    private CommandLineScanner commandLineScanner;
//    @Bean
//    public DataInitializer dataInitializer() {
//        return new DataInitializer(contactStorage);
//    }

    CommandLineWorker commandLineWorker() {
        return new DataInitCommandLineWorker(commandLineScanner, dataInitializer);
    }


    public InitDataAppConfig() {
        System.out.println("INIT");
    }
}
