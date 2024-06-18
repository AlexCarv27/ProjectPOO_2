package exceptions;

public class AccountIsBusy extends Exception {
    private static final String IS_BUSY = "Account %s is busy.\n";

    public AccountIsBusy(){
        super(IS_BUSY);
    }
}
