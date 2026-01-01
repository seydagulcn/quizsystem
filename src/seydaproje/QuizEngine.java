package seydaproje;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * soru olusturan siniftir
 * su anda manuel sorular eklenmektedir
 * her bir kullanici farkli soru sirasi getirmektedir
 */
public class QuizEngine implements Gradable {

    private List<Question> sorular;
    private int dogruSayisi = 0;
    private int aktifSoruIndex = 0;
    private int toplamPuan = 0;
    private int maksimumPuan = 0;
    private List<String> kullaniciCevaplari = new ArrayList<>();
    private Boolean[] soruDogruMu;

    public QuizEngine() {
        sorular = new ArrayList<>();
        projeEkibiSorulariniYukle();
        maksimumPuaniHesapla();
        Collections.shuffle(sorular);

        for (int i = 0; i < sorular.size(); i++) {
            kullaniciCevaplari.add(null);
        }

        soruDogruMu = new Boolean[sorular.size()];
    }

    private void projeEkibiSorulariniYukle() {
        sorular.add(new MultipleChoiceQuestion(
                "Git'te yerel kodları sunucuya gönderen komut?",
                new String[]{"git pull", "git push", "git commit", "git add"},
                "git push", "Kolay"));

        sorular.add(new MultipleChoiceQuestion(
                "Java'da yeni nesne oluşturmak için kullanılan kelime?",
                new String[]{"class", "this", "new", "super"},
                "new", "Kolay"));

        sorular.add(new MultipleChoiceQuestion(
                "Veriyi 'private' yapıp metodlarla erişme ilkesine ne denir?",
                new String[]{"Kalıtım", "Polimorfizm", "Encapsulation", "Interface"},
                "Encapsulation", "Orta"));

        sorular.add(new MultipleChoiceQuestion(
                "Aşağıdakilerden hangisi bir 'Database' türüdür?",
                new String[]{"PostgreSQL", "HTML5", "CSS3", "JSON"},
                "PostgreSQL", "Orta"));

        sorular.add(new MultipleChoiceQuestion(
                "Kod tekrarını önleyen 'DRY' prensibinin açılımı?",
                new String[]{"Do Repeat Yearly", "Don't Repeat Yourself", "Data Run Yield", "Direct Real Yield"},
                "Don't Repeat Yourself", "Zor"));

        sorular.add(new MultipleChoiceQuestion(
                "Başkasının projesini kendi GitHub hesabına kopyalamaya ne denir?",
                new String[]{"Push", "Commit", "Fork", "Merge"},
                "Fork", "Orta"));

        sorular.add(new MultipleChoiceQuestion(
                "Tarayıcılarda etkileşim sağlayan temel programlama dili?",
                new String[]{"Java", "C++", "JavaScript", "Python"},
                "JavaScript", "Kolay"));

        sorular.add(new MultipleChoiceQuestion(
                "Hata ayıklama işlemine ne ad verilir?",
                new String[]{"Compiling", "Running", "Debugging", "Linking"},
                "Debugging", "Kolay"));

        sorular.add(new MultipleChoiceQuestion(
                "OOP'de 'extends' ne işe yarar?",
                new String[]{"Yeni nesne üretir", "Kalıtım (Inheritance) sağlar", "Hata fırlatır", "Kodu bitirir"},
                "Kalıtım (Inheritance) sağlar", "Zor"));

        sorular.add(new MultipleChoiceQuestion(
                "Linux çekirdeğinin yaratıcısı kimdir?",
                new String[]{"Steve Jobs", "Mark Zuckerberg", "Linus Torvalds", "Bill Gates"},
                "Linus Torvalds", "Orta"));

        sorular.add(new TrueFalseQuestion(
                "Java kodları JVM (Java Sanal Makinesi) sayesinde her işletim sisteminde çalışabilir.",
                "Doğru",
                "Kolay"));

        sorular.add(new TrueFalseQuestion(
                "Abstract (Soyut) sınıflardan 'new' anahtar kelimesi ile doğrudan nesne oluşturulabilir.",
                "Yanlış",
                "Zor"));

        sorular.add(new TrueFalseQuestion(
                "Java'da bir sınıf, birden fazla sınıfı aynı anda miras (extends) alabilir.",
                "Yanlış",
                "Orta"));

        sorular.add(new TrueFalseQuestion(
                "'Final' olarak tanımlanan bir değişkenin değeri sonradan kod içinde değiştirilebilir.",
                "Yanlış",
                "Kolay"));

        sorular.add(new TrueFalseQuestion(
                "Interface (Arayüz) içindeki metodlar varsayılan olarak 'public' erişime sahiptir.",
                "Doğru",
                "Zor"));
    }

