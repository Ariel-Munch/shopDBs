package hu.ebanjo.ledshop.dbs.control;

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

import hu.ebanjo.ledshop.dbs.model.Session;
import hu.ebanjo.ledshop.dbs.repo.SessionRepository;

@RestController
@RequestMapping("/sessions")
public class SessionController {
    
        private final SessionRepository sessionRepository;
    
        public SessionController(SessionRepository sessionRepository) {
            this.sessionRepository = sessionRepository;
        }

        @GetMapping
        public List<Session> getSessions() {
            return sessionRepository.findAll();
        }
    
        @GetMapping("/{id}")
        public Session getSession(@PathVariable Long id) {
            return sessionRepository.findById(id).orElseThrow(RuntimeException::new);
        }
    
        @PostMapping
        public ResponseEntity<Session> createSession(@RequestBody Session session) throws URISyntaxException {
            Session savedSession = sessionRepository.save(session);
            return ResponseEntity.created(new URI("/sessions/" + Long.toString( savedSession.getId() ))).body(savedSession);
        }
    
        @PutMapping("/{id}")
        public ResponseEntity<Session> updateSession(@PathVariable Long id, @RequestBody Session session) {
            Session currentSession = sessionRepository.findById(id).orElseThrow(RuntimeException::new);
            currentSession.builder()
                .token(session.getToken())
                .build();
            // currentSession.setName( session.getName() );
            // currentSession.setUrlDbApi( session.getUrlDbApi()  );
            currentSession = sessionRepository.save(session);
    
            return ResponseEntity.ok(currentSession);
        }
    
        @DeleteMapping("/{id}")
        public ResponseEntity<Session> deleteSession(@PathVariable Long id) {
            sessionRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }    
}
