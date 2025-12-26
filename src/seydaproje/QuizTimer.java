package seydaproje;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 *  bu sinif quiz suresini belirler
 */
public class QuizTimer {
    private int kalanSure = 300; 
    private Timer timer;

    /**
     *
     * quizi baslattiginda zamanlayici da baslatir
     * @param guncelle
     * @param bitti
     */
    public void baslat(Runnable guncelle, Runnable bitti) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (kalanSure > 0) {
                    kalanSure--;
                    guncelle.run();
                } else {
                    durdur();
                    bitti.run();
                }
            }
        }, 0, 1000);
    }

    /**
     *
     * durdur methodu quizi bitirir
     */
    public void durdur() { if (timer != null) timer.cancel(); }

    /**
     *
     * sureyi formatlar
     * @return
     */
    public String formatliSure() { return String.format("%02d:%02d", kalanSure/60, kalanSure%60); }
}
