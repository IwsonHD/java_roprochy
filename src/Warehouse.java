import java.util.HashMap;

public class Warehouse {

    private final int maxResourceAmount;
    private volatile HashMap<Type, Integer> warehouse;
    public Warehouse(int maxResourceAmount){
        this.warehouse = new HashMap<>();
        this.maxResourceAmount = maxResourceAmount;
    }

    public synchronized boolean getProducts(Type type, Integer amount, Integer consumerId){
        if(this.warehouse.getOrDefault(type,0) < amount){
            System.out.println("Consumer " + consumerId + " wanted to get too much products of type " + type.toString());
            return false;
        }
        System.out.println("Consumer " + consumerId + " got " + amount + " products of type " + type.toString());
        this.warehouse.put(type,this.warehouse.get(type));
        return true;
    }

    public synchronized boolean inputProducts(Type type, Integer amount, Integer producerId){
        if(this.warehouse.getOrDefault(type, 0) + amount > this.maxResourceAmount){
            System.out.println("Producer " + producerId + " wanted to input too much products of type " + type.toString());
            return false;
        }
        System.out.println("Producer " + producerId + " input " + amount + " products of type " + type.toString());
        this.warehouse.put(type, this.warehouse.getOrDefault(type,0) + amount);
        return true;
    }

    public int getMaxResourceAmount(){ return this.maxResourceAmount;}

}
