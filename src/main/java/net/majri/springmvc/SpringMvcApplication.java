package net.majri.springmvc;

import net.majri.springmvc.entities.Product;
import net.majri.springmvc.repository.ProductRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;



//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@SpringBootApplication
public class SpringMvcApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringMvcApplication.class, args);
    }
    @Bean
    public CommandLineRunner start(ProductRepository productRepository) {
        return args -> {
            productRepository.save(Product.builder()
                    .name("Computer")
                    .price(5400)
                    .quantity(12)
                    .build());
            productRepository.save(Product.builder()
                    .name("Clavier")
                    .price(1200)
                    .quantity(11)
                    .build());
            productRepository.save(Product.builder()
                    .name("Tablette")
                    .price(7200)
                    .quantity(12)
                    .build());
            productRepository.save(Product.builder()
                    .name("Imprimante")
                    .price(2800)
                    .quantity(4)
                    .build());
            productRepository.findAll().forEach(p->{System.out.println(p.toString());
            });
        };
    }


}
