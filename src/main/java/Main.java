import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }


    public static String replace (String oldS, String newS, String data){
        Pattern p = Pattern.compile("("+oldS+")");
        return p.matcher(data).replaceAll(newS);
    }

    public static void main(String[] args) throws Exception{
        int error = 0;

        String output = (new Main()).readRawDataToString();

        Pattern p = Pattern.compile("(.*?)##");
        Matcher m = p.matcher(output);
        List<String> productStrings = new ArrayList<String>();
        while(m.find()){
            productStrings.add(replace("##","",m.group()));
//            System.out.println(replace("##","",m.group()));
        }


        Map<String, Product> productMap = new LinkedHashMap<>();

        for (String str:productStrings) {

            Pattern pat = Pattern.compile("(^(?i)name:(.*?);)((?i)price:(.*?);)");
            Matcher mat = pat.matcher(str);

//            Pattern namePattern = Pattern.compile("^(?i)(name:)(.*?);");
//            Matcher nameMatcher = namePattern.matcher(str);
//
//            Pattern pricePattern = Pattern.compile("(?i)(price:)(.*?);");
//            Matcher priceMatcher = pricePattern.matcher(str);



            while(mat.find()) {
//                String nameWithoutTag = replace("(?i)(name:)","",mat.group(0));
//                String name = replace(";","",nameWithoutTag).toLowerCase();
//                String priceWithoutTag = replace("(?i)(price:)","",mat.group(1));
//                Double price = Double.valueOf(replace(";","",priceWithoutTag));
//
//                System.out.println("NAME: "+mat.group(2));
//                System.out.println("PRICE: "+mat.group(4));

                String name = "";
                Double price = 0.0;

                try {
                    name = mat.group(2).toLowerCase();
                    price = Double.valueOf(mat.group(4));
                    if(name.equals(""))
                        throw new Exception();
                }catch (Exception e){
                    error++;
                    continue;
                }

                if(!productMap.containsKey(name)) {
                    productMap.put(name, new Product(name));
                }
                productMap.get(name).addPrice(price);

            }
        }
        System.out.println(error);

        for (Map.Entry<String, Product> e :productMap.entrySet()) {
            System.out.println(e.getValue());
        }
    }
}
