package org.acme.hibernate.orm.repository.Sondage;

import org.acme.hibernate.orm.domain.ReponseQuestionSondage;
import org.acme.hibernate.orm.domain.ReponseSondageUser;
import java.util.List;

public interface IReponseSondageUserRepository {

    /*List<ReponseSondageUser> getReponsebyQuestion(Question questionMessage, Long id);*/

    void addReponseSondageUser(ReponseSondageUser reponseSondageUser, Long id);

    void deleteReponseSondageUser(ReponseSondageUser reponseSondageUser, Long id);

}
