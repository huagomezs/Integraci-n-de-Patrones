package docengine.model;

import docengine.flyweight.GlyphFactory;


public class Footer extends DocumentElement {

    public Footer(String text, GlyphFactory factory) {
        super(ElementType.FOOTER, text, factory);
    }
}
