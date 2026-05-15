package com.codebricks.services;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.Date;

public class QuizResultService {

    /* ---------- SAVE RESULT ---------- */
    public void saveResult(String userId, String difficulty, int score,
                           int totalQuestions, int correctAnswers) {
        MongoCollection<Document> results = DatabaseManager.getDatabase().getCollection("QuizResults");

        double accuracy = ((double) correctAnswers / totalQuestions) * 100;
        //We were accidentally saved the score as the correctAnswers
        //and calculated accuracy wrong.
        // we fixed the math and passed the right values to the right fields.

        Document result = new Document("userId", userId)
                .append("difficulty", difficulty)
                .append("score", score)
                .append("totalQuestions", totalQuestions)
                .append("correctAnswers", correctAnswers)
                .append("accuracy", accuracy)
                .append("timestamp", new Date());

        results.insertOne(result);
    }

}