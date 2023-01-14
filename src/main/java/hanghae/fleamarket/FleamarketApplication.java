package hanghae.fleamarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@EnableJpaAuditing
@SpringBootApplication
public class FleamarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(FleamarketApplication.class, args);
    }

}
