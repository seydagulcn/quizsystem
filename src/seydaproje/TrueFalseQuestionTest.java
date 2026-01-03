package seydaproje;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TrueFalseQuestionTest {

    @Test
    void correctAnswerShouldReturnTrue() {
        TrueFalseQuestion soru =
                new TrueFalseQuestion(
                        "Java platform bağımsızdır",
                        "Doğru",
                        "Kolay"
                );

        assertTrue(soru.checkAnswer("Doğru"));
    }

    @Test
    void wrongAnswerShouldReturnFalse() {
        TrueFalseQuestion soru =
                new TrueFalseQuestion(
                        "Java platform bağımsızdır",
                        "Doğru",
                        "Kolay"
                );

        assertFalse(soru.checkAnswer("Yanlış"));
    }

    @Test
    void emptyAnswerShouldReturnFalse() {
        TrueFalseQuestion soru =
                new TrueFalseQuestion(
                        "Java platform bağımsızdır",
                        "Doğru",
                        "Kolay"
                );

        assertFalse(soru.checkAnswer("Boş"));
    }

    @Test
    void optionsShouldBeTrueAndFalse() {
        TrueFalseQuestion soru =
                new TrueFalseQuestion(
                        "Java platform bağımsızdır",
                        "Doğru",
                        "Kolay"
                );

        String[] expected = {"Doğru", "Yanlış"};

        assertArrayEquals(expected, soru.getSecenekler());
    }
}
