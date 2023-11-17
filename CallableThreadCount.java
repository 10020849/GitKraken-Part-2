import java.util.*; import java.time.Instant; import java.util.concurrent.*;
public class CallableThreadCount implements Callable<Long>
{
    int threadNum;
    public CallableThreadCount() { System.out.println("In thread"); }
    public Long call()
    {
        for (int i = 0; i < 1000000; i++)
            threadNum++;
        return (long) threadNum;
    }
    public static void main(String[] args)
    {
        int numThreads = 100;
        List<Callable<Long>> tasks = new ArrayList<>();
        for (int i = 0; i < numThreads; i++)
            tasks.add(new CallableThreadCount());
        ExecutorService es = Executors.newFixedThreadPool(numThreads);
        Instant start = Instant.now();
        try
        {
            List<Future<Long>> futures = es.invokeAll(tasks);
            long totalSum = 0;
            for (Future<Long> future : futures)
                totalSum += future.get();
            System.out.println("Sum: " + totalSum);
        } catch (Exception e) { e.printStackTrace(); }
        es.shutdown();
        Instant end = Instant.now();
        System.out.println("Time: " + ( (double) (end.getNano() - start.getNano())/1000000000 ) + " seconds");
    }
}