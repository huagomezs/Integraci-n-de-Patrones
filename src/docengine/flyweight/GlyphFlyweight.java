package docengine.flyweight;


public interface GlyphFlyweight {

    String draw(int x, int y, String color, double scale);

    String getKey();
}
