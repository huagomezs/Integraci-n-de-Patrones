package docengine.interpreter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


public class ExpressionContext {

    private final Map<String, Object> variables = new HashMap<>();

    public void define(String name, Object value) {
        variables.put(name, value);
    }

    public Object get(String name) {
        if (!variables.containsKey(name)) {
            throw new IllegalArgumentException("Undefined variable: " + name);
        }
        return variables.get(name);
    }

    public boolean exists(String name) {
        return variables.containsKey(name);
    }

    /** Defines the built-in variables (CURRENT_DATE). */
    public void loadDefaultVariables() {
        define("CURRENT_DATE", LocalDate.now());
    }
}
