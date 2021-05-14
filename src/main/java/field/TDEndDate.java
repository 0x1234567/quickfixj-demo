package field;

import quickfix.IntField;

public class TDEndDate extends IntField {
    static final long serialVersionUID = 20201117L;
    public static final int FIELD = 9052;

    public TDEndDate() {
        super(9052);
    }

    public TDEndDate(Integer data) {
        super(9052, data);
    }
//    public TDEndDate(int field){
//        super(field);
//    }
}
