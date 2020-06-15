package org.acme.hibernate.orm.repository;


import org.acme.hibernate.orm.domain.Sondage;

import java.util.List;

public interface ISondageRepository {
    List<Sondage> findAll();
    Sondage findSondageById(Long id);
    void createSondage(Sondage sondage);
    void deleteSondage(Long id);
    List<Sondage> findByEvent(Long id);
}
