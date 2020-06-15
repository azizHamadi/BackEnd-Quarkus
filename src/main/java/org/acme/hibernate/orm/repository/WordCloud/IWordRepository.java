package org.acme.hibernate.orm.repository.WordCloud;

import org.acme.hibernate.orm.domain.Word;
import org.acme.hibernate.orm.domain.WordCloud;
import java.util.List;

public interface IWordRepository {

    List<Word> getWordbyWordWloud(Long id);

    void addWord(Word word);

}
