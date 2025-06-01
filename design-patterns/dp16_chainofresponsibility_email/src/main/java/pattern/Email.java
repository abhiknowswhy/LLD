package pattern;

public class Email {
    private String from;
    private String subject;
    private String content;
    private String category;

    public Email(String from, String subject, String content) {
        this.from = from;
        this.subject = subject;
        this.content = content;
        this.category = "inbox"; // default category
    }

    public String getFrom() {
        return from;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Email{from='" + from + "', subject='" + subject + "', category='" + category + "'}";
    }
}
