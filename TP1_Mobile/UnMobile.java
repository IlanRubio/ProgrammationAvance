import java.awt.*;
import java.util.concurrent.Semaphore;
import javax.swing.*;

class UnMobile extends JPanel implements Runnable {
    int saLargeur, saHauteur, sonDebDessin;
    final int sonPas = 10,  sonCote = 40;
    int sonTemps = (int) (Math.random() * ((40 - 5) + 1)) + 5;
    static semaphoreGlobal sem = new semaphoreGlobal(1);

    UnMobile(int telleLargeur, int telleHauteur) {
        super();
        saLargeur = telleLargeur;
        saHauteur = telleHauteur;
        setSize(telleLargeur, telleHauteur);
    }

    public void run() {
        while (true) { //pour le faire tourner en permanence
            // du debut au premier tiers
            for (sonDebDessin = 0; sonDebDessin < saLargeur - sonPas; sonDebDessin += sonPas) {
                repaint();
                try {
                    Thread.sleep(sonTemps);
                } catch (InterruptedException telleExcp) {
                    telleExcp.printStackTrace();
                }
            }


            // premier tiers au deuxieme
            sem.syncWait();
            for (sonDebDessin = saLargeur; sonDebDessin < 2 * saLargeur - sonPas; sonDebDessin += sonPas) {
                repaint();
                try {
                    Thread.sleep(sonTemps);
                } catch (InterruptedException telleExcp) {
                    telleExcp.printStackTrace();
                }
            }
            sem.syncSignal();

            // deuxieme tiers à la fin
            for (sonDebDessin = 2 * saLargeur; sonDebDessin < 3 * saLargeur - sonPas; sonDebDessin += sonPas) {
                repaint();
                try {
                    Thread.sleep(sonTemps);
                } catch (InterruptedException telleExcp) {
                    telleExcp.printStackTrace();
                }
            }

            // fin au deuxieme tiers
            for (sonDebDessin = 3 * saLargeur - sonPas; sonDebDessin > 2 * saLargeur; sonDebDessin -= sonPas) {
                repaint();
                try {
                    Thread.sleep(sonTemps);
                } catch (InterruptedException telleExcp) {
                    telleExcp.printStackTrace();
                }
            }

            //deuxieme tiers au premier
            sem.syncWait();
            for (sonDebDessin = 2 * saLargeur - sonPas; sonDebDessin > saLargeur; sonDebDessin -= sonPas) {
                repaint();
                try {
                    Thread.sleep(sonTemps);
                } catch (InterruptedException telleExcp) {
                    telleExcp.printStackTrace();
                }
            }
            sem.syncSignal();

            //premier tiers au debut
            for (sonDebDessin = saLargeur - sonPas; sonDebDessin > 0; sonDebDessin -= sonPas) {
                repaint();
                try {
                    Thread.sleep(sonTemps);
                } catch (InterruptedException telleExcp) {
                    telleExcp.printStackTrace();
                }
            }
        }
    }

    public void paintComponent(Graphics telCG) {
        super.paintComponent(telCG);
        telCG.fillRect(sonDebDessin, saHauteur / 2, sonCote, sonCote);
    }
}