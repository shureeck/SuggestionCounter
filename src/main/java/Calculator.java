import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static stringconstant.LoggerMessages.*;

public class Calculator {
    private Table table;
    private ArrayList<Key> dkKeys = new ArrayList<>();
    private ArrayList<Key> skKeys = new ArrayList<>();

    public ArrayList<String> getSuggestions(Table table, String ruleList) {

        this.table = table;

        Logger.setLog(String.format(SUGGESYION_CALCULATION_START, table.getName(), ruleList));

        //Get All keys take part in Calculator counting
        dkKeys.addAll(table.getAllKeysForRule("DK", ruleList));
        skKeys.addAll(table.getAllKeysForRule("SK", ruleList));

        //Compound duplicated keys and exclude to small
        Logger.setLog(GOT_DK_FOR_CALCULATION);
        Map<String, Double> dkMap = new HashMap<>(excludeTooSmallKeys(
                compoundDuplicateKeys(dkKeys)));
        Logger.setLog(GOT_SK_FOR_CALCULATION);
        Map<String, Double> skMap = new HashMap<>(excludeTooSmallKeys(
                compoundDuplicateKeys(skKeys)));

        ArrayList<Suggestion> suggestionsList = new ArrayList<>(calculateSuggestions(dkMap, skMap));

        ArrayList<String> result = new ArrayList<>();
        result.addAll(getSuggestionsStrings(suggestionsList));

        return result;
    }

    public Map<String, Double> compoundDuplicateKeys(ArrayList<Key> keysList) {
        Map<String, Double> resultMap = new HashMap<>();

        for (Key key : keysList) {
            String keyMap = key.getColumns();
            Double value = key.getWeight();

            if (resultMap.containsKey(keyMap)) {
                double newValue = resultMap.get(keyMap) + value;
                resultMap.replace(keyMap, newValue);
                Logger.setLog(String.format(KEY_ALREADY_EXISTS, keyMap));
                Logger.setLog(String.format("Modified key: " + COLUMNS_CONFIDENCE, keyMap, String.valueOf(newValue)));

            } else {
                resultMap.put(keyMap, value);
                Logger.setLog(String.format(COLUMNS_CONFIDENCE, keyMap, String.valueOf(value)));
            }
        }
        return resultMap;
    }

    public Map<String, Double> excludeTooSmallKeys(Map<String, Double> keys) {
        Map<String, Double> result = new HashMap<>();
        double threshold = getMax(keys) / 2;

        for (Map.Entry<String, Double> entry : keys.entrySet()) {
            if (entry.getValue() > threshold) {
                result.put(entry.getKey(), entry.getValue());
            } else {
                Logger.setLog(String.format(KEY_WILL_BE_EXCLUDED,
                        entry.getKey(), String.valueOf(entry.getValue()), String.valueOf(threshold)));
            }
        }
        return result;
    }

    public ArrayList<Suggestion> calculateSuggestions(Map<String, Double> dk, Map<String, Double> sk) {
        double sumDK = getSumm(dk);
        double sumSK = getSumm(sk);
        ArrayList<Suggestion> suggestionsList = new ArrayList<>();

        for (Map.Entry<String, Double> entryDK : dk.entrySet()) {
            if (sk.size() > 0) {
                for (Map.Entry<String, Double> entrySK : sk.entrySet()) {
                    double confidenceTemp = (entryDK.getValue() / sumDK) * (entrySK.getValue() / sumSK);
                    String dkTemp = entryDK.getKey();
                    String skTemp = entrySK.getKey();
                    suggestionsList.add(new Suggestion(dkTemp, skTemp, confidenceTemp));
                }
            } else {
                double confidenceTemp = (entryDK.getValue() / sumDK);
                String dkTemp = entryDK.getKey();
                String skTemp = "";
                suggestionsList.add(new Suggestion(dkTemp, skTemp, confidenceTemp));
            }
        }
        return suggestionsList;
    }

    public double getMax(Map<String, Double> map) {
        double res = 0;
        for (Double i : map.values()) {
            if (res < i) {
                res = i;
            }
        }
        return res;
    }

    public double getSumm(Map<String, Double> map) {
        double sum = 0;
        for (double i : map.values()) {
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
            result.add(table.getName() + String.valueOf(i) + "," + "," + suggestion.toString());
            Logger.setLog(table.getName() + "," + String.valueOf(i) + "," + suggestion.toString());
            i++;
        }
        if (sortedSuggestion.size() == 0) {
            result.add(table.getName() + "," + "N/A,N/A,N/A,N/A");
            Logger.setLog(table.getName() + "," + "N/A,N/A,N/A,N/A");
        }
        return result;
    }

}
