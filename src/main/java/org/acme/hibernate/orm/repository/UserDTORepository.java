package org.acme.hibernate.orm.repository;

import org.acme.hibernate.orm.domain.Event;
import org.acme.hibernate.orm.domain.Quiz;
import org.acme.hibernate.orm.domain.ReponseMessage;
import org.acme.hibernate.orm.domain.UserDTO;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class UserDTORepository implements IUserDTORepository {

    private static final Logger LOG = Logger.getLogger(UserDTORepository.class);

    @Inject
    EntityManager entityManager;

    @Override
    @Transactional
    public void createUserDTO(UserDTO userDTO) {
        entityManager.persist(userDTO);
    }

    @Override
    @Transactional
    public List<UserDTO> findAll() {
        return entityManager.createNamedQuery("UserDTO.findAll", UserDTO.class)
                .getResultList();
    }

    @Override
    @Transactional
    public UserDTO findByUserName(String userName) {
        return entityManager.createQuery("select u from UserDTO u " +
                "where u.username = '" + userName + "'" , UserDTO.class).getSingleResult();
    }
}
