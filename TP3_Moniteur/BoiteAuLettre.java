public class BoiteAuLettre {
    private String chLettre;
    private Boolean disponible = false;

    public BoiteAuLettre() {

    }
    public synchronized void deposer(String lettre) throws InterruptedException {
        while (disponible) {
            wait(); // Attend que la boîte soit vide
        }
        this.chLettre = lettre;
        disponible = true;
        notifyAll(); // Notifie le consommateur
    }
    public synchronized String retirer() throws InterruptedException {
        while (!disponible) {
            wait(); // Attend qu'une lettre soit disponible
        }
        disponible = false;
        notifyAll(); // Notifie le producteur
        return chLettre;
    }

}
