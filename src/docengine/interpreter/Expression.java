package docengine.interpreter;


public interface Expression {

    Object interpret(ExpressionContext context);
}
