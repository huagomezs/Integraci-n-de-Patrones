package docengine.mediator;


public class Preview extends UIComponent {

    private String content = "";

    public Preview() {
        super("Preview");
    }

    public void show(String content) {
        this.content = content;
        System.out.println("[Preview] " + content.split("\n")[0]);
    }

    public String getContent() {
        return content;
    }
}
