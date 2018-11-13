public class Key {
    private double ruleNumber;
    private String dk_sk;
    private String style;
    private String columns;
    private double weight;

    public Key(double ruleNumber, String dk_sk, String style, String columns, double weight) {
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

    public double getRule() {
        return ruleNumber;
    }

    public String getColumns() {
        return columns;
    }

    public double getWeight() {
        return weight;
    }

}

