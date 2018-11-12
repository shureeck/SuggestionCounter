import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Calculator {
    private Table table;
    private String ruleList;
    private ArrayList<Key> dkKeys = new ArrayList<>();
    private ArrayList<Key> skKeys = new ArrayList<>();

    public void getSuggestions(Table table, String ruleList) {

        this.table = table;
        this.ruleList = ruleList;

        //Get All keys take part in Calculator counting
        dkKeys.addAll(table.getAllKeysForRule("DK", ruleList));
        skKeys.addAll(table.getAllKeysForRule("SK", ruleList));

        //Compound duplicated keys and exclude to small
        Map<String, Integer> dkMap = new HashMap<>(excludeTooSmallKeys(
                compoundDuplicateKeys(dkKeys)));
        Map<String, Integer> skMap = new HashMap<>(excludeTooSmallKeys(
                compoundDuplicateKeys(skKeys)));

        ArrayList<Suggestion> suggestionsList = new ArrayList<>(calculateSuggestions(dkMap, skMap));

        ArrayList<String> result = new ArrayList<>();
        result.addAll(getSuggestionsStrings(suggestionsList));
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
        int threshold = getMax(keys) / 2;

        for (Map.Entry<String, Integer> entry : keys.entrySet()) {
            if (entry.getValue() > threshold) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    public ArrayList<Suggestion> calculateSuggestions(Map<String, Integer> dk, Map<String, Integer> sk) {
        int sumDK = getSumm(dk);
        int sumSK = getSumm(sk);
        ArrayList<Suggestion> suggestionsList = new ArrayList<>();

        for (Map.Entry<String, Integer> entryDK : dk.entrySet()) {
            for (Map.Entry<String, Integer> entrySK : sk.entrySet()) {
                double confidenceTemp = (entryDK.getValue() / sumDK) * (entrySK.getValue() / sumSK);
                String dkTemp = entryDK.getKey();
                String skTemp = entrySK.getKey();
                suggestionsList.add(new Suggestion(dkTemp, skTemp, confidenceTemp));
            }
        }
        return suggestionsList;
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

    public int getSumm(Map<String, Integer> map) {
        int sum = 0;
        for (int i : map.values()) {
            sum += i;
        }
        return sum;
    }

    public ArrayList<String> getSuggestionsStrings(ArrayList<Suggestion> suggestionList) {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<Suggestion> sortedSuggestion = new ArrayList<>();
        sortedSuggestion.addAll(suggestionList.stream().sorted().collect(Collectors.toList()));
        int i = 1;
        for (Suggestion suggestion : sortedSuggestion) {
            result.add(String.valueOf(i) + "," + table + "," + suggestion.toString());
            i++;
        }
        return result;
    }

}
