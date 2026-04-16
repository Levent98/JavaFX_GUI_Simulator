package com.example.deneme.ControllerClasses;

public class AMTBState {

    //public static byte AmtbMode;

    public enum StateInfo {
        AKTIF,
        PASIF;

        @Override
        public String toString() {
            return name();
        }
    }

    public enum AmtbMode {
        AMTB_MODE_OFF(0),
        AMTB_MODE_STBY(1),
        AMTB_MODE_ALIVE(2),
        AMTB_MODE_BYPASS(3),
        AMTB_MODE_ULTRA_BYPASS_FIS(4),
        AMTB_MODE_ULTRA_BYPASS_RIAS(5),
        AMTB_MODE_ULTRA_BYPASS_FIS_RIAS(6);

        private final int value;

        AmtbMode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String mode) {
            for (AmtbMode m : values()) {
                if (m.name().equalsIgnoreCase(mode)) {
                    return m.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + mode);
        }
    }

    public enum JettisonState {
        JETTISON_STATE_NOT_STARTED(0),
        JETTISON_STATE_RUNNING(1),
        JETTISON_STATE_FINISHED(2);

        private final int value;

        JettisonState(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String mode) {
            for (JettisonState m : values()) {
                if (m.name().equalsIgnoreCase(mode)) {
                    return m.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + mode);
        }
    }

    public enum ArmedState {
        DISARM(0),
        ARM(1);

        private final int value;

        ArmedState(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return name();
        }
    }
    public enum IsArmed{
        DISARM(0),
        ARM(1);

        private final int value;

        IsArmed(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString(){
            return name();
        }
    }

   public enum WOWState{
        HAVADA(0),
        YERDE(1);

       private final int value;

       WOWState(int value) {
           this.value = value;
       }

       public int getValue() {
           return value;
       }

        @Override
        public String toString(){
            return name();
        }
   }

   public enum DiscreteTest{
        PASIF(0),
        AKTIF(1);

       private final int value;

       DiscreteTest(int value) {
           this.value = value;
       }

       public int getValue() {
           return value;
       }

       @Override
       public String toString(){
           return name();
       }

   }

   public enum MDFState{
        VAR(1),
        YOK(0);

       private final int value;

       MDFState(int value) {
           this.value = value;
       }

       public int getValue() {
           return value;
       }

       @Override
       public String toString(){
           return name();
       }
   }

   public enum MDFLoadingState{
        VARSAYILAN(0),
        YUKLENMEKTE(1);

       private final int value;

       MDFLoadingState(int value) {
           this.value = value;
       }

       public int getValue() {
           return value;
       }

       @Override
       public String toString(){
           return name();
       }

   }

   public enum MagCoverState{
        KAPALI(0),
        ACIK(1);

       private final int value;

       MagCoverState(int value) {
           this.value = value;
       }

       public int getValue() {
           return value;
       }

       @Override
       public String toString(){
           return name();
       }
   }

   public enum IBIT{
        TAMAMLANMADI(0),
        TAMAMLANDI(1);

       private final int value;

       IBIT(int value) {
           this.value = value;
       }

       public int getValue() {
           return value;
       }

       @Override
       public String toString(){
           return name();
       }
   }

   public enum FAILState {
       ARIZA_YOK(0),
       ARIZA_VAR(1);

       private final int value;

       FAILState(int value) {
           this.value = value;
       }

       public int getValue() {
           return value;
       }

       @Override
       public String toString(){
           return name();
       }
   }

   public enum NewFail{
       YENI_ARIZA_OLUSMADI(0),
       YENI_ARIZA_OLUSTU(1);


       private final int value;

       NewFail(int value) {
           this.value = value;
       }

       public int getValue() {
           return value;
       }

       @Override
       public String toString(){
           return name();
       }
   }

   public enum SQBPower{
        YOK(0),
        VAR(1);

       private final int value;

       SQBPower(int value) {
           this.value = value;
       }

       public int getValue() {
           return value;
       }

       @Override
       public String toString(){
           return name();
       }

   }

   public enum RTC{
       SET_EDILMEMIS(0),
       SET_EDILMIS(1);

       private final int value;

