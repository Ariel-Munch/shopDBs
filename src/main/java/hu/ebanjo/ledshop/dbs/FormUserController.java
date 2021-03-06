package hu.ebanjo.ledshop.dbs;

import hu.ebanjo.ledshop.dbs.model.Shopuser;
import hu.ebanjo.ledshop.dbs.repo.UserRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class FormUserController {

    private final UserRepository userRepository;

    @Autowired
    public FormUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
        
    @GetMapping("/signup")
    public String showSignUpForm(Shopuser user) {
        return "add-user";
    }
    
    @PostMapping("/adduser")
    public String addUser(@Valid Shopuser user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }
        userRepository.save(user);
        return "redirect:/index";
    }

    // additional CRUD methods

    @GetMapping("/index")
    public String showUserList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "index";
    }
    
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Shopuser user = userRepository.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        
        model.addAttribute("user", user);
        return "update-user";
    }
    
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid Shopuser user, 
      BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "update-user";
        }
            
        userRepository.save(user);
        return "redirect:/index";
    }
        
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        Shopuser user = userRepository.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        return "redirect:/index";
    }    
}