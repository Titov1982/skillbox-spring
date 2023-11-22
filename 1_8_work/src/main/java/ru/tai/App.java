package ru.tai;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.tai.configuration.AppConfig;


public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        String[] profileNames = context.getEnvironment().getActiveProfiles();
        for (String profileName: profileNames) {
            System.out.println("Загружен профиль: " + profileName);
        }
        context.getBean(CommandLineWorker.class).work();
    }
}
