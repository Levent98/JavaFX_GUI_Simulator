package com.example.deneme.ControllerClasses;

public class BlankingMessage {

    public enum category {
        AMTB_BLANKING_TYPE_CHAF(0),
        AMTB_BLANKING_TYPE_FLAR(1),
        AMTB_BLANKING_TYPE_ASH(2),
        AMTB_BLANKING_TYPE_CAT4(3);

        private final int value;

        category(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String category_) {
            for (category category : values()) {
                if (category.name().equalsIgnoreCase(category_)) {
                    return category.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + category_);
        }
    }

    public enum direction {
        AMTB_BLANKING_DIRECTION_ALL(0),
        AMTB_BLANKING_DIRECTION_LEFT(1),
        AMTB_BLANKING_DIRECTION_RIGTH(2);

        private final int value;

        direction(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String direction_) {
            for (direction direction : values()) {
                if (direction.name().equalsIgnoreCase(direction_)) {
                    return direction.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + direction_);
        }

    }

    public enum operation {
        AMTB_BLANKING_OPERATION_PASSIVE(0),
        AMTB_BLANKING_OPERATION_ACTIVE(1);

        private final int value;

        operation(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String operation_) {
            for (operation operation : values()) {
                if (operation.name().equalsIgnoreCase(operation_)) {
                    return operation.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + operation_);
        }

    }

    private int amtbNo;
    private BlankingMessage.category category;
    private BlankingMessage.direction direction;
    private BlankingMessage.operation operation;

    public BlankingMessage(int amtbNo) {
        this.amtbNo = amtbNo;
        this.category = category.AMTB_BLANKING_TYPE_CHAF;
        this.direction = direction.AMTB_BLANKING_DIRECTION_LEFT;
        this.operation = operation.AMTB_BLANKING_OPERATION_PASSIVE;

    }


    public int getAmtbNo() { return amtbNo; }
    public void setAmtbNo(int amtbNo) { this.amtbNo = amtbNo; }

    public BlankingMessage.category getcategory() { return category; }
    public void setcategory(BlankingMessage.category category) { this.category = category; }

    public BlankingMessage.direction getdirection() { return direction; }
    public void setdirection(BlankingMessage.direction direction) { this.direction = direction; }

    public BlankingMessage.operation getoperation() { return operation; }
    public void setoperation(BlankingMessage.operation operation) { this.operation = operation; }


}

