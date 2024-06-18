package exceptions;

public class AcceptedTheInvitation extends Exception{
    private static final String ACCEPTED_INVITATION = "%s accepted the invitation.\n";

    public AcceptedTheInvitation(){
        super(ACCEPTED_INVITATION);
    }
}
