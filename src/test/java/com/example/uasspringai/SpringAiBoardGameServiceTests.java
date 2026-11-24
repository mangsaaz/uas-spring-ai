package com.example.uasspringai;

import java.util.List;
import org.springframework.ai.document.Document;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.evaluation.FactCheckingEvaluator;
import org.springframework.ai.chat.evaluation.RelevancyEvaluator;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringAiBoardGameServiceTests {

    @Autowired
    @Qualifier("springAiBoardGameService")
    private BoardGameService boardGameService;

    @Autowired
    private ChatClient.Builder chatClientBuilder;

    private RelevancyEvaluator relevancyEvaluator;
    private FactCheckingEvaluator factCheckingEvaluator;

    @BeforeEach
    public void setup() {
        this.relevancyEvaluator = new RelevancyEvaluator(chatClientBuilder);
        this.factCheckingEvaluator = new FactCheckingEvaluator(chatClientBuilder);
    }

    @Test
    public void evaluateRelevancy() {
        String userText = "Why is the sky blue?";
        Question question = new Question(userText);
        Answer answer = boardGameService.askQuestion(question);

        EvaluationRequest evaluationRequest = new EvaluationRequest(
                userText, answer.answer());
        var response = relevancyEvaluator.evaluate(evaluationRequest);

        Assertions.assertThat(response.isPass())
                .withFailMessage("""
                        ========================================
                        The answer "%s"
                        is not considered relevant to the question
                        "%s".
                        ========================================
                        """, answer.answer(), userText)
                .isTrue();
    }

  
    @Test
    public void evaluateFactualAccuracy() {
        var userText = "Why is the sky blue?";
        var question = new Question(userText);
        var answer = boardGameService.askQuestion(question);

        var referenceDocs = List.of(new Document(
                "The sky is blue because of Rayleigh scattering: molecules in "
                + "the atmosphere scatter shorter blue wavelengths of sunlight "
                + "more strongly than longer red wavelengths."));

        var evaluationRequest =
                new EvaluationRequest(userText, referenceDocs, answer.answer());
        var response = factCheckingEvaluator.evaluate(evaluationRequest);

        Assertions.assertThat(response.isPass())
        	.withFailMessage("""
                ========================================
                The answer "%s"
                is not considered correct for the question
                "%s".
                Feedback evaluator: "%s"
                ========================================
                """, answer.answer(), userText, response.getFeedback())
        	.isTrue();
    }
}