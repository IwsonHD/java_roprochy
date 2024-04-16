import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class Producer implements Runnable{

    private int producerId;
    private volatile static AtomicBoolean runs;
    private Warehouse mutualWarehouse;

    private final ThreadLocalRandom random;

    private static int amountOfProducers = 0;
    public Producer(Warehouse mutualWarehouse){
        this.producerId = ++amountOfProducers;
        this.mutualWarehouse = mutualWarehouse;
        this.random = ThreadLocalRandom.current();
    }

    public static void initializeSwitch(AtomicBoolean switchOnOff){
        runs = switchOnOff;
    }

    @Override
    public void run(){
        System.out.println("Producer " + this.producerId + " starts producing");
        while(runs.get()){
            try{
                Thread.sleep(10000 + random.nextInt(-2000, 2000));
            }catch (InterruptedException e){
                return;
            }
            Type newProducedType = Type.possibleTypes[random.nextInt(Type.possibleTypes.length)];
            Integer amountOfNewProducts = random.nextInt(1,mutualWarehouse.getMaxResourceAmount()/2);

            if(!mutualWarehouse.inputProducts(newProducedType, amountOfNewProducts, this.producerId)) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

}
