package com.codebricks.services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

    public class PerformanceService {

        //GET RECENT RESULTS
        public List<Document> getRecentResults(String userId) {
            MongoCollection<Document> results = DatabaseManager.getDatabase().getCollection("QuizResults");

            return results.find(Filters.eq("userId", userId))
                    .sort(Sorts.descending("timestamp"))
                    .limit(3)
                    .into(new ArrayList<>());
        }

        //GET TOTAL QUIZZES
        public int getTotalQuizzes(String userId) {
            MongoCollection<Document> results = DatabaseManager.getDatabase().getCollection("QuizResults");
            return (int) results.countDocuments(Filters.eq("userId", userId));
        }

        //GET BEST SCORE
        public int getBestScore(String userId) {
            MongoCollection<Document> results = DatabaseManager.getDatabase().getCollection("QuizResults");

            Document best = results.find(Filters.eq("userId", userId))
                    .sort(Sorts.descending("score"))
                    .limit(1)
                    .first();

            return best != null ? best.getInteger("score", 0) : 0;
        }
    }

