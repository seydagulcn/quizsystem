package seydaproje;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuizEngineTest {

    @Test
    void baslangictaPuanSifirOlmali() {
        QuizEngine engine = new QuizEngine();
        assertEquals(0, engine.calculateNormalizedScore());
    }

    @Test
    void yanlisCevapPuanArtirmamali() {
        QuizEngine engine = new QuizEngine();
        Question q = engine.getSoru(0);

        engine.cevapGuncelle(0, q, "KESIN_YANLIS");

        assertEquals(0, engine.calculateNormalizedScore());
    }

    @Test
    void ayniSoruyaIkiFarkliCevapSistemCokertmemeli() {
        QuizEngine engine = new QuizEngine();
        Question q = engine.getSoru(0);

        engine.cevapGuncelle(0, q, "YANLIS_1");
        int puan1 = engine.calculateNormalizedScore();

        engine.cevapGuncelle(0, q, "YANLIS_2");
        int puan2 = engine.calculateNormalizedScore();

        assertEquals(puan1, puan2);
    }

    @Test
    void skorHerZaman0Ile100ArasindaOlmali() {
        QuizEngine engine = new QuizEngine();

        for (int i = 0; i < engine.getSoruSayisi(); i++) {
            engine.cevapGuncelle(i, engine.getSoru(i), "RANDOM");
        }

        int skor = engine.calculateNormalizedScore();
        assertTrue(skor >= 0 && skor <= 100);
    }
}
