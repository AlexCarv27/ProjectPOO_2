package exceptions;

public class GuestAccountCannotCreate extends Exception {
    private static final String GUEST_CANNOT_CREATE = "Guest account %s cannot create events.\n";

    public GuestAccountCannotCreate(){
        super(GUEST_CANNOT_CREATE);
    }
}
