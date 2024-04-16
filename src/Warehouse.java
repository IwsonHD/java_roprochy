import java.util.HashMap;

public class Magazine {

    public enum Type{
        FISH,
        MEAT,
        METAL,
        WOOD,
        COLA,
        ICE,
        DRUGS
    }
    private final int maxResourceAmount;
    private volatile HashMap<Type, Integer> magazine;
    public Magazine(int maxResourceAmount){
        this.magazine = new HashMap<>();
        this.maxResourceAmount = maxResourceAmount;
    }

    public synchronized void getProducts(Type type, Integer amount){
        if(this.magazine.get(type) < amount) return;
        this.magazine.put(type,this.magazine.get(type));
    }

    public synchronized void inputProducts(Type type, Integer amount){
        if(this.magazine.getOrDefault(type, 0) + amount > this.maxResourceAmount) return;
        this.magazine.put(type, this.magazine.getOrDefault(type,0) + amount);
    }

}
