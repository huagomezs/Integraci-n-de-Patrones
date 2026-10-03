package docengine.builder;

import docengine.flyweight.GlyphFactory;
import docengine.model.DocumentElement;
import docengine.model.DocumentStructure;
import docengine.model.Footer;
import docengine.model.Header;
import docengine.model.Paragraph;
import docengine.model.Table;


public abstract class BaseDocumentBuilder implements DocumentBuilder {

    private static final String DEFAULT_TITLE = "Untitled";
    private static final int LINE_HEIGHT = 14;
    private static final int ELEMENT_SPACING = 6;

    protected DocumentStructure structure;
    protected final GlyphFactory factory;
    protected int positionY;
    protected String textColor;
    protected double scale;

    public BaseDocumentBuilder(GlyphFactory factory) {
        this.factory = factory;
        reset();
    }

    @Override
    public void reset() {
        structure = new DocumentStructure(DEFAULT_TITLE);
        positionY = 0;
        textColor = "#000000";
        scale = 1.0;
    }

    @Override
    public DocumentBuilder addHeader(String text, int level) {
        Header header = new Header(text, level, factory);
        double previousScale = scale;
        scale = previousScale * (2.0 - 0.25 * header.getLevel());
        placeGlyphs(header);
        scale = previousScale;
        if (header.getLevel() == 1 && DEFAULT_TITLE.equals(structure.getTitle())) {
            structure.setTitle(text);
        }
        structure.add(header);
        return this;
    }

    @Override
    public DocumentBuilder addParagraph(String text) {
        Paragraph paragraph = new Paragraph(text, factory);
        placeGlyphs(paragraph);
        structure.add(paragraph);
        return this;
    }

    @Override
    public DocumentBuilder addTable(String[] headers, String[][] rows) {
        Table table = new Table(headers, rows, factory);
        placeGlyphs(table);
        structure.add(table);
        return this;
    }

    @Override
    public DocumentBuilder addFooter(String text) {
        Footer footer = new Footer(text, factory);
        placeGlyphs(footer);
        structure.add(footer);
        return this;
    }


    @Override
    public DocumentStructure build() {
        DocumentStructure result = structure;
        reset();
        return result;
    }


    protected void placeGlyphs(DocumentElement element) {
        element.applyStyle(positionY, textColor, scale);
        int lines = element.getContent().split("\n", -1).length;
        positionY += (int) Math.round(lines * LINE_HEIGHT * scale) + ELEMENT_SPACING;
    }
}
