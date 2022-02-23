package hu.ebanjo.ledshop.dbs.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hu.ebanjo.ledshop.dbs.model.MailTemplate;

@Repository
public interface MailTemplateRepository extends JpaRepository<MailTemplate, Long> {

    List<MailTemplate> findByCodeNameAndLangId(String tplCode, Long langId);

    @Query("SELECT mt FROM MailTemplate mt WHERE  mt.codeName = ?1 and mt.lang.lang in (?2, ?3) order by htmlBody desc, mt.lang.fallback")
    List<MailTemplate> getMailTpl(String tplCode, String lang, String defaLang);

}