package exceptions;

public class NoAccountRegistered extends Exception {
    private static final String NO_ACCOUNT = "No account registered.\n";

    public NoAccountRegistered(){
        super(NO_ACCOUNT);
    }
}
