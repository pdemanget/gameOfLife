package pdemanget.gameoflife;


/**
 * Schedule at variable rate.
 * @author pdemanget
 *
 */
public class GameWorker implements Runnable{
	private int delay;
	private Runnable delegate;
	
	public GameWorker(Runnable delegate) {
		super();
		this.delegate = delegate;
	}

	@Override
	public void run() {
		while(delay>=0) {
			delegate.run();
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				return;
			}
		}
	}
	
	public boolean isRunning() {
		return delay>=0;
	}
	
	public void setDelay(int delay) {
		this.delay=delay;
	}
	
	public void halt() {
		delay=-1;
	}

	public int getDelay() {
		return delay;
	}

}
