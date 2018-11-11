public class Key {
    private int ruleNumber;
    private String dk_sk;
    private String style;
    private String columns;
    private int weight;

    public Key(int ruleNumber, String dk_sk, String style, String columns, int weight) {
        this.ruleNumber = ruleNumber;
        this.dk_sk = dk_sk;
        this.style = style;
        if (columns.isEmpty() && (style.equalsIgnoreCase("all") || style.equalsIgnoreCase("even"))) {
            this.columns = style;
        } else {
            this.columns = columns;
        }
        this.weight = weight;
    }

    public String getType() {
        return dk_sk;
    }

    public int getRule() {
        return ruleNumber;
    }

    public String getColumns() {
        return columns;
    }

    public int getWeight() {
        return weight;
    }

}

