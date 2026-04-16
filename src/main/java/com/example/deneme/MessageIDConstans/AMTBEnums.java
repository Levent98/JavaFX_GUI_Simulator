package com.example.deneme.MessageIDConstans;

public class AMTBEnums {

    public enum amtbCmd {
        AMTB_CMD_STATE_GET(0x0100),
        AMTB_CMD_STATE_SET(0x0101),
        AMTB_CMD_COMMAND_FIRMWARE_VERSION_GET(0x0200),
        AMTB_CMD_COMMAND_FIRMWARE_UPDATE_START(0x0201),
        AMTB_CMD_COMMAND_FIRMWARE_UPDATE_DATA(0x0202),
        AMTB_CMD_COMMAND_FIRMWARE_UPDATE_DATA_ACK(0x0201),
        AMTB_CMD_COMMAND_FIRMWARE_UPDATE_DATA_NAK(0x0201),
        AMTB_CMD_COMMAND_FIRMWARE_UPDATE_END(0x0203),
        AMTB_CMD_COMMAND_FIRMWARE_UPLOAD_INFO(0x0205),
        AMTB_CMD_COMMAND_FIRMWARE_UPLOAD_DATA(0x0206),
        AMTB_CMD_COMMAND_GVD_VERSION_GET(0x0300),
        AMTB_CMD_COMMAND_GVD_UPDATE_START(0x0301),
        AMTB_CMD_COMMAND_GVD_UPDATE_DATA(0x0302),
        AMTB_CMD_COMMAND_GVD_UPDATE_END(0x0303),
        AMTB_CMD_COMMAND_GVD_UPLOAD_INFO(0x0304),
        AMTB_CMD_COMMAND_GVD_UPLOAD_DATA(0x0305),
        AMTB_CMD_COMMAND_GVD_DELETE(0x0306),
        AMTB_CMD_COMMAND_BIT_START(0x0400),
        AMTB_CMD_COMMAND_GET_RESULT(0x0401),
        AMTB_CMD_COMMAND_RTC_SET(0x0500),
        AMTB_CMD_COMMAND_RTC_GET(0x0501),
        AMTB_CMD_COMMAND_BLANKING_SET(0x0600),
        AMTB_CMD_COMMAND_BLANKING_GET(0x0601),
        AMTB_CMD_COMMAND_INVENTORY_GET_CATEGORY_TYPE(0x0700),
        AMTB_CMD_COMMAND_INVENTORY_GET_CELL(0x0701),
        AMTB_CMD_COMMAND_INVENTORY_GET_MAGAZINE_ID(0x0702),
        AMTB_CMD_COMMAND_ZEROIZE_START(0x0800),
        AMTB_CMD_COMMAND_ZEROIZE_GET_INFO(0x0801),
        AMTB_CMD_COMMAND_ASH_FIRMWARE_VERSION_GET(0x0900),
        AMTB_CMD_COMMAND_ASH_FIRMWARE_UPDATE_START(0x0901),
        AMTB_CMD_COMMAND_ASH_FIRMWARE_UPDATE_END(0x0903),
        AMTB_CMD_COMMAND_ASH_PARAMETER_SET(0x0904),
        AMTB_CMD_COMMAND_ASH_DECRYTION_KEY_SEND(0x0905),
        AMTB_CMD_COMMAND_ASH_HARD_KILL_PARAMETER_SET(0x0906),
        AMTB_CMD_COMMAND_RECORD_GET_COUNT(0x0A00),
        AMTB_CMD_COMMAND_RECORD_GET_DATA(0x0A01),
        AMTB_CMD_COMMAND_RECORD_DELETE(0x0A02),
        AMTB_CMD_COMMAND_DICRETE_SET_OUTPUTS(0x0B00),
        AMTB_CMD_COMMAND_DICRETE_GET_INPUTS(0x0B01),
        AMTB_CMD_COMMAND_DICRETE_START_TEST(0x0B02),
        AMTB_CMD_COMMAND_DICRETE_STOP_TEST(0x0B03),
        AMTB_CMD_COMMAND_RESET(0x0C00),
        AMTB_CMD_COMMAND_WORKINGTIME(0x0D00),
        AMTB_CMD_COMMAND_SYNC(0x0E00),
        AMTB_CMD_COMMAND_ALIVE(0x0F00),
        AMTB_CMD_COMMAND_FIRE_LIST_SEND(0x1000),
        AMTB_CMD_COMMAND_FIRE_LIST_GET(0x1001),
        AMTB_CMD_COMMAND_FIRE_START(0x1002),
        AMTB_CMD_COMMAND_FIRE_STOP(0x1003),
        AMTB_CMD_COMMAND_FIRE_MISFIRE_DATA(0x1004),
        AMTB_CMD_COMMAND_FIRE_DIRECT_FIRE_SET(0x1005),
        AMTB_CMD_COMMAND_FIRE_DIRECT_FIRE_GET(0x1006),
        AMTB_CMD_COMMAND_MAGAZINE_COVER_GET(0x1100),
        AMTB_CMD_COMMAND_MAGAZINE_COVER_SET(0x1101),
        AMTB_CMD_COMMAND_TRAINING_GET(0x1200),
        AMTB_CMD_COMMAND_TRAINING_SET(0x1201);

