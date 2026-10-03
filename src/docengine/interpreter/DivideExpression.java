package docengine.interpreter;


public class DivideExpression extends NonTerminalExpression {

    public DivideExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected double operate(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return a / b;
    }
}
