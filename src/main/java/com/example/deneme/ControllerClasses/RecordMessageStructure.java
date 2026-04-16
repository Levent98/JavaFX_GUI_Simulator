package com.example.deneme.ControllerClasses;

import javafx.fxml.Initializable;

public class RecordMessageStructure {


//RecordSendCount Message

    public int eventCount;
    public int faultCount;
    public int misfireCount;

    public enum RecordType {
        RECORD_TYPE_EVENT(0),
        RECORD_TYPE_FAULT(1),
        RECORD_TYPE_MISFIRE(2);


        private final int value;

        RecordType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String RecordType_) {
            for (RecordMessageStructure.RecordType RecordType : values()) {
                if (RecordType.name().equalsIgnoreCase(RecordType_)) {
                    return RecordType.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + RecordType_);
        }
    }

    private int amtbNo;
    private RecordMessageStructure.RecordType RecordType;


    public RecordMessageStructure(int amtbNo) {
        this.amtbNo = amtbNo;
        this.RecordType = RecordType.RECORD_TYPE_EVENT;

    }




    public int getAmtbNo() { return amtbNo; }
    public void setAmtbNo(int amtbNo) { this.amtbNo = amtbNo; }


    public RecordMessageStructure.RecordType getRecordType() {return RecordType;}

    public void setRecordType(RecordMessageStructure.RecordType RecordType) {this.RecordType = RecordType;}

    public int getEventCount() {return eventCount;}

    public void setEventCount(int eventCount) {this.eventCount = eventCount;}

    public int getFaultCount() {return faultCount;}

    public void setFaultCount(int faultCount) {this.faultCount = faultCount;}

    public int getMisfireCount() {return misfireCount;}

    public void setMisfireCount(int misfireCount) {this.misfireCount = misfireCount;}

}


