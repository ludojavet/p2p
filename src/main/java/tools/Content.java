package tools;

public class Content {
    private String content;

    public String getContent() {
        return content;
    }

    public Content(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}
