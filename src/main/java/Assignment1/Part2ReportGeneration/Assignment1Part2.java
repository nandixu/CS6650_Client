package Assignment1.Part2ReportGeneration;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Assignment1Part2 {

    public Assignment1Part2(BlockingQueue<String[]> result_data) {
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
