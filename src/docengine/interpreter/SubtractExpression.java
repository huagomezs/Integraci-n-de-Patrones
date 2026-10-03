package docengine.interpreter;


public class SubtractExpression extends NonTerminalExpression {

    public SubtractExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected double operate(double a, double b) {
        return a - b;
    }
}
