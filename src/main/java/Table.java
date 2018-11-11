import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static stringconstant.StringsConstants.*;

public class Table {
    private String name;
    private Set<Key> keysList = new HashSet<>();

    public Table(String name) {
        this.name = name;
    }

    public void addKey(String keyInfo) {
        String[] tempArray = keyInfo.split(SEMICOLON);
        int ruleNumber = Integer.parseInt(tempArray[0]);
        int weight = Integer.parseInt(tempArray[5]);
        keysList.add(new Key(ruleNumber, tempArray[1], tempArray[2], tempArray[3], weight));
    }

    @Override
    public boolean equals(Object obj) {
        return (this.name).equalsIgnoreCase(((Table) obj).getName());
    }

    public String getName() {
        return name;
    }

    public ArrayList<Key> getAllKeysForRule(String type, String rules) {
        String[] rulesArray = rules.toLowerCase().split(COMA);
        ArrayList<Key> result = new ArrayList<>();

        for (String rule : rulesArray) {
            int ruleNumbTemp = Integer.parseInt(rule.substring(0, 3).trim());
            result.addAll(keysList.stream().filter((p) -> ((p.getRule() == ruleNumbTemp)) &&
                    (p.getType().equalsIgnoreCase(type)))
                    .collect(Collectors.toList()));
        }
        return result;
    }
}
