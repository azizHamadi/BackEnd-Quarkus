package org.acme.hibernate.orm.repository.WordCloud.Impl;

import org.acme.hibernate.orm.domain.Aime;
import org.acme.hibernate.orm.domain.QuestionMessage;
import org.acme.hibernate.orm.domain.Word;
import org.acme.hibernate.orm.domain.WordCloud;
import org.acme.hibernate.orm.repository.QuestionMessageRepository;
import org.acme.hibernate.orm.repository.WordCloud.IWordRepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class WordRepository implements IWordRepository {

    private static final Logger LOGGER = Logger.getLogger(WordRepository.class.getName());

    @Inject
    EntityManager entityManager;

    @Override
    public List<Word> getWordbyWordWloud(Long id) {
        return  entityManager.createQuery("select w from Word w " +
                "where w.wordCloud = " + id , Word.class).getResultList();
    }

    @Override
    @Transactional
    public void addWord(Word word) {
        entityManager.persist(word);
    }
}
