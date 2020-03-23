import java.util.HashMap;
import java.util.Map;

public class Product {
    String name;
    Map<Double, Integer>prices;


    public Product(String name){
        this.name = name;
        prices = new HashMap<>();
    }

    public String getName(){
        return name;
    }

    public void addPrice(Double price){
        if(prices.containsKey(price))
            prices.replace(price,prices.get(price)+1);
        else
            prices.put(price,1);
    }

    public Map<Double,Integer> getPrices(){
        return prices;
    }

//    public Integer getPriceSeen(Double price){
//        return prices.get(price);
//    }

    public Integer getTotalSeen(){
        int sum = 0;
        for(Map.Entry<Double,Integer> e:prices.entrySet()){
            sum += e.getValue();
        }
        return sum;
    }

    public String time( int num ){
        return num > 1 ? "times" : "time";
    }

    public String capName(String name){
        return Character.toUpperCase(name.charAt(0))+name.substring(1);
    }
    @Override
    public String toString() {
        int counter = 0;
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("name:%8s\t\t seen: %d %s\n",capName(name), getTotalSeen(), time(getTotalSeen())));
        sb.append("============= \t \t =============\n");
        for (Map.Entry<Double,Integer> e:prices.entrySet()) {
            sb.append(String.format("Price:%7.2f\t\t seen: %d %s\n",e.getKey(), e.getValue(), time(e.getValue())));
            if(counter == 0)
                sb.append("-------------     \t -------------\n");

            counter++;
        }
        return sb.toString();
    }
}
