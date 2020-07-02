import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class CalculateSumHandler {
    private static final int THREADS_NUMBER = 10;

    public static List<Integer> getListOfNumbers(int value, int number) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            list.add(value);
        }
        return list;
    }

    public static long getSumExecutorService(List<Integer> list) {
        List<Callable<Integer>> callableList = new ArrayList<>();
        for (int i = 0; i < THREADS_NUMBER; i++) {
            callableList.add(new CalculateSumExecutorService(list, THREADS_NUMBER, i));
        }
        ExecutorService executorService = Executors.newFixedThreadPool(THREADS_NUMBER);
        long sum = 0;
        try {
            List<Future<Integer>> futures = executorService.invokeAll(callableList);
            executorService.shutdown();
            for (Future<Integer> future : futures) {
                sum += future.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return sum;
    }

    public static long getSumForkJoin(List<Integer> list) {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        return pool.invoke(new CalculateSumForkJoin(list, THREADS_NUMBER));
    }
}
