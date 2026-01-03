package seydaproje;

import javax.swing.*;
import java.awt.*;

/*
 * bu sinif; ogrenci girisini, sinav ekranini, sorularin goruntulenmesini,
 * zamanlayiciyi ve sinav sonucunu yonetir.
 *
 */
public class ExamSystemGUI extends JFrame {

    private QuizEngine motor = new QuizEngine();
    private QuizTimer sayac;
    private Student ogrenci = new Student();

    private JLabel lblSoru, lblSure, lblBilgi, lblSoruNumarasi;
    private JRadioButton[] secenekler = new JRadioButton[4];
    private ButtonGroup grup = new ButtonGroup();

    private Question suAnkiSoru;
    private int aktifSoruIndex = 0;

    public ExamSystemGUI() {
        girisKontrolu();
    }

    private void girisKontrolu() {
        String numara = JOptionPane.showInputDialog(
                null,
                "Arel Yazılım Kulübü - Proje Ekibi Seçmeleri\nÖğrenci Numaranızı Giriniz:",
                "Sinav Giriş Sistemi",
                JOptionPane.PLAIN_MESSAGE
        );

        if (numara == null) {
            System.exit(0);
        }

        if (ogrenci.kimlikDogrula(numara).equals("true")) {
            if (bilgiEkraniGoster()) {
                anaEkran();
            } else {
                System.exit(0);
            }
        } else {
            JOptionPane.showMessageDialog(null, ogrenci.kimlikDogrula(numara));
            girisKontrolu();
        }
    }

    private boolean bilgiEkraniGoster() {
        int soruSayisi = motor.getSoruSayisi();
        int toplamSure = motor.toplamSureyiHesapla();

        String metin =
                "Sınav Bilgilendirme\n\n" +
                "Toplam Soru Sayısı: " + soruSayisi + "\n" +
                "Toplam Süre: " + (toplamSure / 60) + " dakika\n\n" +
                "• Sorular arasında ileri ve geri geçiş yapabilirsiniz.\n" +
                "• Sorular boş bırakılabilir.\n" +
                "• Süre dolduğunda sınav otomatik olarak sonlandırılır.\n\n" +
                "Sınavı başlatmak istiyor musunuz?";

        int secim = JOptionPane.showConfirmDialog(
                null,
                metin,
                "Sınav Bilgilendirme",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE
        );

        return secim == JOptionPane.YES_OPTION;
    }

    /*
     * sinavin ana arayuz bilesenleri burada eklenir
     *
     */
    private void anaEkran() {
        int toplamSure = motor.toplamSureyiHesapla();
        sayac = new QuizTimer(toplamSure);

        setTitle("Arel Software Club - Project Team Selection");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        JPanel ustPanel = new JPanel(new GridLayout(1, 3));
        lblBilgi = new JLabel(" Proje Ekibi Yetkinlik Testi");
        lblSoruNumarasi = new JLabel("", SwingConstants.CENTER);
        lblSure = new JLabel("Kalan Süre: " + sayac.formatliSure(), SwingConstants.RIGHT);

        ustPanel.add(lblBilgi);
        ustPanel.add(lblSoruNumarasi);
        ustPanel.add(lblSure);
        add(ustPanel, BorderLayout.NORTH);

        JPanel ortaPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        lblSoru = new JLabel();
        lblSoru.setFont(new Font("Arial", Font.BOLD, 14));
        ortaPanel.add(lblSoru);

        for (int i = 0; i < 4; i++) {
            secenekler[i] = new JRadioButton();
            grup.add(secenekler[i]);
            ortaPanel.add(secenekler[i]);
        }

        add(ortaPanel, BorderLayout.CENTER);

        JPanel altPanel = new JPanel(new FlowLayout());
        JButton btnOnceki = new JButton("Önceki soruya don");
        JButton btnOnayla = new JButton("Onayla ve İlerle");

        altPanel.add(btnOnceki);
        altPanel.add(btnOnayla);
        add(altPanel, BorderLayout.SOUTH);

        btnOnayla.addActionListener(e -> ileriSoru());
        btnOnceki.addActionListener(e -> geriSoru());

        sayac.baslat(
                () -> lblSure.setText("Kalan Süre: " + sayac.formatliSure()),
                this::testiBitir
        );

        suAnkiSoru = motor.getSoru(aktifSoruIndex);
        soruGoster();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void soruGoster() {
        if (suAnkiSoru == null) return;

        lblSoru.setText("  " + suAnkiSoru.getSoruMetni());
        lblBilgi.setText(" Zorluk: " + suAnkiSoru.getZorlukSeviyesi());
        lblSoruNumarasi.setText("Soru " + (aktifSoruIndex + 1) + " / " + motor.getSoruSayisi());

        String[] siklar = suAnkiSoru.getSecenekler();

        for (int i = 0; i < 4; i++) {
            if (i < siklar.length) {
                secenekler[i].setText(siklar[i]);
                secenekler[i].setVisible(true);
            } else {
                secenekler[i].setVisible(false);
            }
        }

        grup.clearSelection();
        String kayitli = motor.getKayitliCevap(aktifSoruIndex);

        if (kayitli != null) {
            for (JRadioButton rb : secenekler) {
                if (rb.getText().equals(kayitli)) {
                    rb.setSelected(true);
                }
            }
        }
    }

    private void ileriSoru() {
        boolean secildi = false;

        for (JRadioButton rb : secenekler) {
            if (rb.isSelected()) {
                secildi = true;
                motor.cevapKaydet(aktifSoruIndex, rb.getText());
                motor.cevapGuncelle(aktifSoruIndex, suAnkiSoru, rb.getText());
            }
        }

        if (!secildi) {
            motor.cevapKaydet(aktifSoruIndex, "Boş");
            motor.cevapGuncelle(aktifSoruIndex, suAnkiSoru, "Boş");
        }

        aktifSoruIndex++;

        if (aktifSoruIndex < motor.getSoruSayisi()) {
            suAnkiSoru = motor.getSoru(aktifSoruIndex);
            soruGoster();
        } else {
            testiBitir();
        }
    }

    private void geriSoru() {
        if (aktifSoruIndex > 0) {
            aktifSoruIndex--;
            suAnkiSoru = motor.getSoru(aktifSoruIndex);
            soruGoster();
        }
    }

    private void testiBitir() {
        sayac.durdur();

        int puan = motor.calculateNormalizedScore();

        String mesaj = puan >= 70
                ? "TEBRİKLER! Proje ekibine girmeye hak kazandınız."
                : "Teknik seviyeniz proje ekibi için şu an yeterli değil.";

        JTextArea alan = new JTextArea(motor.detayliRapor());
        alan.setEditable(false);
        alan.setLineWrap(true);
        alan.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(alan);
        scroll.setPreferredSize(new Dimension(550, 300));

        JOptionPane.showMessageDialog(
                this,
                "Test Raporu\nÖğrenci: " + ogrenci.getOgrenciNo()
                        + "\nBaşarı Yüzdesi: %" + puan
                        + "\nKarar: " + mesaj
        );

        JOptionPane.showMessageDialog(
                this,
                scroll,
                "Sınav Detay Raporu",
                JOptionPane.INFORMATION_MESSAGE
        );

        System.exit(0);
    }
}
