import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1_000_000; i++) {
            list.add(2);
        }
        int numberThreads = 10;
        long sumSumExecutorService = getSumExecutorService(list, numberThreads);
        long sumForkJoin = getSumForkJoin(list, numberThreads);
        System.out.println(sumSumExecutorService);
        System.out.println(sumForkJoin);
        System.out.println(sumSumExecutorService == sumForkJoin);
    }

    private static long getSumExecutorService(List<Integer> list, int numberThreads) {
        List<Callable<Integer>> callableList = new ArrayList<>();
        for (int i = 0; i < numberThreads; i++) {
            callableList.add(new CalculateSumExecutorService(list, numberThreads, i));
        }
        ExecutorService executorService = Executors.newFixedThreadPool(numberThreads);
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

    private static long getSumForkJoin(List<Integer> list, int numberThreads) {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        return pool.invoke(new CalculateSumForkJoin(list, numberThreads));
    }
}
