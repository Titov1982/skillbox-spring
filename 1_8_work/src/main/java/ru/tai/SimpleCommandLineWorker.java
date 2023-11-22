package ru.tai;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.tai.service.CommandLineScanner;

@Component
@Profile("simple")
public class SimpleCommandLineWorker implements CommandLineWorker{

    private final CommandLineScanner commandLineScanner;
    public SimpleCommandLineWorker(CommandLineScanner commandLineScanner) {
        this.commandLineScanner = commandLineScanner;
    }

    public void work() {
        commandLineScanner.startCommandLineScanner();
    }
}
