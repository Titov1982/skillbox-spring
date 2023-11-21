package ru.tai;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.tai.service.CommandLineScanner;

@Component
@Profile("simple")
public class SimpleCommandLineWorker implements CommandLineWorker{

//    @Value("${app.data-start-init}")
//    private boolean dataStartInitFlag;
    private final CommandLineScanner commandLineScanner;
//    private final DataInitializer dataInitializer;

//    public CommandLineWorker(CommandLineScanner commandLineScanner, DataInitializer dataInitializer) {
//        this.commandLineScanner = commandLineScanner;
//        this.dataInitializer = dataInitializer;
//    }


    public SimpleCommandLineWorker(CommandLineScanner commandLineScanner) {
        this.commandLineScanner = commandLineScanner;
    }

    public void work() {
//        if (dataStartInitFlag) {
//            dataInitializer.dataFileLoad();
//        }
        commandLineScanner.startCommandLineScanner();
    }
}
