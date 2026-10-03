package docengine.model;

import docengine.flyweight.GlyphFactory;


public class Header extends DocumentElement {

    private final int level;

    public Header(String text, int level, GlyphFactory factory) {
        super(ElementType.HEADER, text, factory);
        this.level = Math.max(1, Math.min(6, level));
    }

    public int getLevel() {
        return level;
    }
}
