import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Lab3 {

    public int THREADS_AMOUNT = 100;

    public static class lab3thread extends Thread {

        private String url;

        public lab3thread(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            // random skier generation
            RandomSkier skier = new RandomSkier();
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

            // do post
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .uri(URI.create("http://localhost:8081/CS6650/skiers/12/seasonss/2019/day/1/skier/123"))
                    .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                    .header("Content-Type", "application/json")
                    .build();
            try {
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response.statusCode());
                System.out.println(response.body());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static void main(String[] args) {

        System.out.println("Start Time: " + System.currentTimeMillis());
        for (int i = 0; i < 1000; i++) {
            lab3thread thread1 = new lab3thread("url");
            thread1.start();
        }
        System.out.println("End Time: " + System.currentTimeMillis());

    }
}
