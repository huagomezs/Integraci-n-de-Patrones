package docengine.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class DocumentStructure {

    private String title;
    private final Map<String, String> metadata = new LinkedHashMap<>();
    private final List<DocumentElement> elements = new ArrayList<>();

    public DocumentStructure(String title) {
        this.title = title;
    }

    public void add(DocumentElement element) {
        elements.add(Objects.requireNonNull(element, "element"));
    }

    public List<DocumentElement> getElements() {
        return Collections.unmodifiableList(elements);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void putMetadata(String key, String value) {
        metadata.put(key, value);
    }

    public Map<String, String> getMetadata() {
        return Collections.unmodifiableMap(metadata);
    }
}
