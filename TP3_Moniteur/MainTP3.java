public class MainTP3 {
    public static void main(String[] args) {
        int tailleTampon = 5; // Augmenter la taille pour plus de tests
        BoiteAuLettre bal = new BoiteAuLettre(tailleTampon);

        Thread producteurThread = new Thread(new Producteur(bal));
        Thread consommateurThread = new Thread(new Consommateur(bal));

        System.out.println("Main : Démarrage des threads...");
        producteurThread.start();
        try {
            Thread.sleep(50); // Laisser le producteur démarrer en premier
        } catch (InterruptedException e) {
            System.out.println("Main : Pause interrompue.");
        }
        consommateurThread.start();

        System.out.println("Main : Threads démarrés.");
        try {
            producteurThread.join();
            consommateurThread.join();
        } catch (InterruptedException e) {
            System.out.println("Main : Thread principal interrompu.");
        }
        System.out.println("Main : Exécution terminée.");
    }
}
