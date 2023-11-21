package ru.tai.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import ru.tai.CommandLineWorker;
import ru.tai.SimpleCommandLineWorker;
import ru.tai.service.CommandLineScanner;
import ru.tai.service.DataInitializer;

@Configuration
@PropertySource("classpath:application.yaml")
@Profile("simple")
public class SimpleAppConfig {
    @Autowired
    private DataInitializer dataInitializer;

    @Autowired
    private CommandLineScanner commandLineScanner;

    CommandLineWorker commandLineWorker() {
        return new SimpleCommandLineWorker(commandLineScanner);
    }

    public SimpleAppConfig() {
        System.out.println("SIMPLE");
    }
}
