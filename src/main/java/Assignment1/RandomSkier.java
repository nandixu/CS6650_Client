package Assignment1;

import java.util.Random;

public class RandomSkier {
    private int skierID;
    private int resortID;
    private int liftID;
    private int seasonID = 2022;
    private int dayID = 1;
    private int time;
    private boolean exit = false;

    int SKIER_ID_LIMIT = 100000;
    int RESORT_ID_LIMIT = 10;
    int LIFT_ID_LIMIT = 40;
    int TIME_LIMIT = 360;

    public RandomSkier(){
        Random rand = new Random();
        this.skierID = rand.nextInt(SKIER_ID_LIMIT);
        this.resortID = rand.nextInt(RESORT_ID_LIMIT);
        this.liftID = rand.nextInt(LIFT_ID_LIMIT);
        this.time = rand.nextInt(TIME_LIMIT);

    }

    public int getSkierID() {
        return skierID;
    }

    public void setSkierID(int skierID) {
        this.skierID = skierID;
    }

    public int getResortID() {
        return resortID;
    }

    public void setResortID(int resortID) {
        this.resortID = resortID;
    }

    public int getLiftID() {
        return liftID;
    }

    public void setLiftID(int liftID) {
        this.liftID = liftID;
    }

    public int getSeasonID() {
        return seasonID;
    }

    public void setSeasonID(int seasonID) {
        this.seasonID = seasonID;
    }

    public int getDayID() {
        return dayID;
    }

    public void setDayID(int dayID) {
        this.dayID = dayID;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean getExit() {
        return this.exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }
}
