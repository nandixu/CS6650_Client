package Assignment1;

import Assignment1.model.RandomSkier;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class ThreadSkierGeneration implements Runnable{

    //This thread will generate skiers
    private BlockingQueue<RandomSkier> bq;
    private int EVENTS_AMOUNT;
    private CountDownLatch countDownLatch;

    public ThreadSkierGeneration(BlockingQueue<RandomSkier> bq, int events_amount, CountDownLatch countDownLatch) {
        this.bq = bq;
        this.countDownLatch = countDownLatch;
        this.EVENTS_AMOUNT = events_amount;
    }

    @Override
    public void run() {
        for (int i=0; i<EVENTS_AMOUNT; i++) {
            RandomSkier new_skier = new RandomSkier();
            try {
                bq.put(new_skier);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Put exit note to blockingqueue
        RandomSkier exit_notification = new RandomSkier();
        exit_notification.setExit(true);
        try {
            bq.put(exit_notification);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        countDownLatch.countDown();
    }

    public BlockingQueue<RandomSkier> getBq() {
        return bq;
    }
}
