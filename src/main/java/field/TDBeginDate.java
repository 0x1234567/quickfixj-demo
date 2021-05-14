package field;

import quickfix.IntField;

public class TDBeginDate extends IntField {
    static final long serialVersionUID = 20201117L;
    public static final int FIELD = 9051;

    public TDBeginDate() {
        super(9051);
    }

    public TDBeginDate(Integer data) {
        super(9051, data);
    }
//    public TDBeginDate(int field){
//        super(field);
//    }
}
