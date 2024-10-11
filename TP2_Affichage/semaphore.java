public abstract class semaphore {

    protected int valeur=0; // nombre de ressource

    protected semaphore (int valeurInitiale){
	valeur = valeurInitiale>0 ? valeurInitiale:0;
    } // ? -> contraction de if, then, else

    public synchronized void syncWait(){
	try {
	    while(valeur<=0){
		wait();
        }
	    valeur--;
	} catch(InterruptedException e){}
    }

    public synchronized void syncSignal(){
	if(++valeur > 0) notifyAll();
    }
}
