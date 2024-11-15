import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BoiteAuLettre {
    private String chLettre;
    private Boolean disponible = false;
    private BlockingQueue<String> tampon;

    public BoiteAuLettre(int capacite) {
        this.tampon = new ArrayBlockingQueue<>(capacite);
    }

    public synchronized void deposer(String lettre) throws InterruptedException {
        tampon.put(lettre); // Bloque si le tampon est plein
        System.out.println("BAL : Lettre déposée -> " + lettre);
    }
    public synchronized String retirer() throws InterruptedException {
        String lettre = tampon.take(); // Bloque si le tampon est vide
        System.out.println("BAL : Lettre retirée -> " + lettre);
        return lettre;
    }

}
