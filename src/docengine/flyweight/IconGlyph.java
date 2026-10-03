package docengine.flyweight;

import java.util.Locale;


public class IconGlyph implements GlyphFlyweight {

    private final String iconName;
    private final String iconImage;

    public IconGlyph(String iconName, String iconImage) {
        this.iconName = iconName;
        this.iconImage = iconImage;
    }

    @Override
    public String draw(int x, int y, String color, double scale) {
        return String.format(Locale.ROOT, "icon '%s' image=%s at (%d,%d) color=%s scale=%.2f",
                iconName, iconImage, x, y, color, scale);
    }

    @Override
    public String getKey() {
        return "I:" + iconName;
    }
}
