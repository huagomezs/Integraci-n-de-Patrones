package docengine.mediator;

import docengine.model.TemplateType;


public class BuilderToolbar extends UIComponent {

    private TemplateType selectedTemplate = TemplateType.EXECUTIVE_REPORT;

    public BuilderToolbar() {
        super("BuilderToolbar");
    }

    public void selectTemplate(TemplateType template) {
        this.selectedTemplate = template;
        notifyMediator(UIEvent.TEMPLATE_CHANGED);
    }

    public TemplateType getSelectedTemplate() {
        return selectedTemplate;
    }
}
