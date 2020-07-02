import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class CalculateSumForkJoin extends RecursiveTask<Long> {
    private static int threshold;
    private final List<Integer> list;

    public CalculateSumForkJoin(List<Integer> list, int numberThreads) {
        this.list = list;
        threshold = list.size() / numberThreads;
    }

    private CalculateSumForkJoin(List<Integer> list) {
        this.list = list;
    }

    @Override
    protected Long compute() {
        if (list.size() > threshold) {
            return ForkJoinTask.invokeAll(createSubTasks())
                    .stream()
                    .mapToLong(ForkJoinTask::join)
                    .sum();
        } else {
            return calculate(list);
        }
    }

    private List<CalculateSumForkJoin> createSubTasks() {
        return List.of(
                new CalculateSumForkJoin(
                        list.subList(0, list.size() / 2 + 1)),
                new CalculateSumForkJoin(
                        list.subList(list.size() / 2 + 1, list.size())));
    }

    private long calculate(List<Integer> list) {
        return list.stream().mapToLong(v -> v).sum();
    }
}
