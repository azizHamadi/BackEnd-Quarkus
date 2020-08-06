package org.acme.hibernate.orm.service.Impl;

import org.acme.hibernate.orm.domain.ReponseFeedBackUser;
import org.acme.hibernate.orm.repository.Feedback.Impl.ReponseFeedBackUserRepository;
import org.acme.hibernate.orm.service.IReponseFeedBackUserService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ReponseFeedBackUserServiceImpl implements IReponseFeedBackUserService {

    @Inject
    ReponseFeedBackUserRepository reponseFeedBackUserRepository;

    @Override
    public List<ReponseFeedBackUser> findAll() {
        return reponseFeedBackUserRepository.findAll();
    }

    @Override
    public ReponseFeedBackUser findSingle(int id) {
        return reponseFeedBackUserRepository.findSingle(id);
    }

    @Override
    public void create(ReponseFeedBackUser reponseFeedBackUser) {
        reponseFeedBackUserRepository.create(reponseFeedBackUser);
    }

    @Override
    public List<ReponseFeedBackUser> findByUserQuestion(String idUser, int idevent) {
        return reponseFeedBackUserRepository.findByUserQuestion(idUser, idevent);
    }
}
