package Assignment1;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class ThreadDoPost implements Runnable{

    //This thread will do 1000 post and die.

    private BlockingQueue<RandomSkier> lift_events;
    private BlockingQueue<String[]> result_data;
    private int POST_AMOUNT_LIMIT = 1000;
    private CountDownLatch countDownLatch;

    public ThreadDoPost(BlockingQueue<RandomSkier> lift_events, BlockingQueue<String[]> result_data, CountDownLatch countDownLatch) {
        this.lift_events = lift_events;
        this.result_data = result_data;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {

        try {
            RandomSkier skier;
            int count = 0;
            while (!(skier = lift_events.take()).getExit()) {
                System.out.println("Posted :" + skier.getSkierID());
                doPost(skier);
                count ++;

                //Thread will die when it reaches maximum allowed post amount.
                if (count == POST_AMOUNT_LIMIT) {
                    countDownLatch.countDown();
                    return;
                }
            }

            if (skier.getExit()) {
                RandomSkier exit_notification = new RandomSkier();
                exit_notification.setExit(true);
                try {
                    lift_events.put(exit_notification);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        countDownLatch.countDown();

    }

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public void doPost(RandomSkier skier) throws IOException, InterruptedException {

        String REQUEST_TYPE = "POST";

        // parse skier info.
        int skier_ID = skier.getSkierID();
        int resort_ID = skier.getResortID();
        int lift_ID = skier.getLiftID();
        int season_ID = skier.getSeasonID();
        int day_ID = skier.getDayID();
        int time = skier.getTime();

        // json formatted data
        String json = new StringBuilder()
                .append("{")
                .append("\"skierID\":" + "\"" + skier_ID + "\", ")
                .append("\"resortID\":" + "\"" + resort_ID + "\", ")
                .append("\"liftID\":" + "\"" + lift_ID + "\", ")
                .append("\"seasonID\":" + "\"" + season_ID + "\", ")
                .append("\"dayID\":" + "\"" + day_ID + "\", ")
                .append("\"time\":" + "\"" + time + "\"")
                .append("}").toString();

        // add json header
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(URI.create("http://localhost:8081/CS6650/skiers/12/seasonss/2019/day/1/skier/123"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .header("Content-Type", "application/json")
                .build();

        // record start time.
        long start_time = System.currentTimeMillis();

        // send request.
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // record end time.
        long time_receive_response = System.currentTimeMillis();

        // calculate latency.
        long latency = time_receive_response - start_time;

        // generate time consumption data.
        String[] data = new String[4];
        data[0] = String.valueOf(start_time);
        data[1] = REQUEST_TYPE;
        data[2] = String.valueOf(latency);
        data[3] = String.valueOf(response.statusCode());

        //put time consumption data into result.data.
        try{
            result_data.put(data);
        }catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }


        // print status code
        //System.out.println(response.statusCode());

        // print response body
        //System.out.println(response.body());
    }

}
