package exceptions;

public class AccountAlreadyExists extends Exception {
        private static final String ACCOUNT_EXISTS = "Account %s already exists.\n";

        public AccountAlreadyExists(){
            super(ACCOUNT_EXISTS);
        }
    }


