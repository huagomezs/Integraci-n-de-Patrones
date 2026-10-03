package docengine.interpreter;


public class VariableExpression extends TerminalExpression {

    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public Object interpret(ExpressionContext context) {
        return context.get(name);
    }
}
