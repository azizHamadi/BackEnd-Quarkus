package org.acme.hibernate.orm;

public enum PollEnum {
    QUIZ {
        @Override
        public String toString(){
            return "quiz";
        }
    },
    WORDCLOUD {
        @Override
        public String toString(){
            return "wordCloud";
        }
    },
    QUESTION {
        @Override
        public String toString(){
            return "question";
        }
    },
    SONDAGE {
        @Override
        public String toString(){
            return "sondage";
        }
    },
    FEEDBACK {
        @Override
        public String toString(){
            return "feedback";
        }
    }
}
