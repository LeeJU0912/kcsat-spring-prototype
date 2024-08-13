package hpclab.ksatengmaker_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class KSatEngMakerSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(KSatEngMakerSpringApplication.class, args);
    }

}
