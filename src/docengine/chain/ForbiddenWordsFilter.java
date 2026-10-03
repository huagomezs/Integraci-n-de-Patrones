package docengine.chain;

import docengine.model.DocumentElement;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ForbiddenWordsFilter extends ProcessorHandler {

    private final Set<String> forbiddenWords = new LinkedHashSet<>();
    private final String mask;

    public ForbiddenWordsFilter(String mask) {
        this.mask = mask;
    }

    public void addWord(String word) {
        forbiddenWords.add(word.toLowerCase(Locale.ROOT));
    }

    @Override
    protected void handle(ProcessingContext context) {
        for (DocumentElement element : context.getStructure().getElements()) {
            String original = element.getContent();
            String clean = censor(original);
            if (!clean.equals(original)) {
                element.setContent(clean);
                context.addWarning(getName(), "Sensitive words hidden in " + element.getType());
            }
        }
    }

    @Override
    public String getName() {
        return "ForbiddenWordsFilter";
    }

    private String censor(String text) {
        String result = text;
        for (String word : forbiddenWords) {
            Pattern pattern = Pattern.compile("\\b" + Pattern.quote(word) + "\\b", Pattern.CASE_INSENSITIVE);
            result = pattern.matcher(result).replaceAll(Matcher.quoteReplacement(mask));
        }
        return result;
    }
}
