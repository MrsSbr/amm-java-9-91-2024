package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Answers {

    private List<Answer> answers;

    public Answers(int AnswerCount) {
        answers = new ArrayList<>();
        generateAnswers(AnswerCount);
    }

    public Answers(List<Answer> answerList) {
        answers = answerList;
    }

    private void generateAnswers(int AnswerCount)
    {
        Random random = new Random();
        for (int i = 0; i < AnswerCount; i++) {
            int age = random.nextInt(18, 80);
            answers.add(new Answer(age));
        }
    }

    public Answers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    public boolean removeAnswer(Answer answer) {
        return answers.remove(answer);
    }

    public boolean containsAnswer(Answer answer) {
        return answers.contains(answer);
    }

    public List<Answer> getAnswers() {
        return new ArrayList<>(answers);
    }

}