       RTC(int value) {
           this.value = value;
       }

       public int getValue() {
           return value;
       }

       @Override
       public String toString(){
           return name();
       }
   }

   public enum STCConnected{
        BAGLI_DEGIL(0),
        BAGLI(1);

       private final int value;

       STCConnected(int value) {
           this.value = value;
       }

       public int getValue() {
           return value;
       }

       @Override
       public String toString(){
           return name();
       }
   }

   public enum BlankIR{
        PASIF(0),
        AKTIF(1);

       private final int value;

       BlankIR(int value) {
           this.value = value;
       }

       public int getValue() {
           return value;
       }

       @Override
       public String toString(){
           return name();
       }
   }

   public enum BlankRF{
        PASIF(0),
        AKTIF(1);

       private final int value;

       BlankRF(int value) {
           this.value = value;
       }

       public int getValue() {
           return value;
       }

       @Override
       public String toString(){
           return name();
       }
   }

    public enum IsAMTB6x5{
        SIX_BY_FIVE(0),
        THREE_BY_TEN(1);

        private final int value;

        IsAMTB6x5(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString(){
            return name();
        }
    }

/*    public enum IsAMTB6x5 {
        SIX_BY_FIVE("6x5"),
        THREE_BY_TEN("3x10");

        private final String label;

        IsAMTB6x5(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }

        public static IsAMTB6x5 fromString(String label) {
            for (IsAMTB6x5 value : values()) {
                if (value.label.equalsIgnoreCase(label)) {
                    return value;
                }
            }
            throw new IllegalArgumentException("Bilinmeyen IsAMTB6x5: " + label);
        }
    }*/

    public enum Misfire{
        YOK(0),
        VAR(1);

        private final int value;

        Misfire(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString(){
            return name();
        }
    }

    public enum NewMisfire{
        OLUSMADI(0),
        OLUSTU(1);

        private final int value;

        NewMisfire(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString(){
            return name();
        }
    }

    public enum IsFiringActive{
        ATIM_YAPILMIYOR(0),
        ATIM_YAPILIYOR(1);

        private final int value;

        IsFiringActive(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString(){
            return name();
        }
    }

    private int amtbNo;

    private StateInfo stateInfo;
    private AmtbMode amtbMode;
    private JettisonState jettisonState;
    private ArmedState armedState;
    private IsArmed isArmed;
    private WOWState wowState;
    private DiscreteTest discreteTest;
    private MDFState mdfState;
    private MDFLoadingState mdfLoadingState;
    private MagCoverState magCoverState;
    private IBIT ibit;
    private FAILState failState;
    private NewFail newFail;
    private SQBPower sqbPower;
    private RTC rtc;
    private STCConnected stcConnected;
    private BlankIR blankIR;
    private BlankRF blankRF;
    private IsAMTB6x5 isAMTB6x5;
    private Misfire misfire;
    private NewMisfire newMisfire;
    private IsFiringActive isFiringActive;

    public AMTBState()
    {

    }

    public AMTBState(int amtbNo) {
        this.amtbNo = amtbNo;
        // default değerleri atayabilirsin
        this.stateInfo = StateInfo.PASIF;
        this.amtbMode = AmtbMode.AMTB_MODE_ALIVE;
        this.jettisonState = JettisonState.JETTISON_STATE_NOT_STARTED;
        this.armedState = ArmedState.DISARM;
        this.isArmed = IsArmed.ARM;
        this.wowState = WOWState.HAVADA;
        this.discreteTest = DiscreteTest.PASIF;
        this.mdfState = MDFState.VAR;
        this.mdfLoadingState = MDFLoadingState.VARSAYILAN;
        this.magCoverState = MagCoverState.ACIK;
        this.ibit = IBIT.TAMAMLANMADI;
        this.failState = FAILState.ARIZA_YOK;
        this.newFail =  NewFail.YENI_ARIZA_OLUSMADI;
        this.sqbPower = SQBPower.YOK;
        this.rtc = RTC.SET_EDILMIS;
        this.stcConnected = STCConnected.BAGLI_DEGIL;
        this.blankIR = BlankIR.PASIF;
        this.blankRF = BlankRF.PASIF;
        this.isAMTB6x5 = IsAMTB6x5.THREE_BY_TEN;
        this.misfire = Misfire.YOK;
        this.newMisfire = NewMisfire.OLUSMADI;
        this.isFiringActive = IsFiringActive.ATIM_YAPILMIYOR;
    }

