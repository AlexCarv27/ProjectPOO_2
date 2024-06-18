package exceptions;

public class CannotCreateHighEvents extends Exception {
    private static final String CANNOT_CREATE_HIGH = "Account %s cannot create high priority events.\n";

    public CannotCreateHighEvents(){
        super(CANNOT_CREATE_HIGH);
    }
}
