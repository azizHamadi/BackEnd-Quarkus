package org.acme.hibernate.orm.repository;

import org.acme.hibernate.orm.domain.Quiz;
import org.acme.hibernate.orm.domain.Sondage;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.util.List;

@ApplicationScoped
public class SondageRepository implements ISondageRepository {

    private static final Logger LOG = Logger.getLogger(QuizRepository.class);

    @Inject
    EntityManager entityManager;

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
    public List<Sondage> findByEvent(Long id) {
        List<Sondage> sondage = entityManager.createQuery("select s from Sondage s " +
                "where s.event = " + id , Sondage.class).getResultList();
        return sondage ;
    }
}
