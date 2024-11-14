import java.util.Scanner;

public class Producteur implements Runnable {
    private final BoiteAuLettre bal;
    private semaphoreGlobal semaphore;

    public Producteur(BoiteAuLettre bal, semaphoreGlobal semaphore) {
        this.bal = bal;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            String lettre;
            do {
                Thread.sleep(1000);
                semaphore.syncWait();
                System.out.print("Entrez une lettre (Q pour quitter) : ");
                lettre = scanner.nextLine();
                bal.deposer(lettre);
                semaphore.syncSignal();
            } while (!lettre.equals("Q"));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
