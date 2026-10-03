package docengine.chain;

import docengine.model.DocumentElement;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SyntaxValidator extends ProcessorHandler {

    private static final Pattern TAG = Pattern.compile("<(/?)([A-Za-z][A-Za-z0-9]*)\\s*>");

    @Override
    protected void handle(ProcessingContext context) {
        boolean corrupted = false;
        for (DocumentElement element : context.getStructure().getElements()) {
            if (!hasBalancedTags(element.getContent())) {
                String preview = element.getContent().replace('\n', ' ');
                context.addError(getName(), "Corrupted tag or marker in " + element.getType() + ": \"" + preview + "\"");
                corrupted = true;
            }
        }
        if (corrupted) {
            context.stop();
        }
    }

    @Override
    public String getName() {
        return "SyntaxValidator";
    }

    private boolean hasBalancedTags(String text) {
        int depth = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.startsWith("#{", i)) {
                depth++;
                i++;
            } else if (text.charAt(i) == '}' && depth > 0) {
                depth--;
            }
        }
        if (depth != 0) {
            return false;
        }
        Deque<String> stack = new ArrayDeque<>();
        Matcher matcher = TAG.matcher(text);
        while (matcher.find()) {
            String name = matcher.group(2).toLowerCase(Locale.ROOT);
            if (matcher.group(1).isEmpty()) {
                stack.push(name);
            } else if (stack.isEmpty() || !stack.pop().equals(name)) {
                return false;
            }
        }
        return stack.isEmpty();
    }
}
