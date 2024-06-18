package exceptions;

public class EventDoesNotExist extends Exception{
    private static final String EVENT_NOT_ON_ACCOUNT = "%s does not exist in account %s.\n";

    public EventDoesNotExist(){
        super(EVENT_NOT_ON_ACCOUNT);
    }
}
