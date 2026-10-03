package docengine.interpreter;


public class MultiplyExpression extends NonTerminalExpression {

    public MultiplyExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected double operate(double a, double b) {
        return a * b;
    }
}
