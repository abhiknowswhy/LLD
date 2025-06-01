package pattern.Handlers.Impl;

import pattern.Email;
import pattern.Handlers.EmailHandler;
import java.util.Arrays;
import java.util.List;

public class SpamKeywordHandler extends EmailHandler {
    private final List<String> spamKeywords = Arrays.asList(
        "promotion",
        "discount",
        "limited time",
        "advertisement",
        "special offer",
        "act now",
        "click here"
    );

    @Override
    protected boolean canHandle(Email email) {
        String subjectLower = email.getSubject().toLowerCase();
        String contentLower = email.getContent().toLowerCase();
        
        return spamKeywords.stream()
            .anyMatch(keyword -> 
                subjectLower.contains(keyword) || contentLower.contains(keyword));
    }

    @Override
    protected void process(Email email) {
        System.out.println("Detected spam keywords in email: " + email.getSubject());
        email.setCategory("spam");
    }
}
