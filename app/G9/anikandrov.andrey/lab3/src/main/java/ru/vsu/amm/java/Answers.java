package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Answers {

    private List<Reply> answers;

    public Answers(int AnswerCount) {
        answers = new ArrayList<>();
        generateAnswers(AnswerCount);
    }

    public Answers(List<Reply> replyList) {
        answers = replyList;
    }

    private void generateAnswers(int AnswerCount) {
        Random random = new Random();
        for (int i = 0; i < AnswerCount; i++) {
            int age = random.nextInt(18, 80);
            answers.add(new Reply(age));
        }
    }

    public Answers(ArrayList<Reply> answers) {
        this.answers = answers;
    }

    public void addAnswer(Reply reply) {
        answers.add(reply);
    }

    public boolean removeAnswer(Reply reply) {
        return answers.remove(reply);
    }

    public boolean containsAnswer(Reply reply) {
        return answers.contains(reply);
    }

    public List<Reply> getAnswers() {
        return new ArrayList<>(answers);
    }

}
