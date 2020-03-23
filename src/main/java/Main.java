import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static int error = 0;

    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }

    //will give back a String List where the data are separated by ##
    //naMe:Milk;price:3.23;type:Food;expiration:1/25/2016
    //naME:BreaD;price:1.23;type:Food;expiration:1/02/2016 ...
    public static List<String> getProductStrings(String data){
        List<String> productStrings = new ArrayList<String>();
        Pattern p = Pattern.compile("(.*?)##");
        Matcher m = p.matcher(data);
        while(m.find())
            productStrings.add(m.group(0));

        return productStrings;
    }

    //turn String List into a map
    public static Map<String, Product> buildMap(List<String> productStrings){
        Map<String, Product> productMap = new LinkedHashMap<>();

        for (String str:productStrings) {
            Pattern pat = Pattern.compile("(^(?i)name:(.*?);)((?i)price:(.*?);)");
            Matcher mat = pat.matcher(str);

            while(mat.find()) {
                String name = "";
                double price = 0.0;
                try {
                    name = zeroToO(mat.group(2).toLowerCase());
                    price = Double.parseDouble(mat.group(4));
                    if(name.equals(""))
                        throw new Exception();
                }catch (Exception e){
                    error++;
                    continue;
                }
                //if the product does not exist in the map, add a new product
                if(!productMap.containsKey(name))
                    productMap.put(name, new Product(name));

                productMap.get(name).addPrice(price);

            }
        }
        return productMap;
    }

    //Convert any 0 in the product name to o
    public static String zeroToO(String name){
        Pattern p = Pattern.compile("(0)");
        return p.matcher(name).replaceAll("o");
    }



    public static void main(String[] args) throws Exception{
        //get data
        String output = (new Main()).readRawDataToString();

        //setup
        List<String> productStrings = getProductStrings(output);
        Map<String, Product> productMap = buildMap(productStrings) ;

        //print result
        for (Map.Entry<String, Product> e :productMap.entrySet())
            System.out.println(e.getValue());

        System.out.println("Errors         \t \t seen: "+error+" times");
    }
}
