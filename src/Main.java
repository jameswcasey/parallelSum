import java.util.Random;
import java.util.Arrays;
import java.util.concurrent.*;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        int[] array = new int[2_000_000];
        for (int i=0; i <= 1_999_999; i++) {
            Random rand = new Random();
            int randomNumber = rand.nextInt(10);
            array[i] = randomNumber;
        }
        long startTime = Instant.now().toEpochMilli();
        singleThread(array);
        long endTime = Instant.now().toEpochMilli();
        System.out.println(endTime - startTime);
        startTime = Instant.now().toEpochMilli();
        multiThread(array);
        endTime = Instant.now().toEpochMilli();
        System.out.println(endTime - startTime);
    }

    public static void singleThread(int[] array) {
        int total = 0;
        for (int i=0; i <= 1_999_999; i++) {
            total = total + array[i];
        }
        System.out.println(total);
    }

    public static void multiThread(int[] array) {
        Task task = new Task(array);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        int futureSum = 0;
        Future<Integer> future = executor.submit(() -> {
            int total = 0;
            for (int i=1_999_999; i >= 1_000_000; i--) {
                total = total + array[i];
            }
            Integer s = Integer.valueOf(total);
            return s;
        });
        int total = 0;
        for (int i=0; i <= 999_999; i++) {
            total = total + array[i];
        }
        while(!future.isDone()) {
            try {
                futureSum = future.get();
                int sum = total + futureSum;
                System.out.println(sum);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}