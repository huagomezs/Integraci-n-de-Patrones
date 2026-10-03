package docengine.builder;

import docengine.flyweight.GlyphFactory;
import docengine.model.DocumentStructure;


public class ExecutiveReportBuilder extends BaseDocumentBuilder {

    private final String titleColor = "#1F3A93";

    public ExecutiveReportBuilder(GlyphFactory factory) {
        super(factory);
    }


    public ExecutiveReportBuilder addExecutiveSummary(String text) {
        String previousColor = textColor;
        textColor = titleColor;
        addHeader("Executive Summary", 2);
        textColor = previousColor;
        addParagraph(text);
        return this;
    }

    @Override
    public DocumentStructure build() {
        structure.putMetadata("template", "EXECUTIVE_REPORT");
        return super.build();
    }
}
