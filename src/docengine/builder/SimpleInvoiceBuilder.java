package docengine.builder;

import docengine.flyweight.GlyphFactory;
import docengine.model.DocumentStructure;


public class SimpleInvoiceBuilder extends BaseDocumentBuilder {

    private final String invoiceNumber = "INV-0001";

    public SimpleInvoiceBuilder(GlyphFactory factory) {
        super(factory);
    }


    public SimpleInvoiceBuilder addTotals(String formula) {
        addParagraph("Total: #{" + formula + "}");
        return this;
    }

    @Override
    public DocumentStructure build() {
        structure.putMetadata("template", "SIMPLE_INVOICE");
        structure.putMetadata("invoiceNumber", invoiceNumber);
        return super.build();
    }
}
