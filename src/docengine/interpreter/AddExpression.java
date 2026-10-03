package docengine.interpreter;


public class AddExpression extends NonTerminalExpression {

    public AddExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected double operate(double a, double b) {
        return a + b;
    }
}
