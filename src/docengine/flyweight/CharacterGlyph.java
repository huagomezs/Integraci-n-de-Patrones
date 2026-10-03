package docengine.flyweight;

import java.util.Locale;


public class CharacterGlyph implements GlyphFlyweight {

    private final char character;
    private final String baseFont;

    public CharacterGlyph(char character, String baseFont) {
        this.character = character;
        this.baseFont = baseFont;
    }

    @Override
    public String draw(int x, int y, String color, double scale) {
        return String.format(Locale.ROOT, "glyph '%c' font=%s at (%d,%d) color=%s scale=%.2f",
                character, baseFont, x, y, color, scale);
    }

    @Override
    public String getKey() {
        return "C:" + baseFont + ":" + character;
    }
}
