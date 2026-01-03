package seydaproje;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MultipleChoiceQuestionTest {

    @Test
    void correctAnswerShouldReturnTrue() {
        String[] secenekler = {"A", "B", "C", "D"};

        MultipleChoiceQuestion soru =
                new MultipleChoiceQuestion(
                        "Test sorusu",
                        secenekler,
                        "B",
                        "Kolay"
                );

        assertTrue(soru.checkAnswer("B"));
    }

    @Test
    void wrongAnswerShouldReturnFalse() {
        String[] secenekler = {"A", "B", "C", "D"};

        MultipleChoiceQuestion soru =
                new MultipleChoiceQuestion(
                        "Test sorusu",
                        secenekler,
                        "B",
                        "Kolay"
                );

        assertFalse(soru.checkAnswer("A"));
    }

    @Test
    void emptyAnswerShouldReturnFalse() {
        String[] secenekler = {"A", "B", "C", "D"};

        MultipleChoiceQuestion soru =
                new MultipleChoiceQuestion(
                        "Test sorusu",
                        secenekler,
                        "B",
                        "Kolay"
                );

        assertFalse(soru.checkAnswer("Boş"));
    }

    @Test
    void optionsShouldBeReturnedCorrectly() {
        String[] secenekler = {"A", "B", "C", "D"};

        MultipleChoiceQuestion soru =
                new MultipleChoiceQuestion(
                        "Test sorusu",
                        secenekler,
                        "B",
                        "Kolay"
                );

        assertArrayEquals(secenekler, soru.getSecenekler());
    }
}
