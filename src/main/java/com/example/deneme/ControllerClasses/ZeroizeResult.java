package com.example.deneme.ControllerClasses;

public class ZeroizeResult {

    public enum results {
        AMTB_ZEROIZE_RESULT_NOT_STARTED(0),
        AMTB_ZEROIZE_RESULT_CONTINUE(1),
        AMTB_ZEROIZE_RESULT_FINISHED(2),
        AMTB_ZEROIZE_RESULT_FAILED(3);

        private final int value;

        results(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String results_) {
            for (ZeroizeResult.results results : values()) {
                if (results.name().equalsIgnoreCase(results_)) {
                    return results.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + results_);
        }
    }

    private int amtbNo;
    private ZeroizeResult.results results;


    public ZeroizeResult(int amtbNo) {
        this.amtbNo = amtbNo;
        this.results = results.AMTB_ZEROIZE_RESULT_NOT_STARTED;

    }


    public int getAmtbNo() { return amtbNo; }
    public void setAmtbNo(int amtbNo) { this.amtbNo = amtbNo; }


    public results getResults() {return results;}

    public void setResults(results results) {this.results = results;}

}
