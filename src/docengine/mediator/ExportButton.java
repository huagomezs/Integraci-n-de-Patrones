package docengine.mediator;


public class ExportButton extends UIComponent {

    private boolean enabled = true;

    public ExportButton() {
        super("ExportButton");
    }

    public void press() {
        if (!enabled) {
            System.out.println("[ExportButton] disabled, ignoring click");
            return;
        }
        notifyMediator(UIEvent.EXPORT_REQUESTED);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
