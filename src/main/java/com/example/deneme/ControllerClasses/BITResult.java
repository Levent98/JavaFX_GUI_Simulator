package com.example.deneme.ControllerClasses;


public class BITResult {

    public enum eeprom {
        AMTB_BIT_RES_NOT_TESTED(0),
        AMTB_BIT_RES_OK(1),
        AMTB_BIT_RES_FAIL(2);

        private final int value;

        eeprom(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String eeprom_) {
            for (eeprom e : values()) {
                if (e.name().equalsIgnoreCase(eeprom_)) {
                    return e.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + eeprom_);
        }

    }

    public enum mybComm {
        AMTB_BIT_RES_NOT_TESTED(0),
        AMTB_BIT_RES_OK(1),
        AMTB_BIT_RES_FAIL(2);

        private final int value;

        mybComm(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String mybComm_) {
            for (mybComm m : values()) {
                if (m.name().equalsIgnoreCase(mybComm_)) {
                    return m.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + mybComm_);
        }
    }

    public enum scanVoltage {
        AMTB_BIT_RES_NOT_TESTED(0),
        AMTB_BIT_RES_OK(1),
        AMTB_BIT_RES_FAIL(2);

        private final int value;

        scanVoltage(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String scanVoltage_) {
            for (scanVoltage scanVoltage : values()) {
                if (scanVoltage.name().equalsIgnoreCase(scanVoltage_)) {
                    return scanVoltage.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + scanVoltage_);
        }
    }

    public enum smartComm1 {
        AMTB_BIT_RES_NOT_TESTED(0),
        AMTB_BIT_RES_OK(1),
        AMTB_BIT_RES_FAIL(2);

        private final int value;

        smartComm1(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String smartComm) {
            for (smartComm1 smartComm1 : values()) {
                if (smartComm1.name().equalsIgnoreCase(smartComm)) {
                    return smartComm1.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + smartComm);
        }
    }

    public enum smartComm2 {
        AMTB_BIT_RES_NOT_TESTED(0),
        AMTB_BIT_RES_OK(1),
        AMTB_BIT_RES_FAIL(2);

        private final int value;

        smartComm2(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String smartComm_2) {
            for (smartComm2 smartComm2 : values()) {
                if (smartComm2.name().equalsIgnoreCase(smartComm_2)) {
                    return smartComm2.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + smartComm_2);
        }
    }

    public enum sqbCurrent1 {
        AMTB_BIT_RES_NOT_TESTED(0),
        AMTB_BIT_RES_OK(1),
        AMTB_BIT_RES_FAIL(2);

        private final int value;

        sqbCurrent1(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String sqbCurrent) {
            for (sqbCurrent1 sqbCurrent1 : values()) {
                if (sqbCurrent1.name().equalsIgnoreCase(sqbCurrent)) {
                    return sqbCurrent1.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + sqbCurrent);
        }
    }

    public enum sqbCurrent2 {
        AMTB_BIT_RES_NOT_TESTED(0),
        AMTB_BIT_RES_OK(1),
        AMTB_BIT_RES_FAIL(2);

        private final int value;

        sqbCurrent2(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String sqbCurrent_2) {
            for (sqbCurrent2 sqbCurrent2 : values()) {
                if (sqbCurrent2.name().equalsIgnoreCase(sqbCurrent_2)) {
                    return sqbCurrent2.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + sqbCurrent_2);
        }
    }

    public enum sqbVoltage1 {
        AMTB_BIT_RES_NOT_TESTED(0),
        AMTB_BIT_RES_OK(1),
        AMTB_BIT_RES_FAIL(2);

        private final int value;

        sqbVoltage1(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String sqbVoltage) {
            for (sqbVoltage1 sqbVoltage1 : values()) {
                if (sqbVoltage1.name().equalsIgnoreCase(sqbVoltage)) {
                    return sqbVoltage1.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + sqbVoltage);
        }
    }

    public enum sqbVoltage2 {
        AMTB_BIT_RES_NOT_TESTED(0),
        AMTB_BIT_RES_OK(1),
        AMTB_BIT_RES_FAIL(2);

        private final int value;

        sqbVoltage2(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String sqbVoltage_2) {
            for (sqbVoltage2 sqbVoltage2 : values()) {
                if (sqbVoltage2.name().equalsIgnoreCase(sqbVoltage_2)) {
                    return sqbVoltage2.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + sqbVoltage_2);
        }
    }

    public enum sqbVoltageIn {
        AMTB_BIT_RES_NOT_TESTED(0),
        AMTB_BIT_RES_OK(1),
        AMTB_BIT_RES_FAIL(2);

