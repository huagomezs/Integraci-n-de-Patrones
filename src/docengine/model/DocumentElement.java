package docengine.model;

import docengine.flyweight.GlyphFactory;
import docengine.flyweight.PlacedGlyph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public abstract class DocumentElement {

    private static final int CHAR_WIDTH = 8;
    private static final int LINE_HEIGHT = 14;

    protected final ElementType type;
    protected String content;
    protected int positionY = 0;
    protected String color = "#000000";
    protected double scale = 1.0;

    private final GlyphFactory factory;
    private final List<PlacedGlyph> glyphs = new ArrayList<>();

    public DocumentElement(ElementType type, String content, GlyphFactory factory) {
        this.type = Objects.requireNonNull(type, "type");
        this.content = Objects.requireNonNull(content, "content");
        this.factory = Objects.requireNonNull(factory, "factory");
        regenerateGlyphs();
    }

    public ElementType getType() {
        return type;
    }

    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = Objects.requireNonNull(content, "content");
        regenerateGlyphs();
    }

    public List<PlacedGlyph> getGlyphs() {
        return Collections.unmodifiableList(glyphs);
    }


    public void applyStyle(int positionY, String color, double scale) {
        this.positionY = positionY;
        this.color = color;
        this.scale = scale;
        regenerateGlyphs();
    }

  
    protected void regenerateGlyphs() {
        glyphs.clear();
        int advance = Math.max(1, (int) Math.round(CHAR_WIDTH * scale));
        int lineHeight = (int) Math.round(LINE_HEIGHT * scale);
        int x = 0;
        int y = positionY;
        int i = 0;
        while (i < content.length()) {
            char c = content.charAt(i);
            if (c == '[' && content.startsWith("[icon:", i)) {
                int end = content.indexOf(']', i);
                if (end > 0) {
                    String name = content.substring(i + 6, end);
                    glyphs.add(factory.place(factory.getIcon(name, "icons/" + name + ".png"), x, y, color, scale));
                    x += advance * 2;
                    i = end + 1;
                    continue;
                }
            }
            if (c == '\n') {
                x = 0;
                y += lineHeight;
            } else if (c == '\t') {
                x += advance * 4;
            } else {
                glyphs.add(factory.place(factory.getCharacter(c), x, y, color, scale));
                x += advance;
            }
            i++;
        }
    }
}
