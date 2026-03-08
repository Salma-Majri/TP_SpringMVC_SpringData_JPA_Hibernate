package net.majri.springmvc.web;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.majri.springmvc.entities.Product;
import net.majri.springmvc.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/user/index")
    @PreAuthorize("hasRole('USER')")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int p,
                        @RequestParam(name = "size", defaultValue = "5") int s,
                        @RequestParam(name = "keyword", defaultValue = "") String kw) {
        Page<Product> pageProducts = productRepository.findByNameContains(kw, PageRequest.of(p, s));
        model.addAttribute("productList", pageProducts.getContent());
        model.addAttribute("pages", new int[pageProducts.getTotalPages()]);
        model.addAttribute("currentPage", p);
        model.addAttribute("keyword", kw);
        return "products";
    }


    @GetMapping("/admin/editProduct")
    public String editProduct(Model model, @RequestParam(name = "id") Long id) {
        Product p = productRepository.findById(id).orElse(null);
        if (p == null) return "redirect:/user/index";
        model.addAttribute("product", p);
        return "editProduct";
    }
    @PostMapping("/admin/update")
    public String update(Model model, @Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "editProduct";
        productRepository.save(product);
        return "redirect:/user/index";
    }





    @GetMapping("/")
    public String home(Model model) {
        return "redirect:/user/index";
    }
    @PostMapping("/admin/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@RequestParam(name="id") Long id){
        productRepository.deleteById(id);
        return "redirect:/user/index";
    }
    @GetMapping("/admin/newProduct")
    @PreAuthorize("hasRole('ADMIN')")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        return "new-product";
    }

    @PostMapping("/admin/saveProduct")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveProduct(@Valid Product product, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) return "new-product";
        productRepository.save(product);
        return "redirect:/admin/newProduct";
    }


    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }

    @GetMapping("/notAuthorized")
    public String notAuthorized(){
        return "notAuthorized";
    }

}