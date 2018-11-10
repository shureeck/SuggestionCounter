import java.util.ArrayList;

import static stringconstant.StringsConstants.*;

public class Table {
    private String name;
    private ArrayList<Key> keysList = new ArrayList<Key>();

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
        return (this.name).equalsIgnoreCase(((Table)obj).getName());
    }

    public String getName() {
        return name;
    }

    public ArrayList<Key> getKeysList() {
        return keysList;
    }
}
