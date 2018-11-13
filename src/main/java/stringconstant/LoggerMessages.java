package stringconstant;

public class LoggerMessages {
    public static final String DONE = "Done: ";
    public static final String ERROR = "Error: ";
    public static final String WARNING = "Warning: ";

    public static final String SEPARATOR = "-----------------------------------------------------------------------------";

    public static final String FILE = DONE + "File ";
    public static final String LINE_READ = DONE + "Line was read: ";
    public static final String READ_SUCCESSFULLY = " was read successfully";
    public static final String FILE_NOT_FOUND = ERROR + "File not found";
    public static final String COULD_NOT_READ_FILE = ERROR + "Could not read file";
    public static final String PATH_TO_TESTLIST_MISSING = ERROR + "The path to file is missing";
    public static final String COULD_NOT_READ_LINE = ERROR + "Could not read line";
    public static final String LINE_READ_SUCCESSFULLY = DONE + "Line was successfully read";
    public static final String TABLE_WAS_CREATED = DONE + "Table %s was created";
    public static final String RULE_WAS_ADDED_TO_TABLE = DONE + "Rule %s was added to table %s";
    public static final String STRING_EMPTY = WARNING + "The string is empty";
    public static final String STRING_HAS_WRONG_FORMAT = ERROR + "Read string has wrong format: ";
    public static final String SUGGESYION_CALCULATION_START = ">>Suggestions calculation for Table %s %s is started";
    public static final String GOT_DK_FOR_CALCULATION = DONE + "Got Distribution Keys which will be used for calculation:";
    public static final String GOT_SK_FOR_CALCULATION = DONE + "Got Sort Keys which will be used for calculation:";
    public static final String KEY_ALREADY_EXISTS = DONE + "Key with columns %s already exists, add the Confidence of two keys";
    public static final String COLUMNS_CONFIDENCE = "Columns: %s Confidence: %s";
    public static final String KEY_WILL_BE_EXCLUDED = WARNING + "Key %s %s has to small confident (threshold=%s). It will be excluded from calculation.";

}
