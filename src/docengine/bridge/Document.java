package docengine.bridge;

import docengine.model.DocumentElement;
import docengine.model.DocumentStructure;
import docengine.model.Footer;
import docengine.model.Header;
import docengine.model.Paragraph;
import docengine.model.Table;

import java.util.Objects;


public abstract class Document {

    private RenderEngine engine;
    private final DocumentStructure structure;

    public Document(DocumentStructure structure, RenderEngine engine) {
        this.structure = Objects.requireNonNull(structure, "structure");
        this.engine = Objects.requireNonNull(engine, "engine");
    }

    public abstract String export();

    public void setEngine(RenderEngine engine) {
        this.engine = Objects.requireNonNull(engine, "engine");
    }

    public RenderEngine getEngine() {
        return engine;
    }

    public DocumentStructure getStructure() {
        return structure;
    }

    protected void renderElement(DocumentElement element) {
        switch (element.getType()) {
            case HEADER:
                engine.renderHeader((Header) element);
                break;
            case PARAGRAPH:
                engine.renderParagraph((Paragraph) element);
                break;
            case TABLE:
                engine.renderTable((Table) element);
                break;
            case FOOTER:
                engine.renderFooter((Footer) element);
                break;
            default:
                throw new IllegalStateException("Unknown element type: " + element.getType());
        }
    }
}
