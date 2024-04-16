import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class Consumer implements Runnable{

    private int consumerId;
    private static volatile AtomicBoolean runs;
    private Warehouse mutualWarehouse;
    private final ThreadLocalRandom random;

    private static int amountOfConsumers = 0;
    public Consumer(Warehouse mutualWarehouse){
        this.consumerId = ++amountOfConsumers;
        this.random = ThreadLocalRandom.current();
        this.mutualWarehouse = mutualWarehouse;

    }
    public static void initializeSwitch(AtomicBoolean switchOnOff){
        runs = switchOnOff;
    }

    @Override
    public void run(){
        System.out.println("Consumer " + this.consumerId + " starts consuming");
        while(runs.get()){
            try{
                Thread.sleep(5000 + random.nextInt(-2000, 2000));
            }catch (InterruptedException e){
                return;
            }
            Type newProducedType = Type.possibleTypes[random.nextInt(Type.possibleTypes.length)];
            Integer amountOfNewProducts = random.nextInt(1,mutualWarehouse.getMaxResourceAmount()/3);

            if(!mutualWarehouse.getProducts(newProducedType, amountOfNewProducts, this.consumerId)) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }

    }
}
