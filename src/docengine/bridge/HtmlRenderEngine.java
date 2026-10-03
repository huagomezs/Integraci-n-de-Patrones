package docengine.bridge;

import docengine.flyweight.PlacedGlyph;
import docengine.model.DocumentElement;
import docengine.model.Footer;
import docengine.model.Header;
import docengine.model.OutputFormat;
import docengine.model.Paragraph;
import docengine.model.Table;

import java.util.List;
import java.util.regex.Pattern;


public class HtmlRenderEngine implements RenderEngine {

    private static final Pattern ICON = Pattern.compile("\\[icon:(\\w+)\\]");

    private StringBuilder buffer = new StringBuilder();
    private boolean pageOpen;

    @Override
    public void startDocument(String title) {
        buffer = new StringBuilder();
        pageOpen = false;
        buffer.append("<!DOCTYPE html>\n<html>\n<head><meta charset=\"UTF-8\"><title>")
                .append(escape(title)).append("</title></head>\n<body>\n");
    }

    @Override
    public void renderHeader(Header header) {
        buffer.append(String.format("<h%d style=\"color:%s\">%s</h%d>%n",
                header.getLevel(), colorOf(header), inline(header.getContent()), header.getLevel()));
    }

    @Override
    public void renderParagraph(Paragraph paragraph) {
        buffer.append(String.format("<p style=\"color:%s\">%s</p>%n", colorOf(paragraph), inline(paragraph.getContent())));
    }

    @Override
    public void renderTable(Table table) {
        buffer.append("<table border=\"1\">\n  <tr>");
        for (String header : table.getHeaders()) {
            buffer.append("<th>").append(inline(header)).append("</th>");
        }
        buffer.append("</tr>\n");
        for (String[] row : table.getRows()) {
            buffer.append("  <tr>");
            for (String cell : row) {
                buffer.append("<td>").append(inline(cell)).append("</td>");
            }
            buffer.append("</tr>\n");
        }
        buffer.append("</table>\n");
    }

    @Override
    public void renderFooter(Footer footer) {
        buffer.append(String.format("<footer style=\"color:%s\">%s</footer>%n", colorOf(footer), inline(footer.getContent())));
    }

    @Override
    public void pageBreak(int number) {
        if (pageOpen) {
            buffer.append("</section>\n");
        }
        buffer.append("<section class=\"page\" data-page=\"").append(number).append("\">\n");
        pageOpen = true;
    }

    @Override
    public String finishDocument() {
        if (pageOpen) {
            buffer.append("</section>\n");
            pageOpen = false;
        }
        buffer.append("</body>\n</html>\n");
        return buffer.toString();
    }

    @Override
    public OutputFormat getFormat() {
        return OutputFormat.HTML;
    }

    private String colorOf(DocumentElement element) {
        List<PlacedGlyph> glyphs = element.getGlyphs();
        return glyphs.isEmpty() ? "#000000" : glyphs.get(0).getColor();
    }

    private String inline(String text) {
        return ICON.matcher(escape(text)).replaceAll("<img src=\"icons/$1.png\" alt=\"$1\"/>");
    }

    private String escape(String text) {
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
