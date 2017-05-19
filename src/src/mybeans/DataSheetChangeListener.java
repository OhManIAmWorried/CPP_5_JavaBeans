package src.mybeans;

import java.util.EventListener;

/**
 * Created by oleg on 05.05.2017.
 */
public interface DataSheetChangeListener extends EventListener {
    public void dataChanged(DataSheetChangeEvent e);
}
