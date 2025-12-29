package seydaproje;

public class TrueFalseQuestion extends Question {

    public TrueFalseQuestion(String soruMetni, String dogruCevap, String zorlukSeviyesi) {
        super(soruMetni, dogruCevap, zorlukSeviyesi);
    }

    @Override
    public boolean checkAnswer(String answer) {
        return answer.equalsIgnoreCase(dogruCevap);
    }

    @Override
    public String[] getSecenekler() {
        return new String[]{"Doğru", "Yanlış"};
    }
}