package com.example.deneme.ControllerClasses;

public class InventoryCellState {

    private int amtbNo;

    public enum category{
        CHAFF(0),
        FLARE(1),
        CAT1(2),
        CAT2(3);

        private final int value;

        category(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String category_) {
            for (InventoryCellState.category c : values()) {
                if (c.name().equalsIgnoreCase(category_)) {
                    return c.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + category_);
        }
    }

    public enum numberofPieces{
        TEK(1),
        CIFT(2);

        private final int value;

        numberofPieces(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String numberofPieces_) {
            for (InventoryCellState.numberofPieces n : values()) {
                if (n.name().equalsIgnoreCase(numberofPieces_)) {
                    return n.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + numberofPieces_);
        }
    }

    public enum dolulukDurumu{
        BOS(0),
        DOLU(1);

        private final int value;

        dolulukDurumu(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String dolulukDurumu_) {
            for (InventoryCellState.dolulukDurumu d : values()) {
                if (d.name().equalsIgnoreCase(dolulukDurumu_)) {
                    return d.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + dolulukDurumu_);
        }
    }

    public enum isMisfired{
        Hatali_Atim_Yok(0),
        Hatali_Atim_Gerceklesti(1);

        private final int value;

        isMisfired(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String isMisfired_) {
            for (InventoryCellState.isMisfired i : values()) {
                if (i.name().equalsIgnoreCase(isMisfired_)) {
                    return i.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + isMisfired_);
        }
    }

    public enum isMultiple{
        Coklu_Muhimmat_Degil(0),
        Coklu_Muhimmat(1);

        private final int value;

        isMultiple(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String isMultiple_) {
            for (InventoryCellState.isMultiple i : values()) {
                if (i.name().equalsIgnoreCase(isMultiple_)) {
                    return i.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + isMultiple_);
        }
    }

    public enum isSmart{
        Akilli_Muhimmat_Degil(0),
        Akilli_Muhimmat(1);

        private final int value;

        isSmart(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String isSmart_) {
            for (InventoryCellState.isSmart i : values()) {
                if (i.name().equalsIgnoreCase(isSmart_)) {
                    return i.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + isSmart_);
        }
    }

    public enum tipBilgisi{
        CHAFF(0),
        FLARE(1),
        CAT1(2),
        CAT2(3);

        private final int value;

        tipBilgisi(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String tipBilgisi_) {
            for (InventoryCellState.tipBilgisi t : values()) {
                if (t.name().equalsIgnoreCase(tipBilgisi_)) {
                    return t.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + tipBilgisi_);
        }
    }

    public enum TekCift{
        CIFT(0),
        TEK(1),
        CIFTUST(2);

        private final int value;

        TekCift(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String TekCift_) {
            for (InventoryCellState.TekCift t : values()) {
                if (t.name().equalsIgnoreCase(TekCift_)) {
                    return t.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + TekCift_);
        }
    }

    public enum kalanOmurKrtiklikDurumu{
        KRITIK_SEVIYEDE_DEGIL(0),
        KRITIK_SEVIYE(1);

        private final int value;

        kalanOmurKrtiklikDurumu(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String kalanOmurKrtiklikDurumu_) {
            for (InventoryCellState.kalanOmurKrtiklikDurumu k : values()) {
                if (k.name().equalsIgnoreCase(kalanOmurKrtiklikDurumu_)) {
                    return k.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + kalanOmurKrtiklikDurumu_);
        }
    }

    public enum uyumsuzlukDurumu{
        UYUMLU(0),
        UYUMSUZ(1);

        private final int value;

        uyumsuzlukDurumu(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int getIntFromString(String uyumsuzlukDurumu_) {
            for (InventoryCellState.uyumsuzlukDurumu u : values()) {
                if (u.name().equalsIgnoreCase(uyumsuzlukDurumu_)) {
                    return u.getValue();
                }
            }
            throw new IllegalArgumentException("Geçersiz mod: " + uyumsuzlukDurumu_);
        }
    }

