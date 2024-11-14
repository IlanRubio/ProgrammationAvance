public class MainTP3 {
    public static void main(String[] args) {
        BoiteAuLettre bal = new BoiteAuLettre();
        semaphoreGlobal sem = new semaphoreGlobal(5);
        Thread producteurThread = new Thread(new Producteur(bal,sem));
        Thread consommateurThread = new Thread(new Consommateur(bal,sem));

        producteurThread.start();
        consommateurThread.start();
    }
}
