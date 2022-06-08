package net.sympower.citizen.apx.dto.incomming;

// Issue with Lombok getters and setter with small "t"
public class QuoteValue {
    private String tLabel;
    private String value;

    public QuoteValue() {

    }

    public QuoteValue(String tLabel, String value) {
        this.tLabel = tLabel;
        this.value = value;
    }

    public String gettLabel() {
        return tLabel;
    }

    public String getValue() {
        return value;
    }

    public void settLabel(String tLabel) {
        this.tLabel = tLabel;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
