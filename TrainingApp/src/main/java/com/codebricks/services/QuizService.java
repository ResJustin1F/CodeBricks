package com.codebricks.services;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizService {

    public static class Question {
        public final String questionText;
        public final List<String> options;
        public final String correctAnswer;
        public final String difficulty;
        public final String topic;

        public Question(String questionText, List<String> options,
                        String correctAnswer, String difficulty, String topic) {
            this.questionText = questionText;
            this.options = options;
            this.correctAnswer = correctAnswer;
            this.difficulty = difficulty;
            this.topic = topic;
        }
    }

    /**
     * Loads 5 randomized questions matching the given difficulty from questions.json
     * TODO: replace with MongoDB query when backend is ready
     */
    public List<Question> getQuestions(String difficulty) {
        List<Question> all = new ArrayList<>();

        try {
            InputStream is = getClass().getResourceAsStream("/data/questions.json");
            if (is == null) {
                System.err.println("QuizService: questions.json not found in resources");
                return all;
            }

            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(new InputStreamReader(is));

            for (Object obj : jsonArray) {
                JSONObject q = (JSONObject) obj;
                String diff = (String) q.get("difficulty");

                if (diff != null && diff.equalsIgnoreCase(difficulty)) {
                    String questionText = (String) q.get("questionText");
                    String correctAnswer = (String) q.get("correctAnswer");
                    String topic = (String) q.get("topic");

                    JSONArray optionsArray = (JSONArray) q.get("options");
                    List<String> options = new ArrayList<>();
                    for (Object opt : optionsArray) {
                        options.add((String) opt);
                    }

                    all.add(new Question(questionText, options, correctAnswer, diff, topic));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Shuffle and return 5
        Collections.shuffle(all);
        return all.size() > 5 ? all.subList(0, 5) : all;
    }
}