  /*  public enum reserved{

        @Override
        public String toString(){return name();}

    }*/

    //public enum ashBildirilenTip{} [0-255]

    //public static int hucreNo;

    private category Category;
    private numberofPieces NumberofPieces;
    private dolulukDurumu DolulukDurumu;
    private isMisfired IsMisfired;
    private isMultiple IsMultiple;
    private isSmart IsSmart;
    private TekCift tekCift;
    private kalanOmurKrtiklikDurumu KalanOmurKrtiklikDurumu;
    private uyumsuzlukDurumu UyumsuzlukDurumu;
    private tipBilgisi tipBilgisix;
    // 25.06.2025
    public  InventoryCellState(){

    }

    public  InventoryCellState(int amtbNo){
        this.amtbNo = amtbNo;
        //this.hucreNo = hucreNo;

        // IA(23.06.2025) : default değerleri atayabilirsin.
        //this.Category = category.AMTB_STORE_CATEGORY_CHAFF;
        this.Category                = category.CHAFF;
        this.NumberofPieces          = numberofPieces.TEK;
        this.DolulukDurumu           = dolulukDurumu.DOLU;
        this.IsMisfired              = isMisfired.Hatali_Atim_Yok;
        this.IsMultiple              = isMultiple.Coklu_Muhimmat_Degil;
        this.IsSmart                 = isSmart.Akilli_Muhimmat_Degil;
        this.tekCift                 = TekCift.TEK;
        this.KalanOmurKrtiklikDurumu = kalanOmurKrtiklikDurumu.KRITIK_SEVIYEDE_DEGIL;
        this.UyumsuzlukDurumu        = uyumsuzlukDurumu.UYUMLU;
        this.tipBilgisix             = tipBilgisi.CHAFF;
    }

    public category getCategory() {
        return Category;
    }
    public void setCategory(category category) {
        Category = category;
    }

    public numberofPieces getNumberofPieces() {
        return NumberofPieces;
    }
    public void setNumberofPieces(numberofPieces numberofPieces) {
        NumberofPieces = numberofPieces;
    }

    public dolulukDurumu getDolulukDurumu() {
        return DolulukDurumu;
    }
    public void setDolulukDurumu(dolulukDurumu dolulukDurumu) {
        DolulukDurumu = dolulukDurumu;
    }

    public isMisfired getIsMisfired() {
        return IsMisfired;
    }
    public void setIsMisfired(isMisfired isMisfired) {
        IsMisfired = isMisfired;
    }

    public isMultiple getIsMultiple() {
        return IsMultiple;
    }
    public void setIsMultiple(isMultiple isMultiple) {
        IsMultiple = isMultiple;
    }

    public isSmart getIsSmart() {
        return IsSmart;
    }
    public void setIsSmart(isSmart isSmart) {
        IsSmart = isSmart;
    }

    public TekCift getTekCift() {
        return tekCift;
    }
    public void setTekCift(TekCift tekCift) {
        this.tekCift = tekCift;
    }

    public kalanOmurKrtiklikDurumu getKalanOmurKrtiklikDurumu() {
        return KalanOmurKrtiklikDurumu;
    }
    public void setKalanOmurKrtiklikDurumu(kalanOmurKrtiklikDurumu kalanOmurKrtiklikDurumu) {
        KalanOmurKrtiklikDurumu = kalanOmurKrtiklikDurumu;
    }

    public uyumsuzlukDurumu getUyumsuzlukDurumu() {
        return UyumsuzlukDurumu;
    }

    public void setUyumsuzlukDurumu(uyumsuzlukDurumu uyumsuzlukDurumu) {
        UyumsuzlukDurumu = uyumsuzlukDurumu;
    }

    public tipBilgisi getTipBilgisi() {
        return tipBilgisix;
    }

    public void setTipBilgisi(tipBilgisi tipBilgisi) {
        tipBilgisix = tipBilgisi;
    }
}
