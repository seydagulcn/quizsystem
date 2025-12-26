package seydaproje;
/**
 * 
 * soru sinifi
 */
public abstract class Question {
    protected String soruMetni;
    protected String dogruCevap;
    protected String zorlukSeviyesi; 

    public Question(String soruMetni, String dogruCevap, String zorlukSeviyesi) {
        this.soruMetni = soruMetni;
        this.dogruCevap = dogruCevap;
        this.zorlukSeviyesi = zorlukSeviyesi;
    }

    public abstract boolean checkAnswer(String answer);
    
    public String getSoruMetni() { return soruMetni; }
    public String getZorlukSeviyesi() { return zorlukSeviyesi; }
}