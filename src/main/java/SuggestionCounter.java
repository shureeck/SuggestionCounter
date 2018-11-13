import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static stringconstant.LoggerMessages.RULE_WAS_ADDED_TO_TABLE;
import static stringconstant.LoggerMessages.TABLE_WAS_CREATED;
import static stringconstant.StringsConstants.*;

public class SuggestionCounter {
    public void countSuggesions() {

        //Input path to statistic file
        Input input = new Input();
        String filePath = input.input(INPUT_PATH_TO_FILE_WITH_RULES);

        //Input path to rule collection
        String ruleCollectionPath = input.input(INPUT_PATH_TO_RULE_COLLECTION);

        //Read file with single rules
        ReadFile reader = new ReadFile();
        Set<String> lineSet = new HashSet<>(reader.readFile(filePath, SINGLE_RULES_PATTERN));
        ArrayList<String> linesList = new ArrayList<>(lineSet);

        //Reed file with rules collection
        ArrayList<String> rulesLlist = new ArrayList<>();
        rulesLlist.addAll(reader.readFile(ruleCollectionPath, RULES_COLEECTION_PATTERN));

        //Get tables arrays
        ArrayList<Table> tablesList = new ArrayList<>();
        tablesList.addAll(getTableList(linesList));

        for (String rules : rulesLlist) {
            Logger.writeFile(new File("result.csv"), "\"" + rules + "\"");
            for (Table table : tablesList) {
                Calculator calculator = new Calculator();
                Logger.writeFile(new File("result.csv"), calculator.getSuggestions(table, rules));
            }
        }

    }

    public ArrayList<Table> getTableList(ArrayList<String> linesList) {
        ArrayList<Table> result = new ArrayList<Table>();
        for (String line : linesList) {
            Table tempTable = createTable(line);
            if (result.contains(tempTable)) {
                int idx = result.indexOf(tempTable);
                String keyInfo = (line.split(SEMICOLON, 2))[1];
                result.get(idx).addKey(keyInfo);
                Logger.setLog(String.format(RULE_WAS_ADDED_TO_TABLE, keyInfo, tempTable.getName()));

            } else {
                result.add(tempTable);
                Logger.setLog(String.format(TABLE_WAS_CREATED, tempTable.getName()));
            }
        }
        return result;
    }

    public Table createTable(String line) {
        String[] tempArray = line.split(SEMICOLON, 2);
        String tableName = tempArray[0];
        String keyInfo = tempArray[1];
        Table tempTable = new Table(tableName);
        tempTable.addKey(keyInfo);
        return tempTable;
    }
}
