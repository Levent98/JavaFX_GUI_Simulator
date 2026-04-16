package com.example.deneme.ControllerClasses;

import com.example.deneme.AMTBDiscreteLines;

public class AmtbDiscreteLinesMessage {

        public enum WOW {
            ENABLE(1), DISABLE(0);
            private final int value;
            WOW(int value) { this.value = value; }
            public int getValue() { return value; }
        }

        public enum DISPENSE {
            ENABLE(1), DISABLE(0);
            private final int value;
            DISPENSE(int value) { this.value = value; }
            public int getValue() { return value; }
        }

        public enum JETTISON {
            ENABLE(1), DISABLE(0);
            private final int value;
            JETTISON(int value) { this.value = value; }
            public int getValue() { return value; }
        }

        public enum ZEROIZE {
            ENABLE(1), DISABLE(0);
            private final int value;
            ZEROIZE(int value) { this.value = value; }
            public int getValue() { return value; }
        }

        public enum BYPASS_MODE {
            ENABLE(1), DISABLE(0);
            private final int value;
            BYPASS_MODE(int value) { this.value = value; }
            public int getValue() { return value; }
        }

        public enum BYPASS_FIS_MODE {
            ENABLE(1), DISABLE(0);
            private final int value;
            BYPASS_FIS_MODE(int value) { this.value = value; }
            public int getValue() { return value; }
        }

        public enum BYPASS_RIAS_MODE {
            ENABLE(1), DISABLE(0);
            private final int value;
            BYPASS_RIAS_MODE(int value) { this.value = value; }
            public int getValue() { return value; }
        }

        public enum BYP_FIS_DRCT0 {
            ENABLE(1), DISABLE(0);
            private final int value;
            BYP_FIS_DRCT0(int value) { this.value = value; }
            public int getValue() { return value; }
        }

        public enum BYP_FIS_DRCT1 {
            ENABLE(1), DISABLE(0);
            private final int value;
            BYP_FIS_DRCT1(int value) { this.value = value; }
            public int getValue() { return value; }
        }

        public enum BYP_FIS_DRCT2 {
            ENABLE(1), DISABLE(0);
            private final int value;
            BYP_FIS_DRCT2(int value) { this.value = value; }
            public int getValue() { return value; }
        }

        public enum BYP_RIAS_DETECT {
            ENABLE(1), DISABLE(0);
            private final int value;
            BYP_RIAS_DETECT(int value) { this.value = value; }
            public int getValue() { return value; }
        }

        public enum RF_BLANK_OUT {
            ENABLE(1), DISABLE(0);
            private final int value;
            RF_BLANK_OUT(int value) { this.value = value; }
            public int getValue() { return value; }
        }

        public enum IR_BLANK_OUT {
            ENABLE(1), DISABLE(0);
            private final int value;
            IR_BLANK_OUT(int value) { this.value = value; }
            public int getValue() { return value; }
        }

        public enum RF_BLANK_IN {
            ENABLE(1), DISABLE(0);
            private final int value;
            RF_BLANK_IN(int value) { this.value = value; }
            public int getValue() { return value; }
        }

        public enum IR_BLANK_IN {
            ENABLE(1), DISABLE(0);
            private final int value;
            IR_BLANK_IN(int value) { this.value = value; }
            public int getValue() { return value; }
        }

        public enum NO_CHAFF {
            ENABLE(1), DISABLE(0);
            private final int value;
            NO_CHAFF(int value) { this.value = value; }
            public int getValue() { return value; }
        }

        public enum NO_FLARE {
            ENABLE(1), DISABLE(0);
            private final int value;
            NO_FLARE(int value) { this.value = value; }
            public int getValue() { return value; }
        }

        public enum IR_BLANK_OUT_IN {
            ENABLE(1), DISABLE(0);
            private final int value;
            IR_BLANK_OUT_IN(int value) { this.value = value; }
            public int getValue() { return value; }
        }

        public enum RF_BLANK_OUT_IN {
            ENABLE(1), DISABLE(0);
            private final int value;
            RF_BLANK_OUT_IN(int value) { this.value = value; }
            public int getValue() { return value; }
        }

