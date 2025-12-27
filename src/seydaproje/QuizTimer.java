package seydaproje;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.SwingUtilities;

public class QuizTimer {
    private int kalanSure;
    private Timer timer;

    public QuizTimer(int toplamSure) {
        this.kalanSure = toplamSure;
    }

    public void baslat(Runnable guncelle, Runnable bitti) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (kalanSure > 0) {
                    kalanSure--;
                    SwingUtilities.invokeLater(guncelle);
                } else {
                    durdur();
                    SwingUtilities.invokeLater(bitti);
                }
            }
        }, 0, 1000);
    }

    public void durdur() {
        if (timer != null) timer.cancel();
    }

    public String formatliSure() {
        return String.format("%02d:%02d", kalanSure / 60, kalanSure % 60);
    }
}
