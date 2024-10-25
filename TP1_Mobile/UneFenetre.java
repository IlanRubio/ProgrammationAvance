import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

class UneFenetre extends JFrame {
    UnMobile sonMobile;
    UnMobile sonMobile1;
    UnMobile sonMobile2;
    private final int LARG = 400, HAUT = 250;

    public UneFenetre() {
        // TODO
        super("le Mobile");
        Container leContainer = getContentPane();
        leContainer.setLayout(new GridLayout(3, 1));
        sonMobile = new UnMobile(LARG, HAUT);
        leContainer.add(sonMobile);

        // creer une thread laThread avec sonMobile
        Thread laTache = new Thread(sonMobile);
        laTache.start();

        sonMobile1 = new UnMobile(LARG, HAUT);
        leContainer.add(sonMobile1);
        Thread laTache1 = new Thread(sonMobile1);
        laTache1.start();

        sonMobile2 = new UnMobile(LARG, HAUT);
        leContainer.add(sonMobile2);
        Thread laTache2 = new Thread(sonMobile2);
        laTache2.start();

        setSize(3000, 2500);
        setVisible(true);
        // lancer laThread
    }
}
