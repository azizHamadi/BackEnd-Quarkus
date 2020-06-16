package org.acme.hibernate.orm.repository.Sondage.Impl;

import org.acme.hibernate.orm.domain.QuestionSondage;
import org.acme.hibernate.orm.domain.Sondage;
import org.acme.hibernate.orm.repository.QuizRepository;
import org.acme.hibernate.orm.repository.Sondage.ISondageRepository;
import org.jboss.logging.Logger;
import org.jose4j.json.internal.json_simple.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class SondageRepository implements ISondageRepository {

    private static final Logger LOG = Logger.getLogger(QuizRepository.class);

    @Inject
    EntityManager entityManager;

    @Inject
    QuestionSondageRepository questionSondageRepository;

    @Override
    public List<Sondage> findAll() {
        return entityManager.createNamedQuery("Sondage.findAll", Sondage.class)
                .getResultList();
    }

    @Override
    public Sondage findSondageById(Long id) {
        Sondage sondage = entityManager.find(Sondage.class, id);
        if (sondage == null) {
            throw new WebApplicationException("sondage with id of " + id + " does not exist.", 404);
        }
        return sondage;
    }

    @Override
    @Transactional
    public void createSondage(Sondage sondage) {
        entityManager.persist(sondage);
    }

    @Override
    @Transactional
    public void deleteSondage(Long id) {
        Sondage s = findSondageById(id);
        entityManager.remove(s);
    }

    @Override
    public List<JSONObject> findByEvent(Long id) {
        List<Sondage> sondages = entityManager.createQuery("select s from Sondage s " +
                "where s.status = true and s.event = " + id , Sondage.class).getResultList();
        List<JSONObject> sondagesObject = new ArrayList<>();
        sondages.forEach(s-> {
            List<QuestionSondage> questionSondages = this.questionSondageRepository.findBySondage(s.getId());
            JSONObject sondage = new JSONObject();
            sondage.put("sondage",s);
            sondage.put("questions", questionSondages);
            sondagesObject.add(sondage);
        });
        return sondagesObject ;
    }
}
