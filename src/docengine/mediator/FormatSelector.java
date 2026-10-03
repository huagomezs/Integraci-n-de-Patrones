package docengine.mediator;

import docengine.model.OutputFormat;


public class FormatSelector extends UIComponent {

    private OutputFormat selectedFormat = OutputFormat.PDF;

    public FormatSelector() {
        super("FormatSelector");
    }

    public void select(OutputFormat format) {
        this.selectedFormat = format;
        notifyMediator(UIEvent.FORMAT_CHANGED);
    }

    public OutputFormat getSelectedFormat() {
        return selectedFormat;
    }
}
