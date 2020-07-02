import java.util.List;
import java.util.concurrent.Callable;

public class CalculateSumExecutorService implements Callable<Integer> {
    private final List<Integer> list;
    private final int delimiter;
    private final int size;

    public CalculateSumExecutorService(List<Integer> list, int numberThreads, int delimiter) {
        this.list = list;
        this.delimiter = delimiter;
        size = list.size() / numberThreads;
    }

    @Override
    public Integer call() {
        int start = delimiter * size;
        int end = start + size;
        List<Integer> subList = list.subList(start, end);
        return subList.stream().mapToInt(v -> v).sum();
    }
}
