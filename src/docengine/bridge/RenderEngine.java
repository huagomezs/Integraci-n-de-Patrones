package docengine.bridge;

import docengine.model.Footer;
import docengine.model.Header;
import docengine.model.OutputFormat;
import docengine.model.Paragraph;
import docengine.model.Table;


public interface RenderEngine {

    void startDocument(String title);

    void renderHeader(Header header);

    void renderParagraph(Paragraph paragraph);

    void renderTable(Table table);

    void renderFooter(Footer footer);

    void pageBreak(int number);

    String finishDocument();

    OutputFormat getFormat();
}
