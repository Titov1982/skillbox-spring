package ru.tai;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.tai.service.CommandLineScanner;
import ru.tai.service.DataInitializer;

@Component
@Profile("init")
public class DataInitCommandLineWorker implements CommandLineWorker{

    private final CommandLineScanner commandLineScanner;
    private final DataInitializer dataInitializer;

    public DataInitCommandLineWorker(CommandLineScanner commandLineScanner, DataInitializer dataInitializer) {
        this.commandLineScanner = commandLineScanner;
        this.dataInitializer = dataInitializer;
    }
    @Override
    public void work() {
        dataInitializer.dataFileLoad();
        commandLineScanner.startCommandLineScanner();
    }
}
