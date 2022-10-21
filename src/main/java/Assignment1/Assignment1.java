package Assignment1;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Assignment1 {

    public static void main(String[] args) throws InterruptedException, IOException {

        //Part 1
        long start_time = System.currentTimeMillis();
        long end_time;
        long total_time;
        long throughput;

        // print out start time.
        System.out.println("Start Time: " + start_time);

        // define lift ride events variables.
        int LIFT_RIDE_EVENTS_AMOUNT = 200000;
        int INITIAL_THREADS_AMOUNT = 32;
        int MORE_THREADS_AMOUNT = 168;

        // this blocking queue saves all lift events generated by ThreadSkierGeneration.
        BlockingQueue<RandomSkier> queue = new ArrayBlockingQueue<>(LIFT_RIDE_EVENTS_AMOUNT);

        // this blocking queue saves all time consumption data.
        BlockingQueue<String[]> result_data = new ArrayBlockingQueue<>(LIFT_RIDE_EVENTS_AMOUNT);

        // this is the CountDownLatch that command the main function to run after all threads die,
        // it helps record end time and total time consumption.
        CountDownLatch countDownLatch = new CountDownLatch(INITIAL_THREADS_AMOUNT + MORE_THREADS_AMOUNT + 1);

        // thread that generate random skiers.
        ThreadSkierGeneration skier_generation = new ThreadSkierGeneration(
                queue,
                LIFT_RIDE_EVENTS_AMOUNT,
                countDownLatch
        );

        // thread that do post.
        ThreadDoPost skier_doPost = new ThreadDoPost(queue, result_data, countDownLatch);

        // begin both threads.
        new Thread(skier_generation).start();
        for (int i=0; i<INITIAL_THREADS_AMOUNT + MORE_THREADS_AMOUNT; i++) {
            new Thread(skier_doPost).start();
        }

        // command CountDownLatch to await until all threads die.
        try {
            countDownLatch.await();
            end_time = System.currentTimeMillis();
            System.out.println("End Time: " + end_time);
            total_time = end_time - start_time;
            throughput = total_time / INITIAL_THREADS_AMOUNT;
            System.out.println("Total Time consumed: " + total_time + "ms.");
            System.out.println("Throughput: " + throughput + "ms/thread");
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        //Part 2
        ResponseSummary summary = parse_result_data(result_data);
        System.out.println("Mean Response Time: " + summary.getMean_response_time() + "ms.");
        System.out.println("Median Response Time: " + summary.getMedian_response_time() + "ms.");
        System.out.println("P99: " + summary.getP99() + "ms.");
        System.out.println("Min Response Time: " + summary.getMin_response_time() + "ms.");
        System.out.println("Max Response Time: " + summary.getMax_response_time() + "ms.");

        String filePath = new String("D://result.csv");
        File file = new File(filePath);
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // adding header to csv
            String[] header = { "Start Time", "Request Type", "Latency", "Response Code" };
            writer.writeNext(header);

            // add data to csv
            for (String[] result : result_data) {
                writer.writeNext(result);
            }

            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static ResponseSummary parse_result_data(BlockingQueue<String[]> result_data) {

        long mean_response_time;
        long median_response_time;
        long p99;
        long min_response_time = Long.MAX_VALUE;
        long max_response_time = Long.MIN_VALUE;

        // helper dataset
        List<Long> response_time_array = new ArrayList<>();
        long total_response_time = 0l;

        for (String[] data : result_data) {
            long response_time = Long.valueOf(data[2]);
            total_response_time += response_time;
            response_time_array.add(response_time);

            // calculate the max and min response time.
            if (response_time > max_response_time) {
                max_response_time = response_time;
            }else if (response_time < min_response_time){
                min_response_time = response_time;
            }
        }

        //calculate mean response time
        mean_response_time = total_response_time / result_data.size();

        //calculate median response time
        Object[] temp = response_time_array.toArray();
        Arrays.sort(temp);
        median_response_time = (long) temp[temp.length/2];

        //calculate p99;
        p99 = find99th(response_time_array.toArray(), total_response_time);

        return new ResponseSummary(mean_response_time, median_response_time, p99, min_response_time, max_response_time);
    }

    public static long find99th(Object[] data, long total_response_time) {
        long response_time_99th = total_response_time * 99l / 100l;
        long temp_sum = 0l;
        long key_99th = (long) data[0];
        Arrays.sort(data);
        for (Object t : data) {
            temp_sum += (long) t;
            if (temp_sum >= response_time_99th) {
                key_99th = (long) t;
            }
        }

        return key_99th;
    }

}