        public enum NO_CHAFF_IN {
            ENABLE(1), DISABLE(0);
            private final int value;
            NO_CHAFF_IN(int value) { this.value = value; }
            public int getValue() { return value; }
        }

        public enum NO_FLARE_IN {
            ENABLE(1), DISABLE(0);
            private final int value;
            NO_FLARE_IN(int value) { this.value = value; }
            public int getValue() { return value; }
        }
    public enum HatNo{
        HAT1(0),
        HAT2(1),
        HAT3(2),
        HAT4(3);

        private final int number;

        HatNo(int value){
            number=value;
        }
        public int getValue() {
            return number;
        }
        public static int getIntFromString(String RecordType_) {
            for (AmtbDiscreteLinesMessage.HatNo hatno : values()) {
                if (hatno.name().equalsIgnoreCase(RecordType_)) {
                    return hatno.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + RecordType_);
        }


    }


    private WOW wow;
    private DISPENSE dispense;
    private JETTISON jettison;
    private ZEROIZE zeroize;
    private BYPASS_MODE bypassMode;
    private BYPASS_FIS_MODE bypassFisMode;
    private BYPASS_RIAS_MODE bypassRiasMode;
    private BYP_FIS_DRCT0 bypFisDrct0;
    private BYP_FIS_DRCT1 bypFisDrct1;
    private BYP_FIS_DRCT2 bypFisDrct2;
    private BYP_RIAS_DETECT bypRiasDetect;
    private RF_BLANK_OUT rfBlankOut;
    private IR_BLANK_OUT irBlankOut;
    private RF_BLANK_IN rfBlankIn;
    private IR_BLANK_IN irBlankIn;
    private NO_CHAFF noChaff;
    private NO_FLARE noFlare;
    private IR_BLANK_OUT_IN irBlankOutIn;
    private RF_BLANK_OUT_IN rfBlankOutIn;
    private NO_CHAFF_IN noChaffIn;
    private NO_FLARE_IN noFlareIn;
    private HatNo hatno;

    public AmtbDiscreteLinesMessage() {
        this.wow = WOW.ENABLE;
        this.dispense = DISPENSE.ENABLE;
        this.jettison = JETTISON.ENABLE;
        this.zeroize = ZEROIZE.ENABLE;
        this.bypassMode = BYPASS_MODE.ENABLE;
        this.bypassFisMode = BYPASS_FIS_MODE.ENABLE;
        this.bypassRiasMode = BYPASS_RIAS_MODE.ENABLE;
        this.bypFisDrct0 = BYP_FIS_DRCT0.ENABLE;
        this.bypFisDrct1 = BYP_FIS_DRCT1.ENABLE;
        this.bypFisDrct2 = BYP_FIS_DRCT2.ENABLE;
        this.bypRiasDetect = BYP_RIAS_DETECT.ENABLE;
        this.rfBlankOut = RF_BLANK_OUT.ENABLE;
        this.irBlankOut = IR_BLANK_OUT.ENABLE;
        this.rfBlankIn = RF_BLANK_IN.ENABLE;
        this.irBlankIn = IR_BLANK_IN.ENABLE;
        this.noChaff = NO_CHAFF.ENABLE;
        this.noFlare = NO_FLARE.ENABLE;
        this.irBlankOutIn = IR_BLANK_OUT_IN.ENABLE;
        this.rfBlankOutIn = RF_BLANK_OUT_IN.ENABLE;
        this.noChaffIn = NO_CHAFF_IN.ENABLE;
        this.noFlareIn = NO_FLARE_IN.ENABLE;
        this.hatno=HatNo.HAT1;
    }

    public WOW getWow() { return wow; }
    public void setWow(WOW wow) { this.wow = wow; }

    public DISPENSE getDispense() { return dispense; }
    public void setDispense(DISPENSE dispense) { this.dispense = dispense; }

    public JETTISON getJettison() { return jettison; }
    public void setJettison(JETTISON jettison) { this.jettison = jettison; }

