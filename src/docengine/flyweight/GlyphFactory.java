package docengine.flyweight;

import java.util.HashMap;
import java.util.Map;


public class GlyphFactory {

    private final String baseFont;
    private final Map<String, GlyphFlyweight> pool = new HashMap<>();
    private int requests;

    public GlyphFactory(String baseFont) {
        this.baseFont = baseFont;
    }

    public GlyphFlyweight getCharacter(char c) {
        requests++;
        String key = "C:" + baseFont + ":" + c; 
        GlyphFlyweight glyph = pool.get(key);
        if (glyph == null) {
            glyph = new CharacterGlyph(c, baseFont);
            pool.put(key, glyph);
        }
        return glyph;
    }

    public GlyphFlyweight getIcon(String name, String image) {
        requests++;
        String key = "I:" + name; 
        GlyphFlyweight glyph = pool.get(key);
        if (glyph == null) {
            glyph = new IconGlyph(name, image);
            pool.put(key, glyph);
        }
        return glyph;
    }

    public PlacedGlyph place(GlyphFlyweight flyweight, int x, int y, String color, double scale) {
        return new PlacedGlyph(flyweight, x, y, color, scale);
    }

    public int getTotalInstances() {
        return pool.size();
    }


    public int getTotalRequests() {
        return requests;
    }
}
