package seydaproje;

import javax.swing.*;
import java.awt.*;

public class ExamSystemGUI extends JFrame {
    private QuizEngine motor = new QuizEngine();
    private QuizTimer sayac;
    private Student ogrenci = new Student();
    private JLabel lblSoru, lblSure, lblBilgi;
    private JRadioButton[] secenekler = new JRadioButton[4];
    private ButtonGroup grup = new ButtonGroup();
    private Question suAnkiSoru;

    public ExamSystemGUI() {
        girisKontrolu();
    }

    private void girisKontrolu() {
        String numara = JOptionPane.showInputDialog(
                null,
                "Arel Yazılım Kulübü - Proje Ekibi Seçmeleri\nÖğrenci Numaranızı Giriniz:",
                "Yönetici Giriş Sistemi",
                JOptionPane.PLAIN_MESSAGE
        );

        if (ogrenci.kimlikDogrula(numara).equals("true")) {
            anaEkran();
        } else {
            JOptionPane.showMessageDialog(null, ogrenci.kimlikDogrula(numara));
            girisKontrolu();
        }
    }

    private void anaEkran() {
        int toplamSure = motor.toplamSureyiHesapla();
        sayac = new QuizTimer(toplamSure);

        setTitle("Arel Software Club - Project Team Selection");
        setSize(650, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        JPanel ustPanel = new JPanel(new GridLayout(1, 2));
        lblBilgi = new JLabel(" Proje Ekibi Yetkinlik Testi");
        lblSure = new JLabel("Kalan Süre: " + sayac.formatliSure(), SwingConstants.RIGHT);
        ustPanel.add(lblBilgi);
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

        JButton btnOnayla = new JButton("Onayla ve İlerle");
        add(btnOnayla, BorderLayout.SOUTH);

        btnOnayla.addActionListener(e -> soruGecisIslemi());

        sayac.baslat(
                () -> lblSure.setText("Kalan Süre: " + sayac.formatliSure()),
                this::testiBitir
        );

        soruGecisIslemi();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void soruGecisIslemi() {
        if (suAnkiSoru != null) {
            boolean cevapVerildi = false;

            for (JRadioButton rb : secenekler) {
                if (rb.isSelected()) {
                    cevapVerildi = true;
                    motor.cevapKaydet(rb.getText());

                    if (suAnkiSoru.checkAnswer(rb.getText())) {
                        motor.dogruCevapVerildi(suAnkiSoru);
                    }
                }
            }

            if (!cevapVerildi) {
                motor.cevapKaydet("Boş");
            }
        }

        suAnkiSoru = motor.sonrakiSoru();
        if (suAnkiSoru != null) {
            lblSoru.setText("  " + suAnkiSoru.getSoruMetni());
            lblBilgi.setText(" Zorluk: " + suAnkiSoru.getZorlukSeviyesi());
            String[] siklar = ((MultipleChoiceQuestion) suAnkiSoru).getSecenekler();
            for (int i = 0; i < 4; i++) {
                secenekler[i].setText(siklar[i]);
            }
            grup.clearSelection();
        } else {
            testiBitir();
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
                "Yönetici Raporu\nÖğrenci: " + ogrenci.getOgrenciNo()
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