        private final int value;

        sqbVoltageIn(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String sqbVoltageIn_) {
            for (sqbVoltageIn sqbVoltageIn : values()) {
                if (sqbVoltageIn.name().equalsIgnoreCase(sqbVoltageIn_)) {
                    return sqbVoltageIn.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + sqbVoltageIn_);
        }
    }

    public enum sqbVoltageArm {
        AMTB_BIT_RES_NOT_TESTED(0),
        AMTB_BIT_RES_OK(1),
        AMTB_BIT_RES_FAIL(2);

        private final int value;

        sqbVoltageArm(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String sqbVoltageArm_) {
            for (sqbVoltageArm sqbVoltageArm : values()) {
                if (sqbVoltageArm.name().equalsIgnoreCase(sqbVoltageArm_)) {
                    return sqbVoltageArm.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + sqbVoltageArm_);
        }
    }

    public enum loadCh1 {
        AMTB_BIT_RES_NOT_TESTED(0),
        AMTB_BIT_RES_OK(1),
        AMTB_BIT_RES_FAIL(2);

        private final int value;

        loadCh1(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String loadCh) {
            for (loadCh1 loadCh1 : values()) {
                if (loadCh1.name().equalsIgnoreCase(loadCh)) {
                    return loadCh1.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + loadCh);
        }
    }

    public enum loadCh2 {
        AMTB_BIT_RES_NOT_TESTED(0),
        AMTB_BIT_RES_OK(1),
        AMTB_BIT_RES_FAIL(2);

        private final int value;

        loadCh2(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String loadCh_2) {
            for (loadCh2 loadCh2 : values()) {
                if (loadCh2.name().equalsIgnoreCase(loadCh_2)) {
                    return loadCh2.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + loadCh_2);
        }
    }

    public enum voltageLeakCh1 {
        AMTB_BIT_RES_NOT_TESTED(0),
        AMTB_BIT_RES_OK(1),
        AMTB_BIT_RES_FAIL(2);

        private final int value;

        voltageLeakCh1(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String voltageLeakCh) {
            for (voltageLeakCh1 voltageLeakCh1 : values()) {
                if (voltageLeakCh1.name().equalsIgnoreCase(voltageLeakCh)) {
                    return voltageLeakCh1.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + voltageLeakCh);
        }
    }

    public enum voltageLeakCh2 {
        AMTB_BIT_RES_NOT_TESTED(0),
        AMTB_BIT_RES_OK(1),
        AMTB_BIT_RES_FAIL(2);

        private final int value;

        voltageLeakCh2(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String voltageLeakCh_2) {
            for (voltageLeakCh2 voltageLeakCh2 : values()) {
                if (voltageLeakCh2.name().equalsIgnoreCase(voltageLeakCh_2)) {
                    return voltageLeakCh2.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + voltageLeakCh_2);
        }
    }

    public enum currentAboveCh1 {
        AMTB_BIT_RES_NOT_TESTED(0),
        AMTB_BIT_RES_OK(1),
        AMTB_BIT_RES_FAIL(2);

        private final int value;

        currentAboveCh1(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String currentAboveCh) {
            for (currentAboveCh1 currentAboveCh1 : values()) {
                if (currentAboveCh1.name().equalsIgnoreCase(currentAboveCh)) {
                    return currentAboveCh1.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + currentAboveCh);
        }
    }

    public enum currentAboveCh2 {
        AMTB_BIT_RES_NOT_TESTED(0),
        AMTB_BIT_RES_OK(1),
        AMTB_BIT_RES_FAIL(2);

        private final int value;

        currentAboveCh2(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String currentAboveCh_2) {
            for (currentAboveCh2 currentAboveCh2 : values()) {
                if (currentAboveCh2.name().equalsIgnoreCase(currentAboveCh_2)) {
                    return currentAboveCh2.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + currentAboveCh_2);
        }
    }

    public enum  currentBelowCh1{
        AMTB_BIT_RES_NOT_TESTED(0),
        AMTB_BIT_RES_OK(1),
        AMTB_BIT_RES_FAIL(2);

        private final int value;

        currentBelowCh1(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String currentBelowCh) {
            for (currentBelowCh1 c : values()) {
                if (c.name().equalsIgnoreCase(currentBelowCh)) {
                    return c.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + currentBelowCh);
        }
    }

    public enum  currentBelowCh2{
        AMTB_BIT_RES_NOT_TESTED(0),
        AMTB_BIT_RES_OK(1),
        AMTB_BIT_RES_FAIL(2);

