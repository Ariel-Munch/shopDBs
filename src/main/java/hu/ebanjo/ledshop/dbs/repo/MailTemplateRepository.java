package hu.ebanjo.ledshop.dbs.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.ebanjo.ledshop.dbs.model.MailTemplate;

@Repository
public interface MailTemplateRepository extends JpaRepository<MailTemplate, Long> {

    List<MailTemplate> findByCodeNameAndLangId(String tplCode, Long langId);

}