package docengine.builder;

import docengine.model.DocumentStructure;


public interface DocumentBuilder {

    void reset();

    DocumentBuilder addHeader(String text, int level);

    DocumentBuilder addParagraph(String text);

    DocumentBuilder addTable(String[] headers, String[][] rows);

    DocumentBuilder addFooter(String text);

    DocumentStructure build();
}
