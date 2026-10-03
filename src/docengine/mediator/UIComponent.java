package docengine.mediator;


public abstract class UIComponent {

    protected final String name;
    private EditorMediator mediator;

    public UIComponent(String name) {
        this.name = name;
    }

    public void setMediator(EditorMediator mediator) {
        this.mediator = mediator;
    }

    protected void notifyMediator(UIEvent event) {
        if (mediator != null) {
            mediator.notify(this, event);
        }
    }

    public String getName() {
        return name;
    }
}
