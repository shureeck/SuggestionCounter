import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Suggestions {
    private Table table;
    private String ruleList;
    private ArrayList<Key> dkKeys = new ArrayList<>();
    private ArrayList<Key> skKeys = new ArrayList<>();

    public Suggestions(Table table, String ruleList) {
        this.table = table;
        this.ruleList = ruleList;
    }

    public void getDK() {
        //Get All keys take part in Suggestions counting
        dkKeys.addAll(table.getAllKeysForRule("DK", ruleList));
        skKeys.addAll(table.getAllKeysForRule("SK", ruleList));

        //Compound duplicated keys and exclude to small
        Map<String, Integer> dkMap = new HashMap<>(excludeTooSmallKeys(
                compoundDuplicateKeys(dkKeys)));
        Map<String, Integer> skMap = new HashMap<>(excludeTooSmallKeys(
                compoundDuplicateKeys(skKeys)));

    }

    public Map<String, Integer> compoundDuplicateKeys(ArrayList<Key> keysList) {
        Map<String, Integer> resultMap = new HashMap<>();

        for (Key key : keysList) {
            String keyMap = key.getColumns();
            Integer value = key.getWeight();

            int putResult = resultMap.put(keyMap, value);

            if (putResult == 1) {
                int newValue = resultMap.get(keyMap) + value;
                resultMap.replace(keyMap, newValue);
            }
        }
        return resultMap;
    }

    public Map<String, Integer> excludeTooSmallKeys(Map<String, Integer> keys) {
        Map<String, Integer> result = new HashMap<>();
        int threshold = getMax(keys)/2;

         for (Map.Entry<String, Integer> entry : keys.entrySet()){
             if (entry.getValue()>threshold){
                 result.put(entry.getKey(), entry.getValue());
             }
         }
         return result;
    }

    public int getMax(Map<String, Integer> map) {
        int res = 0;
        for (Integer i : map.values()) {
            if (res < i) {
                res = i;
            }
        }
        return res;
    }

}
