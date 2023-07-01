public class Task implements Runnable {
    int[] array;
    int sum = 0;
    @Override
    public void run() {
        int total = 0;
        for (int i=2_000_000; i == 1_000_000; i--) {
            total = total + array[i];
        }
        sum = total;
    }

    Task(int[] array)
    {
        this.array = array;
    }
}