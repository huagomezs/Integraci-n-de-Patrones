package docengine.model;

import docengine.flyweight.GlyphFactory;


public class Paragraph extends DocumentElement {

    public Paragraph(String text, GlyphFactory factory) {
        super(ElementType.PARAGRAPH, text, factory);
    }
}
