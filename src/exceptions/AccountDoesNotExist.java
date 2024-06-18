package exceptions;

public class AccountDoesNotExist extends Exception {
    private static final String NO_ACCOUNT = "Account %s does not exist\n";

    public AccountDoesNotExist(){
        super(NO_ACCOUNT);
    }
}
