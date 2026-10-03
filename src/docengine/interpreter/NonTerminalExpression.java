package docengine.interpreter;


public abstract class NonTerminalExpression implements Expression {

    private final Expression left;
    private final Expression right;

    public NonTerminalExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Object interpret(ExpressionContext context) {
        Object a = left.interpret(context);
        Object b = right.interpret(context);
        if (!(a instanceof Number) || !(b instanceof Number)) {
            throw new IllegalArgumentException("Arithmetic needs numeric operands but got: " + a + " and " + b);
        }
        return operate(((Number) a).doubleValue(), ((Number) b).doubleValue());
    }

    protected abstract double operate(double a, double b);
}
