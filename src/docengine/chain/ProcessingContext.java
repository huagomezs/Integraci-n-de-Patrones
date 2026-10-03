package docengine.chain;

import docengine.interpreter.ExpressionContext;
import docengine.model.DocumentStructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ProcessingContext {

    private final DocumentStructure structure;
    private final ExpressionContext variables;
    private final List<String> errors = new ArrayList<>();
    private final List<String> warnings = new ArrayList<>();
    private boolean chainStopped;

    public ProcessingContext(DocumentStructure structure, ExpressionContext variables) {
        this.structure = structure;
        this.variables = variables;
    }

    public DocumentStructure getStructure() {
        return structure;
    }

    public ExpressionContext getVariables() {
        return variables;
    }

    public void addError(String handler, String message) {
        errors.add("[" + handler + "] " + message);
    }

    public void addWarning(String handler, String message) {
        warnings.add("[" + handler + "] " + message);
    }


    public void stop() {
        chainStopped = true;
    }

    public boolean isStopped() {
        return chainStopped;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public List<String> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    public List<String> getWarnings() {
        return Collections.unmodifiableList(warnings);
    }
}