        private final int valueAmtbCmd;  // değeri saklayacak alan

        // Constructor: enum sabiti oluşturulurken değer atanmasını sağlar
        amtbCmd(int valueAmtbCmd) {
            this.valueAmtbCmd = valueAmtbCmd;
        }

        // Değeri dışarıdan almak için getter
        public int getAmtbCmdValue() {
            return valueAmtbCmd;
        }
    }

    public static String getAMTBBIT_e(int val){

        switch (val){
            case 0 : return AMTBMessageState.AMTB_BIT_IBIT;
            case 1 : return AMTBMessageState.AMTB_BIT_PBIT;
            case 2 : return AMTBMessageState.AMTB_BIT_CBIT;
            default: return AMTBMessageState.GELEN_BILGI_HATALI;
        }
    }

    public static String getAmtbBlankingCategory_e(int val){

        switch (val){
            case 0 : return AMTBMessageState.AMTB_BLANKING_TYPE_CHAF;
            case 1 : return AMTBMessageState.AMTB_BLANKING_TYPE_FLAR;
            case 2 : return AMTBMessageState.AMTB_BLANKING_TYPE_ASH;
            case 3 : return AMTBMessageState.AMTB_BLANKING_TYPE_CAT4;
            default: return AMTBMessageState.GELEN_BILGI_HATALI;
        }
    }

    public static String getAmtbZeroizeResults_e(int val){

        switch (val){
            case 0 : return AMTBMessageState.AMTB_ZEROIZE_RESULT_NOT_STARTED;
            case 1 : return AMTBMessageState.AMTB_ZEROIZE_RESULT_CONTINUE;
            case 2 : return AMTBMessageState.AMTB_ZEROIZE_RESULT_FINISHED;
            case 3 : return AMTBMessageState.AMTB_ZEROIZE_RESULT_FAILED;
            default : return AMTBMessageState.GELEN_BILGI_HATALI;
        }
    }

    public static String getAmtbModesStr_e(int val){

        switch (val){
            case 0 : return AMTBMessageState.AMTB_MODE_OFF;
            case 1 : return AMTBMessageState.AMTB_MODE_STBY;
            case 2 : return AMTBMessageState.AMTB_MODE_ALIVE;
            case 3 : return AMTBMessageState.AMTB_MODE_BYPASS;
            case 4 : return AMTBMessageState.AMTB_MODE_ULTRA_BYPASS_FIS;
            case 5 : return AMTBMessageState.AMTB_MODE_ULTRA_BYPASS_RIAS;
            case 6 : return AMTBMessageState.AMTB_MODE_ULTRA_BYPASS_FIS_RIAS;
            default : return AMTBMessageState.GELEN_BILGI_HATALI;
        }
    }
    public static byte getAmtbModesInt_e(String amtbModes)
    {
        switch ( amtbModes.toUpperCase())
        {
            case AMTBMessageState.AMTB_MODE_OFF:
                return (byte) 0;
            case AMTBMessageState.AMTB_MODE_STBY:
                return (byte) 1;
            case AMTBMessageState.AMTB_MODE_ALIVE:
                return (byte) 2;
            case AMTBMessageState.AMTB_MODE_BYPASS:
                return (byte) 3;
            case AMTBMessageState.AMTB_MODE_ULTRA_BYPASS_FIS:
                return (byte) 4;
            case AMTBMessageState.AMTB_MODE_ULTRA_BYPASS_RIAS:
                return (byte) 5;
            case AMTBMessageState.AMTB_MODE_ULTRA_BYPASS_FIS_RIAS:
                return (byte) 6;

            default: return (byte)-1;
        }
    }

    public static String getAmtbJettisonStateStr_e(int val){

        switch (val){
            case 0 : return AMTBMessageState.JETTISON_STATE_NOT_STARTED;
            case 1 : return AMTBMessageState.JETTISON_STATE_RUNNING;
            case 2 : return AMTBMessageState.JETTISON_STATE_FINISHED;
            default: return AMTBMessageState.GELEN_BILGI_HATALI;
        }
    }

