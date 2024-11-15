import java.util.Scanner;

public class Producteur implements Runnable {
    private final BoiteAuLettre bal;

    public Producteur(BoiteAuLettre bal) {
        this.bal = bal;
    }

    @Override
    public void run() {
        try {
            for (char lettre = 'A'; lettre <= 'Z'; lettre++) {
                System.out.println("Producteur : Dépôt de la lettre -> " + lettre);
                bal.deposer(String.valueOf(lettre));
                Thread.sleep(500); // Pause de 500 ms pour ralentir le producteur
            }
            System.out.println("Producteur : Dépôt de la lettre spéciale '*'.");
            bal.deposer("*");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Producteur interrompu.");
        }
    }
}
