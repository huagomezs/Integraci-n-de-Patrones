package docengine.interpreter;


public class NumberExpression extends TerminalExpression {

    private final double value;

    public NumberExpression(double value) {
        this.value = value;
    }

    @Override
    public Object interpret(ExpressionContext context) {
        return value;
    }
}
