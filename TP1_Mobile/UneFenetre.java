import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

class UneFenetre extends JFrame {
    UnMobile sonMobile;
    JButton sonButton;
    private final int LARG = 400, HAUT = 250;

    public UneFenetre() {
        // TODO
        super("le Mobile");
        Container leContainer = getContentPane();
        sonMobile = new UnMobile(LARG, HAUT);
        sonButton = new JButton("Start/Stop");

        leContainer.add(sonButton);
        leContainer.add(sonMobile);
        // creer une thread laThread avec sonMobile
        Thread laTache = new Thread(sonMobile);
        laTache.start();
        setSize(LARG, HAUT);
        setVisible(true);
        // lancer laThread
    }
}
