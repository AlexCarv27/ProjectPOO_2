package exceptions;

public class UnknownAccountType extends Exception {
    private static final String UNKNOWN_ACCOUNT = "Unknown account type.\n";

    public UnknownAccountType(){
        super(UNKNOWN_ACCOUNT);
    }
}
