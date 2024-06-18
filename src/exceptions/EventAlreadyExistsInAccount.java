package exceptions;

public class EventAlreadyExistsInAccount extends Exception {
    private static final String EVENT_ALREADY_EXISTS = "%s already exists in account %s.\n";

    public EventAlreadyExistsInAccount(){
        super(EVENT_ALREADY_EXISTS);
    }
}
