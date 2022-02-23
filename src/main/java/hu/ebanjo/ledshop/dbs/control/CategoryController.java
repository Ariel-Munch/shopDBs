package hu.ebanjo.ledshop.dbs.control;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import hu.ebanjo.ledshop.dbs.model.Category;
import hu.ebanjo.ledshop.dbs.repo.CategoryRepository;

@RestController
@CrossOrigin(origins= "*")
@RequestMapping("/categories")
public class CategoryController {
    
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private RestTemplate restTemplate;
    @Value("${app.raktaros.api.url:http://192.168.10.6:9990/api}") private URI RAKTAROS_API;


        @GetMapping
        public List<Category> getCategories() {
            return categoryRepository.findAll();
        }
        @GetMapping("/raktaros/get/all")
        public Category[] getCategoriesTimot() {
            return restTemplate.getForObject(RAKTAROS_API + "/category/get/all", Category[].class);
        }
    
        @GetMapping("/{id}")
        public Category getCategory(@PathVariable Long id) {
            return categoryRepository.findById(id).orElseThrow(RuntimeException::new);
        }
    
        @PostMapping
        public ResponseEntity<Category> createCategory(@RequestBody Category category) throws URISyntaxException {
            Category savedCategory = categoryRepository.save(category);
            return ResponseEntity.created(new URI("/categories/" + Long.toString( savedCategory.getId() ))).body(savedCategory);
        }
    
        @PutMapping("/{id}")
        public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
            Category currentCategory = categoryRepository.findById(id).orElseThrow(RuntimeException::new);
            currentCategory.builder()
                .name(category.getName())
                .title(category.getTitle())
                .build();
            // currentCategory.setName( category.getName() );
            // currentCategory.setUrlDbApi( category.getUrlDbApi()  );
            currentCategory = categoryRepository.save(category);
    
            return ResponseEntity.ok(currentCategory);
        }
    
        @DeleteMapping("/{id}")
        public ResponseEntity<Category> deleteCategory(@PathVariable Long id) {
            categoryRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }    
}
