package docengine.bridge;

import docengine.model.DocumentElement;
import docengine.model.DocumentStructure;

import java.util.ArrayList;
import java.util.List;


public class PagedDocument extends Document {

    private final int elementsPerPage;

    public PagedDocument(DocumentStructure structure, RenderEngine engine, int elementsPerPage) {
        super(structure, engine);
        if (elementsPerPage < 1) {
            throw new IllegalArgumentException("elementsPerPage must be >= 1");
        }
        this.elementsPerPage = elementsPerPage;
    }

    @Override
    public String export() {
        RenderEngine engine = getEngine();
        engine.startDocument(getStructure().getTitle());
        List<List<DocumentElement>> pages = paginate();
        for (int i = 0; i < pages.size(); i++) {
            engine.pageBreak(i + 1);
            for (DocumentElement element : pages.get(i)) {
                renderElement(element);
            }
        }
        return engine.finishDocument();
    }

    private List<List<DocumentElement>> paginate() {
        List<DocumentElement> all = getStructure().getElements();
        List<List<DocumentElement>> pages = new ArrayList<>();
        for (int i = 0; i < all.size(); i += elementsPerPage) {
            pages.add(new ArrayList<>(all.subList(i, Math.min(i + elementsPerPage, all.size()))));
        }
        return pages;
    }
}
