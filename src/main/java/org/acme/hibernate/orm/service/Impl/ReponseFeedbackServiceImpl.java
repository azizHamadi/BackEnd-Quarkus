package org.acme.hibernate.orm.service.Impl;

import org.acme.hibernate.orm.domain.ReponseFeedback;
import org.acme.hibernate.orm.repository.Feedback.Impl.ReponseFeedbackRepository;
import org.acme.hibernate.orm.service.IReponseFeedbackService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ReponseFeedbackServiceImpl implements IReponseFeedbackService {

    @Inject
    ReponseFeedbackRepository reponseFeedbackRepository;

    @Override
    public List<ReponseFeedback> findAll() {
        return reponseFeedbackRepository.findAll();
    }

    @Override
    public ReponseFeedback findSingle(int id) {
        return reponseFeedbackRepository.findSingle(id);
    }

    @Override
    public void create(ReponseFeedback reponseFeedback) {
        reponseFeedbackRepository.create(reponseFeedback);
    }
}