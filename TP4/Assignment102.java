// Estimate the value of Pi using Monte-Carlo Method, using parallel program
//package assignments;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import static java.lang.Math.abs;
class PiMonteCarlo {
	AtomicInteger nAtomSuccess;
	int nThrows;
	double value;
	int nProcess;
	class MonteCarlo implements Runnable {
		@Override
		public void run() {
			double x = Math.random();
			double y = Math.random();
			if (x * x + y * y <= 1)
				nAtomSuccess.incrementAndGet();
		}
	}
	public PiMonteCarlo(int i, int n ) {
		this.nAtomSuccess = new AtomicInteger(0);
		this.nThrows = i;
		this.value = 0;
		this.nProcess = n;
	}
	public double getPi() {
		//int nProcessors = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = Executors.newWorkStealingPool(nProcess);
		for (int i = 1; i <= nThrows; i++) {
			Runnable worker = new MonteCarlo();
			executor.execute(worker);
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		value = 4.0 * nAtomSuccess.get() / nThrows;
		return value;
	}
}
public class Assignment102 {
	public static void main(String[] args) {
		PiMonteCarlo PiVal = new PiMonteCarlo(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		long startTime = System.currentTimeMillis();
		double value = PiVal.getPi();
		long stopTime = System.currentTimeMillis();

		System.out.println("Approx value:" + value);
		System.out.println("Ntotal:" + Integer.parseInt(args[0]));
		System.out.println("Erreur relative: " + abs(value - Math.PI) / Math.PI);
		System.out.println("Available processors: " + Integer.parseInt(args[1]));
		System.out.println("Time Duration: " + (stopTime - startTime) + "ms");


		System.out.println("Difference to exact value of pi: " + (value - Math.PI));
		String data = "\n"+Integer.parseInt(args[0]) +", " + abs(value - Math.PI) / Math.PI +", " + Integer.parseInt(args[1]) +", " + (stopTime - startTime) + "ms" ;

        FileWriter writer = null;
        try {
            writer = new FileWriter("TP4/out_assignment102_G21_4c.txt",true);
			writer.write(data);
			writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

	}
}
