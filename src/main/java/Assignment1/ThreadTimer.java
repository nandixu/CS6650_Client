package Assignment1;

public class ThreadTimer implements Runnable{

    private long ending_time;

    public ThreadTimer(){
    }

    @Override
    public void run() {
        this.ending_time = System.currentTimeMillis();
        System.out.println("End Time: " + ending_time);
    }

    public long getEnding_time() {
        return ending_time;
    }
}
