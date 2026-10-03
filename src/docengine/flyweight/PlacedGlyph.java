package docengine.flyweight;


public class PlacedGlyph {

    private final GlyphFlyweight flyweight;
    private final int x;
    private final int y;
    private final String color;
    private final double scale;

    public PlacedGlyph(GlyphFlyweight flyweight, int x, int y, String color, double scale) {
        this.flyweight = flyweight;
        this.x = x;
        this.y = y;
        this.color = color;
        this.scale = scale;
    }

    public String render() {
        return flyweight.draw(x, y, color, scale);
    }

    public GlyphFlyweight getFlyweight() {
        return flyweight;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getColor() {
        return color;
    }

    public double getScale() {
        return scale;
    }
}
