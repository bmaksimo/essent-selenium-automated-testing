package stepdefinitions.dwp.tables.plus;

import org.apache.commons.lang3.BooleanUtils;

public enum SwitchState {
    Open(1),
    Closed(0),
    On(1),
    Off(0),
    Checked(1),
    Unchecked(0),
    Undefined(-1);

    private int state = 0;

    SwitchState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public boolean isOn() {
        return state > 0;
    }
    public boolean hasState(String state) {
        boolean fromString = BooleanUtils.toBoolean(state);
        return BooleanUtils.toInteger(fromString) == this.state;
    }
    public boolean isUndefined() {
        return state < 0;
    }

}