        private final int value;

        currentBelowCh2(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String currentBelowCh_2) {
            for (currentBelowCh2 currentBelowCh2 : values()) {
                if (currentBelowCh2.name().equalsIgnoreCase(currentBelowCh_2)) {
                    return currentBelowCh2.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + currentBelowCh_2);
        }
    }

    public enum  dischargeCh1{
        AMTB_BIT_RES_NOT_TESTED(0),
        AMTB_BIT_RES_OK(1),
        AMTB_BIT_RES_FAIL(2);

        private final int value;

        dischargeCh1(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String dischargeCh) {
            for (BITResult.dischargeCh1 dischargeCh1 : values()) {
                if (dischargeCh1.name().equalsIgnoreCase(dischargeCh)) {
                    return dischargeCh1.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + dischargeCh);
        }
    }

    public enum  dischargeCh2{
        AMTB_BIT_RES_NOT_TESTED(0),
        AMTB_BIT_RES_OK(1),
        AMTB_BIT_RES_FAIL(2);

        private final int value;

        dischargeCh2(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String dischargeCh_2) {
            for (dischargeCh2 dischargeCh2 : values()) {
                if (dischargeCh2.name().equalsIgnoreCase(dischargeCh_2)) {
                    return dischargeCh2.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + dischargeCh_2);
        }
    }

    public enum  currentLeakCh1{
        AMTB_BIT_RES_NOT_TESTED(0),
        AMTB_BIT_RES_OK(1),
        AMTB_BIT_RES_FAIL(2);

        private final int value;

        currentLeakCh1(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String currentLeakCh) {
            for (currentLeakCh1 currentLeakCh1 : values()) {
                if (currentLeakCh1.name().equalsIgnoreCase(currentLeakCh)) {
                    return currentLeakCh1.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + currentLeakCh);
        }
    }

    public enum  currentLeakCh2{
        AMTB_BIT_RES_NOT_TESTED(0),
        AMTB_BIT_RES_OK(1),
        AMTB_BIT_RES_FAIL(2);

        private final int value;

        currentLeakCh2(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String currentLeakCh_2) {
            for (currentLeakCh2 currentLeakCh2 : values()) {
                if (currentLeakCh2.name().equalsIgnoreCase(currentLeakCh_2)) {
                    return currentLeakCh2.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + currentLeakCh_2);
        }
    }

    public enum  eepromWrite{
        AMTB_BIT_RES_NOT_TESTED(0),
        AMTB_BIT_RES_OK(1),
        AMTB_BIT_RES_FAIL(2);

        private final int value;

        eepromWrite(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String eepromWrite_) {
            for (eepromWrite eepromWrite : values()) {
                if (eepromWrite.name().equalsIgnoreCase(eepromWrite_)) {
                    return eepromWrite.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + eepromWrite_);
        }
    }

    public enum  magazineCover {
        AMTB_BIT_RES_NOT_TESTED(0),
        AMTB_BIT_RES_OK(1),
        AMTB_BIT_RES_FAIL(2);

        private final int value;

        magazineCover(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String magazineCover_) {
            for (magazineCover magazineCover : values()) {
                if (magazineCover.name().equalsIgnoreCase(magazineCover_)) {
                    return magazineCover.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + magazineCover_);
        }
    }

    public enum  logbackup{
        AMTB_BIT_RES_NOT_TESTED(0),
        AMTB_BIT_RES_OK(1),
        AMTB_BIT_RES_FAIL(2);

        private final int value;

        logbackup(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String logbackup_) {
            for (logbackup logbackup : values()) {
                if (logbackup.name().equalsIgnoreCase(logbackup_)) {
                    return logbackup.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + logbackup_);
        }
    }

    public enum  ram {
        AMTB_BIT_RES_NOT_TESTED(0),
        AMTB_BIT_RES_OK(1),
        AMTB_BIT_RES_FAIL(2);

        private final int value;

        ram(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String ram_) {
            for (ram ram : values()) {
                if (ram.name().equalsIgnoreCase(ram_)) {
                    return ram.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + ram_);
        }
    }


