package docengine.bridge;

import docengine.model.DocumentElement;
import docengine.model.DocumentStructure;

public class ContinuousDocument extends Document {

    public ContinuousDocument(DocumentStructure structure, RenderEngine engine) {
        super(structure, engine);
    }

    @Override
    public String export() {
        RenderEngine engine = getEngine();
        engine.startDocument(getStructure().getTitle());
        for (DocumentElement element : getStructure().getElements()) {
            renderElement(element);
        }
        return engine.finishDocument();
    }
}
