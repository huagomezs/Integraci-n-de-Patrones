package docengine.builder;

import docengine.model.DocumentStructure;
import docengine.model.TemplateType;


public class DocumentDirector {

    private DocumentBuilder builder;

    public DocumentDirector(DocumentBuilder builder) {
        this.builder = builder;
    }

    public void setBuilder(DocumentBuilder builder) {
        this.builder = builder;
    }

    public DocumentStructure construct(TemplateType template) {
        switch (template) {
            case EXECUTIVE_REPORT:
                return constructExecutiveReport();
            case SIMPLE_INVOICE:
                return constructSimpleInvoice();
            default:
                throw new IllegalArgumentException("Unsupported template: " + template);
        }
    }

    private DocumentStructure constructExecutiveReport() {
        builder.reset();
        builder.addHeader("Quarterly Executive Report", 1);
        builder.addParagraph("Prepared on #{CURRENT_DATE}. [icon:check] Status: on track. "
                + "This confidential draft is for internal use only.");
        if (builder instanceof ExecutiveReportBuilder) {
            ((ExecutiveReportBuilder) builder).addExecutiveSummary(
                    "Revenue grew steadily while costs stayed flat. [icon:chart] See the table below.");
        }
        builder.addTable(
                new String[] {"Region", "Units", "Revenue"},
                new String[][] {{"North", "120", "12000"}, {"South", "95", "9500"}, {"East", "143", "14300"}});
        builder.addParagraph("Projected price with tax and discount: #{BASE_PRICE * 1.19 - DISCOUNT}");
        builder.addFooter("Intelligent Document Engine [icon:logo]");
        return builder.build();
    }

    private DocumentStructure constructSimpleInvoice() {
        builder.reset();
        builder.addHeader("Invoice", 1);
        builder.addParagraph("Issued on #{CURRENT_DATE} to ACME Corp. [icon:check] Thank you for your business.");
        builder.addTable(
                new String[] {"Item", "Qty", "Price"},
                new String[][] {{"Widget", "10", "100"}, {"Gadget", "5", "200"}});
        if (builder instanceof SimpleInvoiceBuilder) {
            ((SimpleInvoiceBuilder) builder).addTotals("BASE_PRICE * 1.19 - DISCOUNT");
        }
        builder.addFooter("Payment due in 30 days.");
        return builder.build();
    }
}