    private int amtbNo;
    private eeprom Eeprom;
    private mybComm MybComm;
    private scanVoltage ScanVoltage;
    private smartComm1 SmartComm1;
    private smartComm2 SmartComm2;
    private sqbCurrent1 SqbCurrent1;
    private sqbCurrent2 SqbCurrent2;
    private sqbVoltage1 SqbVoltage1;
    private sqbVoltage2 SqbVoltage2;
    private sqbVoltageArm SqbVoltageArm;
    private sqbVoltageIn SqbVoltageIn;
    private loadCh1 LoadCh1;
    private loadCh2 LoadCh2;
    private voltageLeakCh1 VoltageLeakCh1;
    private voltageLeakCh2 VoltageLeakCh2;
    private currentAboveCh1 CurrentAboveCh1;
    private currentAboveCh2 CurrentAboveCh2;
    private currentBelowCh1 CurrentBelowCh1;
    private currentBelowCh2 CurrentBelowCh2;
    private dischargeCh1 DischargeCh1;
    private dischargeCh2 DischargeCh2;
    private currentLeakCh1 CurrentLeakCh1;
    private currentLeakCh2 CurrentLeakCh2;
    private eepromWrite EepromWrite;
    private magazineCover MagazineCover;
    private logbackup Logbackup;
    private ram RAM;

    public BITResult(int amtbNo){
        this.amtbNo          = amtbNo;
        this.Eeprom          = eeprom.AMTB_BIT_RES_OK;
        this.MybComm         = mybComm.AMTB_BIT_RES_OK;
        this.ScanVoltage     = scanVoltage.AMTB_BIT_RES_OK;
        this.SmartComm1      = smartComm1.AMTB_BIT_RES_OK;
        this.SmartComm2      = smartComm2.AMTB_BIT_RES_OK;
        this.SqbCurrent1     = sqbCurrent1.AMTB_BIT_RES_OK;
        this.SqbCurrent2     = sqbCurrent2.AMTB_BIT_RES_OK;
        this.SqbVoltage1     = sqbVoltage1.AMTB_BIT_RES_OK;
        this.SqbVoltage2     = sqbVoltage2.AMTB_BIT_RES_OK;
        this.SqbVoltageIn    = sqbVoltageIn.AMTB_BIT_RES_OK;
        this.SqbVoltageArm   = sqbVoltageArm.AMTB_BIT_RES_OK;
        this.LoadCh1         = loadCh1.AMTB_BIT_RES_OK;
        this.LoadCh2         = loadCh2.AMTB_BIT_RES_OK;
        this.VoltageLeakCh1  = voltageLeakCh1.AMTB_BIT_RES_OK;
        this.VoltageLeakCh2  = voltageLeakCh2.AMTB_BIT_RES_OK;
        this.CurrentAboveCh1 = currentAboveCh1.AMTB_BIT_RES_OK;
        this.CurrentAboveCh2 = currentAboveCh2.AMTB_BIT_RES_OK;
        this.CurrentBelowCh1 = currentBelowCh1.AMTB_BIT_RES_OK;
        this.CurrentBelowCh2 = currentBelowCh2.AMTB_BIT_RES_OK;
        this.DischargeCh1    = dischargeCh1.AMTB_BIT_RES_OK;
        this.DischargeCh2    = dischargeCh2.AMTB_BIT_RES_OK;
        this.CurrentLeakCh1  = currentLeakCh1.AMTB_BIT_RES_OK;
        this.CurrentLeakCh2  = currentLeakCh2.AMTB_BIT_RES_OK;
        this.EepromWrite     = eepromWrite.AMTB_BIT_RES_OK;
        this.MagazineCover   = magazineCover.AMTB_BIT_RES_OK;
        this.Logbackup       = logbackup.AMTB_BIT_RES_OK;
        this.RAM             = ram.AMTB_BIT_RES_OK;

    }

    public int getAmtbNo() { return amtbNo; }
    public void setAmtbNo(int amtbNo) { this.amtbNo = amtbNo; }

    public eeprom getEeprom() { return Eeprom; }
    public void setEeprom(eeprom eeprom) { this.Eeprom = eeprom; }

    public mybComm getMybComm() { return MybComm; }
    public void setMybComm(mybComm mybComm) { this.MybComm = mybComm; }

    public scanVoltage getScanVoltage() { return ScanVoltage; }
    public void setScanVoltage(scanVoltage scanVoltage) { this.ScanVoltage = scanVoltage; }

    public smartComm1 getSmartComm1() { return SmartComm1; }
    public void setSmartComm1(smartComm1 smartComm1) { this.SmartComm1 = smartComm1; }

    public smartComm2 getSmartComm2() { return SmartComm2; }
    public void setSmartComm2(smartComm2 smartComm2) { this.SmartComm2 = smartComm2; }

    public sqbCurrent1 getSqbCurrent1() { return SqbCurrent1; }
    public void setSqbCurrent1(sqbCurrent1 sqbCurrent1) { this.SqbCurrent1 = sqbCurrent1; }

