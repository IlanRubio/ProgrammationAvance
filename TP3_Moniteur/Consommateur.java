public class Consommateur implements Runnable {
    private final BoiteAuLettre bal;
    private semaphoreGlobal semaphore;

    public Consommateur(BoiteAuLettre bal, semaphoreGlobal semaphore) {
        this.bal = bal;
        this.semaphore=semaphore;
    }

    @Override
    public void run() {
        try {
            String lettre;
            do {
                Thread.sleep(1000);
                semaphore.syncWait();
                lettre = bal.retirer();
                System.out.println("Lettre lue : " + lettre);
                semaphore.syncSignal();
            } while (!lettre.equals("Q"));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}