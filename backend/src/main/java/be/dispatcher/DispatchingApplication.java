package be.dispatcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import be.dispatcher.world.World;

@SpringBootApplication
@PropertySource("dispatcher.properties")
@Import(WeavingConfigurer.class)
@ComponentScan(value = {"be.dispatcher"})
@EnableAutoConfiguration
public class DispatchingApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(DispatchingApplication.class, args);
        startTheWorldAfterStartup(run);
    }

    private static void startTheWorldAfterStartup(ConfigurableApplicationContext run) {
        run.getBean(World.class).startWorldTicking();
    }
}