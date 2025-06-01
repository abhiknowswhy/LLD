package pattern.Visitors;

import java.util.HashMap;
import java.util.Map;

public class MenuAnalysisState {
    private Map<String, Object> state;

    public MenuAnalysisState() {
        this.state = new HashMap<>();
    }

    public void setValue(String key, Object value) {
        state.put(key, value);
    }

    public Object getValue(String key) {
        return state.get(key);
    }

    public double getDoubleValue(String key) {
        Object value = state.get(key);
        return value instanceof Number ? ((Number) value).doubleValue() : 0.0;
    }

    public int getIntValue(String key) {
        Object value = state.get(key);
        return value instanceof Number ? ((Number) value).intValue() : 0;
    }

    public Map<String, Object> getState() {
        return new HashMap<>(state);
    }
}
