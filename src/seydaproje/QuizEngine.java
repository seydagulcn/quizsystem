package seydaproje;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * soru olusturan siniftir su anda manuel sorular eklenmektedir her bir kullanici farkli soru sirasi getirmektedir
 */
public class QuizEngine implements Gradable {
    private List<Question> sorular;
    private int dogruSayisi = 0;
    private int aktifSoruIndex = 0;

    public QuizEngine() {
        sorular = new ArrayList<>();
        projeEkibiSorulariniYukle();
        Collections.shuffle(sorular); 
    }

    private void projeEkibiSorulariniYukle() {
        sorular.add(new MultipleChoiceQuestion("Git'te yerel kodları sunucuya gönderen komut?", new String[]{"git pull", "git push", "git commit", "git add"}, "git push", "Kolay"));
        sorular.add(new MultipleChoiceQuestion("Java'da yeni nesne oluşturmak için kullanılan kelime?", new String[]{"class", "this", "new", "super"}, "new", "Kolay"));
        sorular.add(new MultipleChoiceQuestion("Veriyi 'private' yapıp metodlarla erişme ilkesine ne denir?", new String[]{"Kalıtım", "Polimorfizm", "Encapsulation", "Interface"}, "Encapsulation", "Orta"));
        sorular.add(new MultipleChoiceQuestion("Aşağıdakilerden hangisi bir 'Database' türüdür?", new String[]{"PostgreSQL", "HTML5", "CSS3", "JSON"}, "PostgreSQL", "Orta"));
        sorular.add(new MultipleChoiceQuestion("Kod tekrarını önleyen 'DRY' prensibinin açılımı?", new String[]{"Do Repeat Yearly", "Don't Repeat Yourself", "Data Run Yield", "Direct Real Yield"}, "Don't Repeat Yourself", "Zor"));
        sorular.add(new MultipleChoiceQuestion("Başkasının projesini kendi GitHub hesabına kopyalamaya ne denir?", new String[]{"Push", "Commit", "Fork", "Merge"}, "Fork", "Orta"));
        sorular.add(new MultipleChoiceQuestion("Tarayıcılarda etkileşim sağlayan temel programlama dili?", new String[]{"Java", "C++", "JavaScript", "Python"}, "JavaScript", "Kolay"));
        sorular.add(new MultipleChoiceQuestion("Hata ayıklama işlemine ne ad verilir?", new String[]{"Compiling", "Running", "Debugging", "Linking"}, "Debugging", "Kolay"));
        sorular.add(new MultipleChoiceQuestion("OOP'de 'extends' ne işe yarar?", new String[]{"Yeni nesne üretir", "Kalıtım (Inheritance) sağlar", "Hata fırlatır", "Kodu bitirir"}, "Kalıtım (Inheritance) sağlar", "Zor"));
        sorular.add(new MultipleChoiceQuestion("Linux çekirdeğinin yaratıcısı kimdir?", new String[]{"Steve Jobs", "Mark Zuckerberg", "Linus Torvalds", "Bill Gates"}, "Linus Torvalds", "Orta"));
    }

    public Question sonrakiSoru() {
        if (aktifSoruIndex < sorular.size()) return sorular.get(aktifSoruIndex++);
        return null;
    }

    public void dogruCevapVerildi() { dogruSayisi++; }
/**
 * 
 * puan hesaplar (dogru sayisini 100le carpip soru sayisina boler)
 */
    @Override
    public int calculateScore() {
        return (dogruSayisi * 100) / sorular.size();
    }
}
