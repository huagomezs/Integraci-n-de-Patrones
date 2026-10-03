package docengine.bridge;

import docengine.model.Footer;
import docengine.model.Header;
import docengine.model.OutputFormat;
import docengine.model.Paragraph;
import docengine.model.Table;

import java.util.regex.Pattern;


public class MarkdownRenderEngine implements RenderEngine {

    private static final Pattern ICON = Pattern.compile("\\[icon:(\\w+)\\]");

    private StringBuilder buffer = new StringBuilder();

    @Override
    public void startDocument(String title) {
        buffer = new StringBuilder();
        buffer.append("<!-- ").append(title).append(" -->\n\n");
    }

    @Override
    public void renderHeader(Header header) {
        buffer.append("#".repeat(header.getLevel())).append(' ').append(inline(header.getContent())).append("\n\n");
    }

    @Override
    public void renderParagraph(Paragraph paragraph) {
        buffer.append(inline(paragraph.getContent())).append("\n\n");
    }

    @Override
    public void renderTable(Table table) {
        appendRow(table.getHeaders());
        buffer.append('|');
        for (int i = 0; i < table.getHeaders().length; i++) {
            buffer.append(" --- |");
        }
        buffer.append('\n');
        for (String[] row : table.getRows()) {
            appendRow(row);
        }
        buffer.append('\n');
    }

    @Override
    public void renderFooter(Footer footer) {
        buffer.append("---\n*").append(inline(footer.getContent())).append("*\n");
    }

    @Override
    public void pageBreak(int number) {
        if (number > 1) {
            buffer.append("---\n\n");
        }
        buffer.append("<!-- page ").append(number).append(" -->\n");
    }

    @Override
    public String finishDocument() {
        return buffer.toString();
    }

    @Override
    public OutputFormat getFormat() {
        return OutputFormat.MARKDOWN;
    }

    private void appendRow(String[] cells) {
        buffer.append('|');
        for (String cell : cells) {
            buffer.append(' ').append(inline(cell).replace("|", "\\|")).append(" |");
        }
        buffer.append('\n');
    }

    private String inline(String text) {
        return ICON.matcher(text).replaceAll("![$1](icons/$1.png)");
    }
}
