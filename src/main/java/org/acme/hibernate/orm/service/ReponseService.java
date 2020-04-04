package org.acme.hibernate.orm.service;

import org.acme.hibernate.orm.domain.Reponse;

public interface ReponseService {

    Reponse update(Long id, Reponse reponse);

}