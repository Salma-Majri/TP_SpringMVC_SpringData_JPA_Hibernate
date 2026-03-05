package net.majri.springmvc.web;

import lombok.AllArgsConstructor;
import net.majri.springmvc.entities.Product;
import net.majri.springmvc.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
@AllArgsConstructor
public class ProductController {

    private ProductRepository productRepository;

    @GetMapping("/index")
    public String index(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("productList", products);
        return "products";
    }
}