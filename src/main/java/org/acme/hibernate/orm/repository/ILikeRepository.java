package org.acme.hibernate.orm.repository;

import org.acme.hibernate.orm.domain.Aime;
import org.acme.hibernate.orm.domain.QuestionMessage;
import org.acme.hibernate.orm.domain.UserDTO;

import javax.transaction.Transactional;
import java.util.List;

public interface ILikeRepository {

    List<Aime> getLikebyQuestion(QuestionMessage questionMessage, Long id);

    void addLike(Aime like, Long id);

    void deleteLike(Aime like, Long id);

}
