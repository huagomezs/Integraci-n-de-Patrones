package docengine.interpreter;

import java.util.ArrayList;
import java.util.List;


public class ExpressionParser {

    private List<String> tokens = new ArrayList<>();
    private int position;

    public Expression parse(String formula) {
        tokens = tokenize(formula);
        position = 0;
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Empty formula");
        }
        Expression result = parseAddSub();
        if (position < tokens.size()) {
            throw new IllegalArgumentException(
                    "Unexpected token '" + tokens.get(position) + "' in formula: " + formula);
        }
        return result;
    }

    private List<String> tokenize(String formula) {
        List<String> result = new ArrayList<>();
        int i = 0;
        while (i < formula.length()) {
            char c = formula.charAt(i);
            if (Character.isWhitespace(c)) {
                i++;
            } else if (Character.isDigit(c) || c == '.') {
                int start = i;
                while (i < formula.length() && (Character.isDigit(formula.charAt(i)) || formula.charAt(i) == '.')) {
                    i++;
                }
                result.add(formula.substring(start, i));
            } else if (Character.isLetter(c) || c == '_') {
                int start = i;
                while (i < formula.length()
                        && (Character.isLetterOrDigit(formula.charAt(i)) || formula.charAt(i) == '_')) {
                    i++;
                }
                result.add(formula.substring(start, i));
            } else if ("+-*/()".indexOf(c) >= 0) {
                result.add(String.valueOf(c));
                i++;
            } else {
                throw new IllegalArgumentException("Unexpected character '" + c + "' in formula: " + formula);
            }
        }
        return result;
    }

    private Expression parseAddSub() {
        Expression left = parseMulDiv();
        while (position < tokens.size() && (tokens.get(position).equals("+") || tokens.get(position).equals("-"))) {
            String operator = tokens.get(position++);
            Expression right = parseMulDiv();
            left = operator.equals("+") ? new AddExpression(left, right) : new SubtractExpression(left, right);
        }
        return left;
    }

    private Expression parseMulDiv() {
        Expression left = parseFactor();
        while (position < tokens.size() && (tokens.get(position).equals("*") || tokens.get(position).equals("/"))) {
            String operator = tokens.get(position++);
            Expression right = parseFactor();
            left = operator.equals("*") ? new MultiplyExpression(left, right) : new DivideExpression(left, right);
        }
        return left;
    }

    private Expression parseFactor() {
        if (position >= tokens.size()) {
            throw new IllegalArgumentException("Unexpected end of formula");
        }
        String token = tokens.get(position++);
        if (token.equals("(")) {
            Expression inner = parseAddSub();
            if (position >= tokens.size() || !tokens.get(position).equals(")")) {
                throw new IllegalArgumentException("Missing closing parenthesis");
            }
            position++;
            return inner;
        }
        if (token.equals("-")) {
            return new SubtractExpression(new NumberExpression(0), parseFactor());
        }
        char first = token.charAt(0);
        if (Character.isDigit(first) || first == '.') {
            return new NumberExpression(Double.parseDouble(token));
        }
        if (Character.isLetter(first) || first == '_') {
            return new VariableExpression(token);
        }
        throw new IllegalArgumentException("Unexpected token: " + token);
    }
}
