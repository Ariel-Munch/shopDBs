package hu.ebanjo.ledshop.dbs.control;

import hu.ebanjo.ledshop.dbs.model.Picture;
import hu.ebanjo.ledshop.dbs.repo.PictureRepository;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/pix")
public class PictureController {
    

    private final PictureRepository pictureRepository;
    
    public PictureController(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    
        @GetMapping
        public List<Picture> getPictures() {
            return pictureRepository.findAll();
        }
    
        @GetMapping("/{id}")
        public Picture getPicture(@PathVariable Long id) {
            return pictureRepository.findById(id).orElseThrow(RuntimeException::new);
        }
    
        @PostMapping
        public ResponseEntity<Picture> createPicture(@RequestBody Picture picture) throws URISyntaxException {
            Picture savedPicture = pictureRepository.save(picture);
            return ResponseEntity.created(new URI("/pictures/" + Long.toString( savedPicture.getId() ))).body(savedPicture);
        }
    
        @PutMapping("/{id}")
        public ResponseEntity<Picture> updatePicture(@PathVariable Long id, @RequestBody Picture picture) {
            Picture currentPicture = pictureRepository.findById(id).orElseThrow(RuntimeException::new);
            currentPicture.setName(picture.getName()) ;
            // currentPicture.setName( picture.getName() );
            // currentPicture.setUrlDbApi( picture.getUrlDbApi()  );
            currentPicture = pictureRepository.save(picture);
    
            return ResponseEntity.ok(currentPicture);
        }
    
        @DeleteMapping("/{id}")
        public ResponseEntity<Picture> deletePicture(@PathVariable Long id) {
            pictureRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }    
}
