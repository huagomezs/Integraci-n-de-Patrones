package docengine.mediator;

import docengine.bridge.ContinuousDocument;
import docengine.bridge.Document;
import docengine.bridge.HtmlRenderEngine;
import docengine.bridge.MarkdownRenderEngine;
import docengine.bridge.PagedDocument;
import docengine.bridge.PdfRenderEngine;
import docengine.bridge.RenderEngine;
import docengine.builder.DocumentBuilder;
import docengine.builder.DocumentDirector;
import docengine.builder.ExecutiveReportBuilder;
import docengine.builder.SimpleInvoiceBuilder;
import docengine.chain.ProcessingContext;
import docengine.chain.ProcessorHandler;
import docengine.flyweight.GlyphFactory;
import docengine.interpreter.ExpressionContext;
import docengine.model.DocumentStructure;
import docengine.model.OutputFormat;
import docengine.model.TemplateType;


public class DocumentEditorMediator implements EditorMediator {

    private static final int ELEMENTS_PER_PAGE = 3;

    private FormatSelector formatSelector;
    private BuilderToolbar toolbar;
    private Preview preview;
    private ExportButton exportButton;

    private DocumentBuilder builder;
    private DocumentDirector director;
    private RenderEngine renderer;
    private final ProcessorHandler chain;
    private final GlyphFactory factory;
    private final ExpressionContext variables;
    private Document currentDocument;

    private OutputFormat currentFormat = OutputFormat.PDF;
    private TemplateType currentTemplate = TemplateType.EXECUTIVE_REPORT;
    private String lastExport;

    public DocumentEditorMediator(GlyphFactory factory, ProcessorHandler chain, ExpressionContext variables) {
        this.factory = factory;
        this.chain = chain;
        this.variables = variables;
        reconfigureBuilder(currentTemplate);
        reconfigureRenderer(currentFormat);
    }

    public void register(FormatSelector selector, BuilderToolbar toolbar, Preview preview, ExportButton button) {
        this.formatSelector = selector;
        this.toolbar = toolbar;
        this.preview = preview;
        this.exportButton = button;
        selector.setMediator(this);
        toolbar.setMediator(this);
        preview.setMediator(this);
        button.setMediator(this);
        exportButton.setEnabled(true);
    }

    @Override
    public void notify(UIComponent sender, UIEvent event) {
        System.out.println("[Mediator] " + sender.getName() + " -> " + event);
        switch (event) {
            case FORMAT_CHANGED:
                reconfigureRenderer(formatSelector.getSelectedFormat());
                updatePreview();
                break;
            case TEMPLATE_CHANGED:
                reconfigureBuilder(toolbar.getSelectedTemplate());
                updatePreview();
                break;
            case EXPORT_REQUESTED:
                runExport();
                break;
            default:
                break;
        }
    }

    public String getLastExport() {
        return lastExport;
    }

    private void reconfigureBuilder(TemplateType template) {
        currentTemplate = template;
        switch (template) {
            case EXECUTIVE_REPORT:
                builder = new ExecutiveReportBuilder(factory);
                break;
            case SIMPLE_INVOICE:
                builder = new SimpleInvoiceBuilder(factory);
                break;
            default:
                throw new IllegalArgumentException("Unsupported template: " + template);
        }
        if (director == null) {
            director = new DocumentDirector(builder);
        } else {
            director.setBuilder(builder);
        }
        System.out.println("[Mediator] Builder -> " + builder.getClass().getSimpleName());
    }

    private void reconfigureRenderer(OutputFormat format) {
        currentFormat = format;
        switch (format) {
            case PDF:
                renderer = new PdfRenderEngine();
                break;
            case HTML:
                renderer = new HtmlRenderEngine();
                break;
            case MARKDOWN:
                renderer = new MarkdownRenderEngine();
                break;
            default:
                throw new IllegalArgumentException("Unsupported format: " + format);
        }
        System.out.println("[Mediator] Renderer -> " + renderer.getClass().getSimpleName());
    }


    private Document processDocument() {
        DocumentStructure structure = director.construct(currentTemplate);
        ProcessingContext context = new ProcessingContext(structure, variables);
        chain.process(context);
        for (String warning : context.getWarnings()) {
            System.out.println("[Chain] warning " + warning);
        }
        if (context.hasErrors()) {
            for (String error : context.getErrors()) {
                System.out.println("[Chain] ERROR " + error);
            }
            return null;
        }
        currentDocument = (currentFormat == OutputFormat.PDF)
                ? new PagedDocument(structure, renderer, ELEMENTS_PER_PAGE)
                : new ContinuousDocument(structure, renderer);
        return currentDocument;
    }

    private void updatePreview() {
        preview.show("Ready: " + currentTemplate + " as " + currentFormat);
    }

    private void runExport() {
        Document document = processDocument();
        if (document == null) {
            lastExport = null;
            return;
        }
        lastExport = document.export();
        preview.show(lastExport);
    }
}
