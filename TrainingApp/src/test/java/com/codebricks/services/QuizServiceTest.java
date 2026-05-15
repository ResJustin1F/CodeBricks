package com.codebricks.services;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class QuizServiceTest {

    @Test
    public void testQuestionsNotEmpty() {
        QuizService service = new QuizService();
        List<QuizService.Question> questions = service.getQuestions("easy");
        assertFalse(questions.isEmpty());
    }

    @Test
    public void testQuestionLimitIsFive() {
        QuizService service = new QuizService();
        List<QuizService.Question> questions = service.getQuestions("easy");
        assertTrue(questions.size() <= 5);
    }

    @Test
    public void testQuestionsMatchDifficulty() {
        QuizService service = new QuizService();
        List<QuizService.Question> questions = service.getQuestions("hard");
        for (QuizService.Question q : questions) {
            assertEquals("hard", q.difficulty.toLowerCase());
        }
    }
}