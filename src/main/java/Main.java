import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> list = CalculateSumHandler.getListOfNumbers(2, 1_000_000);
        long sumSumExecutorService = CalculateSumHandler.getSumExecutorService(list);
        long sumForkJoin = CalculateSumHandler.getSumForkJoin(list);
        System.out.println(sumSumExecutorService);
        System.out.println(sumForkJoin);
        System.out.println(sumSumExecutorService == sumForkJoin);
    }
}
