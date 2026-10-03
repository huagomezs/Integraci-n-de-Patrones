package docengine.model;

import docengine.flyweight.GlyphFactory;


public class Table extends DocumentElement {

    private String[] headers;
    private String[][] rows;

    public Table(String[] headers, String[][] rows, GlyphFactory factory) {
        super(ElementType.TABLE, serialize(headers, rows), factory);
        this.headers = headers.clone();
        this.rows = copy(rows);
    }

    @Override
    public void setContent(String content) {
        super.setContent(content);
        parse(content);
    }

    public String[] getHeaders() {
        return headers.clone();
    }

    public String[][] getRows() {
        return copy(rows);
    }

    private void parse(String text) {
        String[] lines = text.split("\n", -1);
        this.headers = lines[0].split("\t", -1);
        this.rows = new String[lines.length - 1][];
        for (int i = 1; i < lines.length; i++) {
            rows[i - 1] = lines[i].split("\t", -1);
        }
    }

    private static String serialize(String[] headers, String[][] rows) {
        StringBuilder sb = new StringBuilder(String.join("\t", headers));
        for (String[] row : rows) {
            sb.append('\n').append(String.join("\t", row));
        }
        return sb.toString();
    }

    private static String[][] copy(String[][] source) {
        String[][] result = new String[source.length][];
        for (int i = 0; i < source.length; i++) {
            result[i] = source[i].clone();
        }
        return result;
    }
}
