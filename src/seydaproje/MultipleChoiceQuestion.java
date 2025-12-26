package seydaproje;

public class MultipleChoiceQuestion extends Question {
    private String[] secenekler;

    public MultipleChoiceQuestion(String soru, String[] siklar, String dogru, String zorluk) {
        super(soru, dogru, zorluk);
        this.secenekler = siklar;
    }

    @Override
    public boolean checkAnswer(String answer) {
        return dogruCevap.equalsIgnoreCase(answer);
    }

    public String[] getSecenekler() { return secenekler; }
}
