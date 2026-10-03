package docengine.bridge;

import docengine.model.Footer;
import docengine.model.Header;
import docengine.model.OutputFormat;
import docengine.model.Paragraph;
import docengine.model.Table;


public class PdfRenderEngine implements RenderEngine {

    private StringBuilder buffer = new StringBuilder();

    @Override
    public void startDocument(String title) {
        buffer = new StringBuilder();
        buffer.append("PDF DOCUMENT: ").append(title).append("\n");
    }

    @Override
    public void renderHeader(Header header) {
        buffer.append("[H").append(header.getLevel()).append("] ").append(header.getContent()).append("\n");
    }

    @Override
    public void renderParagraph(Paragraph paragraph) {
        buffer.append(paragraph.getContent()).append("\n");
    }

    @Override
    public void renderTable(Table table) {
        buffer.append(String.join(" | ", table.getHeaders())).append("\n");
        for (String[] row : table.getRows()) {
            buffer.append(String.join(" | ", row)).append("\n");
        }
    }

    @Override
    public void renderFooter(Footer footer) {
        buffer.append("[Footer] ").append(footer.getContent()).append("\n");
    }

    @Override
    public void pageBreak(int number) {
        buffer.append("---------- Page ").append(number).append(" ----------\n");
    }

    @Override
    public String finishDocument() {
        buffer.append("---------- End of document ----------\n");
        return buffer.toString();
    }

    @Override
    public OutputFormat getFormat() {
        return OutputFormat.PDF;
    }
}
