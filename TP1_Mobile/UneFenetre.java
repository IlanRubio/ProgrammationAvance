import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

class UneFenetre extends JFrame {
    UnMobile sonMobile;
    UnMobile sonMobile1;
    JButton sonButton;
    JButton sonButton1;
    private final int LARG = 400, HAUT = 250;

    public UneFenetre() {
        // TODO
        super("le Mobile");
        Container leContainer = getContentPane();
        leContainer.setLayout(new GridLayout(2, 1));
        sonMobile = new UnMobile(LARG, HAUT);
        //sonButton = new JButton("Start/Stop");
        leContainer.add(sonMobile);
        //leContainer.add(sonButton);
        // creer une thread laThread avec sonMobile
        Thread laTache = new Thread(sonMobile);
        laTache.start();

        sonMobile1 = new UnMobile(LARG, HAUT);
        // sonButton1 = new JButton("Start/Stop");
        // leContainer.add(sonButton1);
        leContainer.add(sonMobile1);
        Thread laTache1 = new Thread(sonMobile1);
        laTache1.start();

        setSize(LARG, HAUT);
        setVisible(true);
        // lancer laThread
    }
}
