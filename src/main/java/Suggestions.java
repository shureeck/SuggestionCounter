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
        dkKeys.addAll(table.getAllKeysForRule("DK", ruleList));
        skKeys.addAll(table.getAllKeysForRule("SK", ruleList));

        Map<String, Integer> dkMap = new HashMap<>();
        for (Key key : dkKeys) {
            String keyMap = key.getColumns();
            Integer value = key.getWeight();

            int putResult = dkMap.put(keyMap, value);

            if (putResult == 1) {
                int newValue = dkMap.get(keyMap) + value;
                dkMap.replace(keyMap, newValue);
            }
        }
    }

}
