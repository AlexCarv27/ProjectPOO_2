import java.util.Iterator;

public interface Invites {
    /**
     * Invites an account to an Event
     * @param account - account to be invited
     */
    void addInvite(Account account);

    /**
     * Accepts an invitation to an event
     * @param account - account to accept invitation
     */
    void accept(Account account);

    /**
     * Rejects an invitation an event
     * @param account - account to reject invitation
     */
    void decline(Account account);

    /**
     * Automatically rejects an event that doesn't have conditions to be accepted
     * @param account - account to automatically reject the event
     */
    void noChoiceDecline(Account account);

    /**
     * Gets the number of people that accepted an event, including its creator
     * @return int number of people that accepted
     */
    int getAccepted();

    /**
     * Gets the number of people that rejected an event
     * @return int number of people that rejected
     */
    int getDeclined();

    /**
     * Gets the number of people that didn't accept or reject an event
     * @return int number of unanswered invites
     */
    int getUnanswered();

    /**
     * Gets the number of invited people to an event
     * @return int number of invites to an event
     */
    int getTotalInvites();

    /**
     * Checks if an account was invited to an event
     * @param name - name of the account that will be checked if was invited
     * @return true or false
     */
    boolean notOnList(String name);

    /**
     * Checks if an account accepted or rejected an event
     * @param name - name of the account that will be checked if responded to the invitation
     * @return true or false
     */
    boolean hasAlreadyResponded(String name);

    /**
     * Gets an iterator with all invited people to an event
     * @return iterator if invited people to an event
     */
    Iterator<Account> eventIterator();

    /**
     * Checks if someone has accepted an event
     * @param name - name of the account that will be checked if accepted the invitation
     * @return true or false
     */
    boolean hasAcceptedEvent(String name);

    /**
     * Checks if someone has rejected an event
     * @param name - name of the account that will be checked if rejected the invitation
     * @return true or false
     */
    boolean hasRejectedEvent(String name);

    /**
     * Checks if someone has not accepted or rejected an event
     * @param name - name of the account that will be checked if didn't respond to the invitation
     * @return true or false
     */
    boolean hasNotRespondedEvent(String name);
}
