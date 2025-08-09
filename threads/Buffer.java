import java.util.LinkedList;

public class Buffer {
    private LinkedList<Integer> list = new LinkedList<>();
    private final int MAX_CAPACITY = 5;

    public synchronized void put(int value) throws InterruptedException {
        while (list.size() == MAX_CAPACITY) {
            System.out.println("Buffer is full. Producer is waiting...");
            wait();
        }
        list.add(value);
        System.out.println("Producer put: " + value);
        notifyAll();
    }

    public synchronized int get () throws InterruptedException {
        while (list.isEmpty()) {
            System.out.println("Buffer is empty. Consumer is waiting...");
            wait();
        }
        int value = list.removeFirst();
        System.out.println("Consumer got: " + value);
        notifyAll();
        return value;
    }
}