    public Question getSoru(int index) {
        if (index >= 0 && index < sorular.size()) {
            return sorular.get(index);
        }
        return null;
    }

    public int getSoruSayisi() {
        return sorular.size();
    }

    public void cevapKaydet(int index, String cevap) {
        kullaniciCevaplari.set(index, cevap);
    }

    public String getKayitliCevap(int index) {
        return kullaniciCevaplari.get(index);
    }

    public void cevapGuncelle(int index, Question soru, String yeniCevap) {
        Boolean oncekiDurum = soruDogruMu[index];
        boolean simdiDogru = soru.checkAnswer(yeniCevap);

        if (Boolean.TRUE.equals(oncekiDurum) && !simdiDogru) {
            puanDus(soru);
        }

        if (!Boolean.TRUE.equals(oncekiDurum) && simdiDogru) {
            puanEkle(soru);
        }

        soruDogruMu[index] = simdiDogru;
    }

    private void puanEkle(Question soru) {
        if (soru.getZorlukSeviyesi().equals("Kolay")) {
            toplamPuan += 10;
        } else if (soru.getZorlukSeviyesi().equals("Orta")) {
            toplamPuan += 20;
        } else if (soru.getZorlukSeviyesi().equals("Zor")) {
            toplamPuan += 30;
        }
    }

    private void puanDus(Question soru) {
        if (soru.getZorlukSeviyesi().equals("Kolay")) {
            toplamPuan -= 10;
        } else if (soru.getZorlukSeviyesi().equals("Orta")) {
            toplamPuan -= 20;
        } else if (soru.getZorlukSeviyesi().equals("Zor")) {
            toplamPuan -= 30;
        }
    }

    @Override
    public int calculateScore() {
        return (toplamPuan * 100) / maksimumPuan;
    }

    public int calculateNormalizedScore() {
        if (maksimumPuan == 0) return 0;
        return (toplamPuan * 100) / maksimumPuan;
    }

    private void maksimumPuaniHesapla() {
        for (Question q : sorular) {
            if (q.getZorlukSeviyesi().equals("Kolay")) {
                maksimumPuan += 10;
            } else if (q.getZorlukSeviyesi().equals("Orta")) {
                maksimumPuan += 20;
            } else if (q.getZorlukSeviyesi().equals("Zor")) {
                maksimumPuan += 30;
            }
        }
    }

    public int toplamSureyiHesapla() {
        int toplamSure = 0;
        for (Question q : sorular) {
            if (q.getZorlukSeviyesi().equals("Kolay")) {
                toplamSure += 10;
            } else if (q.getZorlukSeviyesi().equals("Orta")) {
                toplamSure += 20;
            } else if (q.getZorlukSeviyesi().equals("Zor")) {
                toplamSure += 30;
            }
        }
        return toplamSure;
    }

    public String detayliRapor() {
        StringBuilder rapor = new StringBuilder();
        for (int i = 0; i < sorular.size(); i++) {
            Question q = sorular.get(i);
            String verilen = kullaniciCevaplari.get(i);

            rapor.append("Soru ").append(i + 1).append(": ")
                    .append(q.getSoruMetni())
                    .append("\nVerilen Cevap: ").append(verilen)
                    .append("\nDogru Cevap: ").append(q.dogruCevap);

            if (verilen != null && q.checkAnswer(verilen)) {
                rapor.append("\nSonuc: Dogru\n\n");
            } else {
                rapor.append("\nSonuc: Yanlis\n\n");
            }
        }
        return rapor.toString();
    }
}
