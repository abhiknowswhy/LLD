package pattern.Handlers;

import pattern.Email;

public abstract class EmailHandler {
    protected EmailHandler nextHandler;

    public EmailHandler setNext(EmailHandler handler) {
        this.nextHandler = handler;
        return handler;
    }

    public void handle(Email email) {
        if (canHandle(email)) {
            process(email);
        }
        
        // Continue to next handler even if current handler processed the email
        if (nextHandler != null) {
            nextHandler.handle(email);
        }
    }

    protected abstract boolean canHandle(Email email);
    protected abstract void process(Email email);
}
