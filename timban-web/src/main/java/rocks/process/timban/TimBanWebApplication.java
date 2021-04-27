package rocks.process.timban;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rocks.process.timban.data.domain.TimbanUser;

@SpringBootApplication
public class TimBanWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimBanWebApplication.class, args);


    }
}