    public ZEROIZE getZeroize() { return zeroize; }
    public void setZeroize(ZEROIZE zeroize) { this.zeroize = zeroize; }

    public BYPASS_MODE getBypassMode() { return bypassMode; }
    public void setBypassMode(BYPASS_MODE bypassMode) { this.bypassMode = bypassMode; }

    public BYPASS_FIS_MODE getBypassFisMode() { return bypassFisMode; }
    public void setBypassFisMode(BYPASS_FIS_MODE bypassFisMode) { this.bypassFisMode = bypassFisMode; }

    public BYPASS_RIAS_MODE getBypassRiasMode() { return bypassRiasMode; }
    public void setBypassRiasMode(BYPASS_RIAS_MODE bypassRiasMode) { this.bypassRiasMode = bypassRiasMode; }

    public BYP_FIS_DRCT0 getBypFisDrct0() { return bypFisDrct0; }
    public void setBypFisDrct0(BYP_FIS_DRCT0 bypFisDrct0) { this.bypFisDrct0 = bypFisDrct0; }

    public BYP_FIS_DRCT1 getBypFisDrct1() { return bypFisDrct1; }
    public void setBypFisDrct1(BYP_FIS_DRCT1 bypFisDrct1) { this.bypFisDrct1 = bypFisDrct1; }

    public BYP_FIS_DRCT2 getBypFisDrct2() { return bypFisDrct2; }
    public void setBypFisDrct2(BYP_FIS_DRCT2 bypFisDrct2) { this.bypFisDrct2 = bypFisDrct2; }

    public BYP_RIAS_DETECT getBypRiasDetect() { return bypRiasDetect; }
    public void setBypRiasDetect(BYP_RIAS_DETECT bypRiasDetect) { this.bypRiasDetect = bypRiasDetect; }

    public RF_BLANK_OUT getRfBlankOut() { return rfBlankOut; }
    public void setRfBlankOut(RF_BLANK_OUT rfBlankOut) { this.rfBlankOut = rfBlankOut; }

    public IR_BLANK_OUT getIrBlankOut() { return irBlankOut; }
    public void setIrBlankOut(IR_BLANK_OUT irBlankOut) { this.irBlankOut = irBlankOut; }

    public RF_BLANK_IN getRfBlankIn() { return rfBlankIn; }
    public void setRfBlankIn(RF_BLANK_IN rfBlankIn) { this.rfBlankIn = rfBlankIn; }

    public IR_BLANK_IN getIrBlankIn() { return irBlankIn; }
    public void setIrBlankIn(IR_BLANK_IN irBlankIn) { this.irBlankIn = irBlankIn; }

    public NO_CHAFF getNoChaff() { return noChaff; }
    public void setNoChaff(NO_CHAFF noChaff) { this.noChaff = noChaff; }

    public NO_FLARE getNoFlare() { return noFlare; }
    public void setNoFlare(NO_FLARE noFlare) { this.noFlare = noFlare; }

    public IR_BLANK_OUT_IN getIrBlankOutIn() { return irBlankOutIn; }
    public void setIrBlankOutIn(IR_BLANK_OUT_IN irBlankOutIn) { this.irBlankOutIn = irBlankOutIn; }

    public RF_BLANK_OUT_IN getRfBlankOutIn() { return rfBlankOutIn; }
    public void setRfBlankOutIn(RF_BLANK_OUT_IN rfBlankOutIn) { this.rfBlankOutIn = rfBlankOutIn; }

    public NO_CHAFF_IN getNoChaffIn() { return noChaffIn; }
    public void setNoChaffIn(NO_CHAFF_IN noChaffIn) { this.noChaffIn = noChaffIn; }

    public NO_FLARE_IN getNoFlareIn() { return noFlareIn; }
    public void setNoFlareIn(NO_FLARE_IN noFlareIn) { this.noFlareIn = noFlareIn; }

    public HatNo getHatno(){return hatno;}
    public void setHatNo(HatNo val) { this.hatno = val; }







}
