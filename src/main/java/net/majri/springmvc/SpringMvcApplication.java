package net.majri.springmvc;

import net.majri.springmvc.entities.Product;
import net.majri.springmvc.repository.ProductRepository;
import net.majri.springmvc.service.AccountService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@SpringBootApplication
public class SpringMvcApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringMvcApplication.class, args);
    }
    @Bean
    public CommandLineRunner start(AccountService accountService, ProductRepository productRepository) {
        return args -> {
            accountService.addNewRole("ROLE_USER", "Standard User");
            accountService.addNewRole("ROLE_ADMIN", "System Admin");


            accountService.addNewUser("salma", "1234", "1234");
            accountService.addNewUser("admin", "1234", "1234");

            accountService.addRoleToUser("admin", "ROLE_USER");
            accountService.addRoleToUser("admin", "ROLE_ADMIN");
            accountService.addRoleToUser("salma", "ROLE_USER");
            productRepository.save(Product.builder()
                    .name("Computer")
                    .price(5400)
                    .quantity(12)
                    .build());
            productRepository.save(Product.builder()
                    .name("keyboard")
                    .price(1200)
                    .quantity(11)
                    .build());
            productRepository.save(Product.builder()
                    .name("Tablet")
                    .price(7200)
                    .quantity(12)
                    .build());
            productRepository.save(Product.builder()
                    .name("Printer")
                    .price(2800)
                    .quantity(4)
                    .build());
            productRepository.findAll().forEach(p->{System.out.println(p.toString());
            });
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
