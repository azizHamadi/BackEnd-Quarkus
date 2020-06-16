package org.acme.hibernate.orm.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "word")
@NamedQuery(name = "Word.findAll",
        query = "SELECT w FROM Word w",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true") )
@Cacheable
public class Word implements Serializable {

    @Id
    @SequenceGenerator(
            name = "wordSequence",
            sequenceName = "words_id_seq",
            allocationSize = 1,
            initialValue = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wordsSequence")
    private Integer id;

    @ManyToOne
    UserDTO userDTO;

    @ManyToOne
    WordCloud wordCloud;

    @Column
    String word;

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public WordCloud getWordCloud() {
        return wordCloud;
    }

    public void setWordCloud(WordCloud wordCloud) {
        this.wordCloud = wordCloud;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "Word{" +
                "userDTO=" + userDTO +
                ", wordCloud=" + wordCloud +
                ", word='" + word + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return userDTO.equals(word1.userDTO) &&
                wordCloud.equals(word1.wordCloud) &&
                word.equals(word1.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userDTO, wordCloud, word);
    }
}