    // getter ve setter metodları
    public int getAmtbNo() { return amtbNo; }

    public StateInfo getStateInfo() {
        return stateInfo;
    }
    public void setStateInfo(StateInfo stateInfo) {
        this.stateInfo = stateInfo;
    }

    public JettisonState getJettisonState() {
        return jettisonState;
    }
    public void setJettisonState(JettisonState jettisonState) {
        this.jettisonState = jettisonState;
    }

    public AmtbMode getAmtbMode() { return amtbMode; }
    public void setAmtbMode(AmtbMode mode) { this.amtbMode = mode; }

    public ArmedState getArmedState() {
        return armedState;
    }
    public void setArmedState(ArmedState armedState) {
        this.armedState = armedState;
    }

    public IsArmed getIsArmed() {
        return isArmed;
    }
    public void setIsArmed(IsArmed isArmed) {
        this.isArmed = isArmed;
    }

    public WOWState getWowState(){
        return wowState;
    }
    public void setWowState(WOWState wowState){
        this.wowState = wowState;
    }

    public DiscreteTest getDiscreteTest() {
        return discreteTest;
    }
    public void setDiscreteTest(DiscreteTest discreteTest) {
        this.discreteTest = discreteTest;
    }

    public MDFState getMdfState() {
        return mdfState;
    }
    public void setMdfState(MDFState mdfState) {
        this.mdfState = mdfState;
    }

    public MDFLoadingState getMdfLoadingState() {
        return mdfLoadingState;
    }
    public void setMdfLoadingState(MDFLoadingState mdfLoadingState) {
        this.mdfLoadingState = mdfLoadingState;
    }

    public MagCoverState getMagCoverState() {
        return magCoverState;
    }
    public void setMagCoverState(MagCoverState magCoverState) {
        this.magCoverState = magCoverState;
    }

    public IBIT getIbit() {
        return ibit;
    }
    public void setIbit(IBIT ibit) {
        this.ibit = ibit;
    }

    public FAILState getFailState() {
        return failState;
    }
    public void setFailState(FAILState failState) {
        this.failState = failState;
    }

    public NewFail getNewFail() {
        return newFail;
    }
    public void setNewFail(NewFail newFail) {
        this.newFail = newFail;
    }

    public SQBPower getSqbPower() {
        return sqbPower;
    }
    public void setSqbPower(SQBPower sqbPower) {
        this.sqbPower = sqbPower;
    }

    public RTC getRtc() {
        return rtc;
    }
    public void setRtc(RTC rtc) {
        this.rtc = rtc;
    }

    public STCConnected getStcConnected() {
        return stcConnected;
    }
    public void setStcConnected(STCConnected stcConnected) {
        this.stcConnected = stcConnected;
    }

    public BlankIR getBlankIR() {
        return blankIR;
    }
    public void setBlankIR(BlankIR blankIR) {
        this.blankIR = blankIR;
    }

    public BlankRF getBlankRF() {
        return blankRF;
    }
    public void setBlankRF(BlankRF blankRF) {
        this.blankRF = blankRF;
    }

    public IsAMTB6x5 getIsAMTB6x5() {
        return isAMTB6x5;
    }
    public void setIsAMTB6x5(IsAMTB6x5 isAMTB6x5) {
        this.isAMTB6x5 = isAMTB6x5;
    }

    public Misfire getMisfire() {
        return misfire;
    }
    public void setMisfire(Misfire misfire) {
        this.misfire = misfire;
    }

    public NewMisfire getNewMisfire() {
        return newMisfire;
    }
    public void setNewMisfire(NewMisfire newMisfire) {
        this.newMisfire = newMisfire;
    }

    public IsFiringActive getIsFiringActive() {
        return isFiringActive;
    }
    public void setIsFiringActive(IsFiringActive isFiringActive) {
        this.isFiringActive = isFiringActive;
    }
}
