package com.paveltinnik.webquizengine.engine.entities;

import java.util.List;

// Класс, представляющий ответ на quiz в теле запроса, который посылает user
public class AnswerFromUser {
    private List<Integer> answer;

    public AnswerFromUser() {}

    public AnswerFromUser(List<Integer> answer) {
        this.answer = answer;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "AnswerFromUser{" +
                "answer=" + answer +
                '}';
    }
}
