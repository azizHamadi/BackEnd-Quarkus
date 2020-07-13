package org.acme.hibernate.orm.repository.Feedback;

import org.acme.hibernate.orm.domain.ReponseFeedback;
import java.util.List;

public interface IReponseFeedbackRepository {
    List<ReponseFeedback> findAll();
    ReponseFeedback findSingle(int id);
    void create(ReponseFeedback reponseFeedback);
}
