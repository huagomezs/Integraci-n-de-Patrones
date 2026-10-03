package docengine.chain;

import docengine.interpreter.Expression;
import docengine.interpreter.ExpressionContext;
import docengine.interpreter.ExpressionParser;
import docengine.model.DocumentElement;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ExpressionEvaluator extends ProcessorHandler {

    private final String markerPattern = "#\\{([^}]*)\\}";
    private final ExpressionParser parser = new ExpressionParser();

    @Override
    protected void handle(ProcessingContext context) {
        for (DocumentElement element : context.getStructure().getElements()) {
            try {
                String original = element.getContent();
                String evaluated = evaluateMarkers(original, context.getVariables());
                if (!evaluated.equals(original)) {
                    element.setContent(evaluated);
                }
            } catch (RuntimeException e) {
                context.addError(getName(), "Cannot evaluate formula in " + element.getType() + ": " + e.getMessage());
                context.stop();
                return;
            }
        }
    }

    @Override
    public String getName() {
        return "ExpressionEvaluator";
    }

    private String evaluateMarkers(String text, ExpressionContext variables) {
        Matcher matcher = Pattern.compile(markerPattern).matcher(text);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            Expression expression = parser.parse(matcher.group(1));
            Object value = expression.interpret(variables);
            String formatted = (value instanceof Number)
                    ? String.format(Locale.ROOT, "%.2f", ((Number) value).doubleValue())
                    : String.valueOf(value);
            matcher.appendReplacement(result, Matcher.quoteReplacement(formatted));
        }
        matcher.appendTail(result);
        return result.toString();
    }
}
