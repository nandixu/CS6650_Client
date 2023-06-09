package Scratchs;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import static org.junit.Assert.assertThat;

public class HttpClientTutorialPostJSON {

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static void main(String[] args) throws IOException, InterruptedException {

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

        // add json header
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(URI.create("http://localhost:8081/CS6650/skiers/12/seasonss/2019/day/1/skier/123"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());

        // print response body
        System.out.println(response.body());

    }

    @Test
    public static void whenPostJsonUsingHttpClient_thenCorrect()
            throws ClientProtocolException, IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://www.example.com");

        String json = "{\"id\":1,\"name\":\"John\"}";
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = client.execute(httpPost);
        client.close();
    }

}
