public class Consommateur implements Runnable {
    private final BoiteAuLettre bal;

    public Consommateur(BoiteAuLettre bal) {
        this.bal = bal;
    }

    @Override
    public void run() {
        System.out.println("Consommateur : Thread démarré.");
        try {
            String lettre;
            do {
                lettre = bal.retirer();
                System.out.println("Consommateur : Lettre consommée -> " + lettre);
                Thread.sleep(500); // Pause de 1 seconde pour ralentir le consommateur
            } while (!lettre.equals("*"));
            System.out.println("Consommateur : Fin du traitement.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Consommateur interrompu.");
        }

    }
}