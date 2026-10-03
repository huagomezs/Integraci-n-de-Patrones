package docengine.app;

import docengine.chain.ExpressionEvaluator;
import docengine.chain.ForbiddenWordsFilter;
import docengine.chain.ProcessorHandler;
import docengine.chain.SyntaxValidator;
import docengine.flyweight.GlyphFactory;
import docengine.interpreter.Expression;
import docengine.interpreter.ExpressionContext;
import docengine.interpreter.ExpressionParser;
import docengine.mediator.BuilderToolbar;
import docengine.mediator.DocumentEditorMediator;
import docengine.mediator.ExportButton;
import docengine.mediator.FormatSelector;
import docengine.mediator.Preview;
import docengine.model.OutputFormat;
import docengine.model.TemplateType;

public class Main {

    public static void main(String[] args) {
        GlyphFactory factory = new GlyphFactory("Arial");

        ExpressionContext variables = new ExpressionContext();
        variables.loadDefaultVariables();
        variables.define("BASE_PRICE", 1000.0);
        variables.define("DISCOUNT", 50.0);

        ProcessorHandler chain = setupChain();

        DocumentEditorMediator mediator = new DocumentEditorMediator(factory, chain, variables);
        FormatSelector formatSelector = new FormatSelector();
        BuilderToolbar toolbar = new BuilderToolbar();
        Preview preview = new Preview();
        ExportButton exportButton = new ExportButton();
        mediator.register(formatSelector, toolbar, preview, exportButton);

        System.out.println("\n1. Configuration (Mediator)");
        toolbar.selectTemplate(TemplateType.EXECUTIVE_REPORT);

        System.out.println("\n2. Interpreter");
        ExpressionParser parser = new ExpressionParser();
        Expression formula = parser.parse("BASE_PRICE * 1.19 - DISCOUNT");
        System.out.println("BASE_PRICE * 1.19 - DISCOUNT = " + formula.interpret(variables));


        System.out.println("\n3. PDF");
        formatSelector.select(OutputFormat.PDF);
        exportButton.press();
        System.out.println(mediator.getLastExport());

        System.out.println("4. HTML");
        formatSelector.select(OutputFormat.HTML);
        exportButton.press();
        System.out.println(mediator.getLastExport());

        System.out.println("5. Markdown");
        formatSelector.select(OutputFormat.MARKDOWN);
        exportButton.press();
        System.out.println(mediator.getLastExport());


        System.out.println("Flyweight");
        System.out.println("Glyph requests: " + factory.getTotalRequests());
        System.out.println("Shared instances: " + factory.getTotalInstances());
    }

    private static ProcessorHandler setupChain() {
        ProcessorHandler validator = new SyntaxValidator();
        ForbiddenWordsFilter filter = new ForbiddenWordsFilter("[REDACTED]");
        filter.addWord("confidential");
        ExpressionEvaluator evaluator = new ExpressionEvaluator();
        validator.setNext(filter).setNext(evaluator);
        return validator;
    }
}
