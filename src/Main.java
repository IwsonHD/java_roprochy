import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args) {
        AtomicBoolean switchOnOff = new AtomicBoolean(true);
        Producer.initializeSwitch(switchOnOff);
        Consumer.initializeSwitch(switchOnOff);
        int amountOfProducers = 3;
        int amountOfConsumers = 3;
        int amountOfProductSpace = 20;

        Warehouse warehouse = new Warehouse(amountOfProductSpace);

        final ExecutorService producerExecutor = Executors.newFixedThreadPool(amountOfProducers);
        final ExecutorService consumerExecutor = Executors.newFixedThreadPool(amountOfConsumers);
        for(int i = 0; i < amountOfConsumers; i++){
            consumerExecutor.submit(new Consumer(warehouse));
        }
        for(int i = 0; i < amountOfProducers; i++){
            producerExecutor.submit(new Producer(warehouse));
        }


        Scanner scanner = new Scanner(System.in);
        do{
            if(scanner.nextLine().equals("end")) {
                switchOnOff.set(false);
                System.out.println("Ending simulation");
            }
        }while(switchOnOff.get());

        producerExecutor.shutdownNow();
        consumerExecutor.shutdownNow();

    }
}