package Multithreading;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class PrimeSum {
    private static final int numThreads = 4;
    private static AtomicLong totalSum = new AtomicLong(0);

    public static void main(String[] args) throws InterruptedException {
        int upperLimit = 1000000;
        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
        int chunkSize = upperLimit / numThreads;

        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize + 1;
            int end = (i == numThreads - 1) ? upperLimit : (i + 1) * chunkSize;
            threadPool.execute(new PrimeTask(start, end));
        }

        threadPool.shutdown();
        threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        System.out.println("Sum of prime numbers up to " + upperLimit + " is: " + totalSum.get());
    }

    static class PrimeTask implements Runnable {
        private final int start, end;

        public PrimeTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public void run() {
            long localSum = 0;
            for (int i = start; i <= end; i++) {
                if (isPrime(i)) {
                    localSum += i;
                }
            }
            totalSum.addAndGet(localSum);
        }

        private boolean isPrime(int num) {
            if (num < 2) return false;
            if (num == 2) return true;
            if (num % 2 == 0) return false;
            for (int i = 3; i * i <= num; i += 2) {
                if (num % i == 0) return false;
            }
            return true;
        }
    }
}