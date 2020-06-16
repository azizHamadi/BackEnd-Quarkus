package org.acme.hibernate.orm.repository.Sondage;


import org.acme.hibernate.orm.domain.Sondage;
import org.jose4j.json.internal.json_simple.JSONObject;

import java.util.List;

public interface ISondageRepository {
    List<Sondage> findAll();
    Sondage findSondageById(Long id);
    void createSondage(Sondage sondage);
    void deleteSondage(Long id);
    List<JSONObject> findByEvent(Long id);
}
