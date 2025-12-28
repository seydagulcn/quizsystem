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

    private int aktifIndex = 0;

    public ExamSystemGUI() {
        girisKontrolu();
    }

    private void girisKontrolu() {
        String numara = JOptionPane.showInputDialog(null,
                "Arel Yazılım Kulübü - Proje Ekibi Seçmeleri\nÖğrenci Numaranızı Giriniz:");

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
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel ust = new JPanel(new GridLayout(1, 2));
        lblBilgi = new JLabel(" Proje Ekibi Yetkinlik Testi");
        lblSure = new JLabel("", SwingConstants.RIGHT);
        ust.add(lblBilgi);
        ust.add(lblSure);
        add(ust, BorderLayout.NORTH);

        JPanel orta = new JPanel(new GridLayout(6, 1));
        lblSoru = new JLabel();
        orta.add(lblSoru);

        for (int i = 0; i < 4; i++) {
            secenekler[i] = new JRadioButton();
            grup.add(secenekler[i]);
            orta.add(secenekler[i]);
        }
        add(orta, BorderLayout.CENTER);

        JPanel alt = new JPanel(new GridLayout(1, 2));
        JButton btnOnceki = new JButton("Önceki");
        JButton btnSonraki = new JButton("Sonraki");
        alt.add(btnOnceki);
        alt.add(btnSonraki);
        add(alt, BorderLayout.SOUTH);

        btnOnceki.addActionListener(e -> {
            cevapKaydet();
            if (aktifIndex > 0) {
                aktifIndex--;
                soruYukle();
            }
        });

        btnSonraki.addActionListener(e -> {
            cevapKaydet();
            if (aktifIndex < motor.getSoruSayisi() - 1) {
                aktifIndex++;
                soruYukle();
            } else {
                testiBitir();
            }
        });

        sayac.baslat(
                () -> lblSure.setText("Kalan Süre: " + sayac.formatliSure()),
                this::testiBitir
        );

        soruYukle();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void soruYukle() {
        Question q = motor.getSoru(aktifIndex);
        lblSoru.setText(q.getSoruMetni());
        lblBilgi.setText("Zorluk: " + q.getZorlukSeviyesi());

        grup.clearSelection();
        String[] siklar = ((MultipleChoiceQuestion) q).getSecenekler();

        for (int i = 0; i < 4; i++) {
            secenekler[i].setText(siklar[i]);
            if (siklar[i].equals(motor.getKayitliCevap(aktifIndex))) {
                secenekler[i].setSelected(true);
            }
        }
    }

    private void cevapKaydet() {
        for (JRadioButton rb : secenekler) {
            if (rb.isSelected()) {
                motor.cevapKaydet(aktifIndex, rb.getText());
            }
        }
    }

    private void testiBitir() {
        sayac.durdur();
        int puan = motor.calculateNormalizedScore();

        JTextArea alan = new JTextArea(motor.detayliRapor());
        alan.setEditable(false);
        JScrollPane scroll = new JScrollPane(alan);
        scroll.setPreferredSize(new Dimension(550, 300));

        JOptionPane.showMessageDialog(this,
                "Öğrenci: " + ogrenci.getOgrenciNo()
                        + "\nBaşarı Yüzdesi: %" + puan);

        JOptionPane.showMessageDialog(this, scroll, "Sınav Detay Raporu",
                JOptionPane.INFORMATION_MESSAGE);

        System.exit(0);
    }
}
