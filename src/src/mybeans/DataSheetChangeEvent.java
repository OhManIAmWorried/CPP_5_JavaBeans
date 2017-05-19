package src.mybeans;

import java.util.EventObject;

/**
 * Created by oleg on 05.05.2017.
 */
public class DataSheetChangeEvent extends EventObject {

    private static final long serialVersionUID = 1L;

    public DataSheetChangeEvent(Object source) {
        super(source);
    }
}