    public sqbCurrent2 getSqbCurrent2() { return SqbCurrent2; }
    public void setSqbCurrent2(sqbCurrent2 sqbCurrent2) { this.SqbCurrent2 = sqbCurrent2; }

    public sqbVoltage1 getSqbVoltage1() { return SqbVoltage1; }
    public void setSqbVoltage1(sqbVoltage1 sqbVoltage1) { this.SqbVoltage1 = sqbVoltage1; }

    public sqbVoltage2 getSqbVoltage2() { return SqbVoltage2; }
    public void setSqbVoltage2(sqbVoltage2 sqbVoltage2) { this.SqbVoltage2 = sqbVoltage2; }

    public sqbVoltageIn getsqbVoltageIn() { return SqbVoltageIn; }
    public void setsqbVoltageIn(sqbVoltageIn sqbVoltageIn) { this.SqbVoltageIn = sqbVoltageIn; }

    public sqbVoltageArm getSqbVoltageArm() { return SqbVoltageArm; }
    public void setSqbVoltageArm(sqbVoltageArm sqbVoltageArm) { this.SqbVoltageArm = sqbVoltageArm; }

    public loadCh1 getLoadCh1() { return LoadCh1; }
    public void setLoadCh1(loadCh1 loadCh1) { this.LoadCh1 = loadCh1; }

    public loadCh2 getLoadCh2() { return LoadCh2; }
    public void setLoadCh2(loadCh2 loadCh2) { this.LoadCh2 = loadCh2; }

    public voltageLeakCh1 getVoltageLeakCh1() { return VoltageLeakCh1; }
    public void setVoltageLeakCh1(voltageLeakCh1 voltageLeakCh1) { this.VoltageLeakCh1 = voltageLeakCh1; }

    public voltageLeakCh2 getVoltageLeakCh2() { return VoltageLeakCh2; }
    public void setVoltageLeakCh2(voltageLeakCh2 voltageLeakCh2) { this.VoltageLeakCh2 = voltageLeakCh2; }

    public currentAboveCh1 getCurrentAboveCh1() { return CurrentAboveCh1; }
    public void setCurrentAboveCh1(currentAboveCh1 currentAboveCh1) { this.CurrentAboveCh1 = currentAboveCh1; }

    public currentAboveCh2 getCurrentAboveCh2() { return CurrentAboveCh2; }
    public void setCurrentAboveCh2(currentAboveCh2 currentAboveCh2) { this.CurrentAboveCh2 = currentAboveCh2; }

    public currentBelowCh1 getCurrentBelowCh1() { return CurrentBelowCh1; }
    public void setCurrentBelowCh1(currentBelowCh1 currentBelowCh1) { this.CurrentBelowCh1 = currentBelowCh1; }

    public currentBelowCh2 getCurrentBelowCh2() { return CurrentBelowCh2; }
    public void setCurrentBelowCh2(currentBelowCh2 currentBelowCh2) { this.CurrentBelowCh2 = currentBelowCh2; }

    public dischargeCh1 getDischargeCh1() { return DischargeCh1; }
    public void setDischargeCh1(dischargeCh1 dischargeCh1) { this.DischargeCh1 = dischargeCh1; }

    public dischargeCh2 getDischargeCh2() { return DischargeCh2; }
    public void setDischargeCh2(dischargeCh2 dischargeCh2) { this.DischargeCh2 = dischargeCh2; }

    public currentLeakCh1 getCurrentLeakCh1() { return CurrentLeakCh1; }
    public void setCurrentLeakCh1(currentLeakCh1 currentLeakCh1) { this.CurrentLeakCh1 = currentLeakCh1; }

    public currentLeakCh2 getCurrentLeakCh2() { return CurrentLeakCh2; }
    public void setCurrentLeakCh2(currentLeakCh2 currentLeakCh2) { this.CurrentLeakCh2 = currentLeakCh2; }

    public eepromWrite getEepromWrite() { return EepromWrite; }
    public void setEepromWrite(eepromWrite eepromWrite) { this.EepromWrite = eepromWrite; }

    public magazineCover getMagazineCover() { return MagazineCover; }
    public void setMagazineCover(magazineCover magazineCover) { this.MagazineCover = magazineCover; }

    public logbackup getLogbackup() {return Logbackup;}
    public void setLogbackup(logbackup logbackup) {Logbackup = logbackup;}

    public ram getRAM() {return RAM;}
    public void setRAM(ram RAM) {this.RAM = RAM;}
}