    public static byte getAmtbJettisonStateInt_e(String JettisonState){

        switch (JettisonState.toUpperCase()){
            case AMTBMessageState.JETTISON_STATE_NOT_STARTED:
                return (byte) 0;
            case AMTBMessageState.JETTISON_STATE_RUNNING:
                return (byte) 1;
            case AMTBMessageState.JETTISON_STATE_FINISHED:
                return (byte) 2;

            default: return (byte)-1;
        }
    }

    public static byte getA(String JettisonState){

        switch (JettisonState.toUpperCase()){
            case AMTBMessageState.JETTISON_STATE_NOT_STARTED:
                return (byte) 0;
            case AMTBMessageState.JETTISON_STATE_RUNNING:
                return (byte) 1;
            case AMTBMessageState.JETTISON_STATE_FINISHED:
                return (byte) 2;

            default: return (byte)-1;
        }
    }

    public static String getAmtbBITResults_e(int val){

        switch (val){
            case 0 : return AMTBMessageState.AMTB_BIT_RES_NOT_TESTED;
            case 1 : return AMTBMessageState.AMTB_BIT_RES_OK;
            case 2 : return AMTBMessageState.AMTB_BIT_RES_FAIL;
            default: return AMTBMessageState.GELEN_BILGI_HATALI;
        }
    }

    public static String getFireOperationState_e(int val){

        switch (val){
            case 0 : return AMTBMessageState.FIRE_OPERATION_STATE_WAITING;
            case 1 : return AMTBMessageState.FIRE_OPERATION_STATE_RUNNING;
            case 2 : return AMTBMessageState.FIRE_OPERATION_STATE_SUCCESS;
            case 3 : return AMTBMessageState.FIRE_OPERATION_STATE_SUCCESS_BY_MISFIRE;
            case 4 : return AMTBMessageState.FIRE_OPERATION_STATE_FAIL;
            case 5 : return AMTBMessageState.FIRE_OPERATION_STATE_NEARLY_SIMULTANEOUSLY;
            default : return AMTBMessageState.GELEN_BILGI_HATALI;
        }
    }

    public static String getAmtbBlankingDirection_e(int val){

        switch (val){
            case 0 : return AMTBMessageState.AMTB_BLANKING_DIRECTION_ALL;
            case 1 : return AMTBMessageState.AMTB_BLANKING_DIRECTION_LEFT;
            case 2 : return AMTBMessageState.AMTB_BLANKING_DIRECTION_RIGTH;
            default: return AMTBMessageState.GELEN_BILGI_HATALI;
        }
    }


    public static String getAmtbBlankingOperation_e(int val){

        switch (val){
            case 0 : return AMTBMessageState.AMTB_BLANKING_OPERATION_PASSIVE;
            case 1 : return AMTBMessageState.AMTB_BLANKING_OPERATION_ACTIVE;
            default: return AMTBMessageState.GELEN_BILGI_HATALI;
        }
    }

    public static String getAmtbStoreCategory_e(int val){

        switch (val){
            case 0 : return AMTBMessageState.AMTB_STORE_CATEGORY_CHAFF;
            case 1 : return AMTBMessageState.AMTB_STORE_CATEGORY_FLARE;
            case 2 : return AMTBMessageState.AMTB_STORE_CATEGORY_CAT1;
            case 3 : return AMTBMessageState.AMTB_STORE_CATEGORY_CAT2;
            default : return AMTBMessageState.GELEN_BILGI_HATALI;
        }
    }

    public static String getAmtbMisfireReason_e(int val){

        switch (val){
            case 0 : return AMTBMessageState.AMTB_MISFIRE_REASON_UNKNOWN;
            case 1 : return AMTBMessageState.AMTB_MISFIRE_REASON_EARLY;
            case 2 : return AMTBMessageState.AMTB_MISFIRE_REASON_LATE;
            case 3 : return AMTBMessageState.AMTB_MISFIRE_REASON_INV_SCAN;
            default : return AMTBMessageState.GELEN_BILGI_HATALI;
        }
    }

    public static String getTekCift_e(int val){

        switch (val){
            case 0 : return AMTBMessageState.CIFT;
            case 1 : return AMTBMessageState.TEK;
            case 2 : return AMTBMessageState.CIFT_UST;
            default : return AMTBMessageState.GELEN_BILGI_HATALI;
        }
    }

}
