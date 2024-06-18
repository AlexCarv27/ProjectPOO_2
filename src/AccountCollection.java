import exceptions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;


public interface AccountCollection {
    /**
     * check if the name is already used
     * @param name that we want to check
     * @return true or false
     */
    boolean  isNameValid(String name);

    /**
     * check if the type is one of the three
     * @param name that we want to check
     * @return true or false
     */
    boolean isTypeValid(String name);

    /**
     * method that adds an account to the system
     * @param c name that the account will have
     * @param type authority that the account will have
     */
    void addAccount(String c, String type) throws AccountAlreadyExists, UnknownAccountType;

    /**
     * See if the system has any account
     * @return true or false
     */
    boolean isEmpty();

    /**
     * Gets an Iterator containing  all the accounts registered
     * @return Iterator containing  all the accounts registered
     */
    Iterator<Account> iteratorAccount()throws NoAccountRegistered;

    /**
     * Checks if the account has permission to create high priority events
     * @param name - name of the account to be checked
     * @return true or false
     */
    boolean hasPermission(String name);



    /**
     * Adds an event to the system
     * @param promoter - name of the creator of the event
     * @param name - name of the event
     * @param prior - priority of the event
     * @param date - time of the event
     * @param topics - topics of the event
     * @throws AccountIsBusy- account is busy
     * @throws EventAlreadyExistsInAccount- event already exist in account
     * @throws AccountDoesNotExist-account does not exist
     * @throws UnknownPriority- priority is invalid
     * @throws GuestAccountCannotCreate-guest cannot create events
     * @throws CannotCreateHighEvents-staff cant create high events
     */
    void addEvent(String promoter, String name, String prior, LocalDateTime date, String topics)  throws AccountIsBusy, EventAlreadyExistsInAccount, AccountDoesNotExist ,UnknownPriority,GuestAccountCannotCreate,CannotCreateHighEvents;



    /**
     * Checks if an account is of the "guest" type
     * @param name - name of the account
     * @return true or false
     */
    boolean isGuest(String name);

    /**
     * Checks if there aren't any events registered ion the system
     * @return true or false
     */
    boolean noEvents();

    /**
     * Checks if an account have an event with a given name
     * @param name - name to be checked
     * @param eventName - nme of the event to be checked
     * @return true or false
     */
    boolean eventsWithSameName(String name, String eventName);

    /**
     * Makes an invitation of an account to an event
     * @param inviteName - name of the account to be invited
     * @param eventName - name of the event
     * @param promoterName - name of the promoter of the event
     */
    void invite(String inviteName,String eventName,String promoterName) ;

    /**
     * Checks if an account already accepted an event at a given hour, in order to create an event
     * @param inviteName - name of the promoter
     * @param time - time of the event
     * @return true or false
     */
    boolean isBusyPromoter(String inviteName, LocalDateTime time);

    /**
     * Accepts an event in an account
     * @param name - name of the account
     * @param eventName - name of the event
     */
    void accept(String name,String eventName);


     /**
      * Rejects an event in an account
     * @param name - name of the account
     * @param eventName - name of the event
     */
    void decline(String name,String eventName);

    /**
     * Checks if an account has events
     * @param name - name of the account
     * @return true or false
     */
    boolean noEventsInAccount(String name);

    /**
     * Checks if an account is already invited to an event
     * @param inviteName - name of the invited account
     * @param eventName - name of the event
     * @param promoterName - name of the promoter
     * @return true or false
     */
    boolean isAlreadyInv(String inviteName,String eventName, String promoterName);

    /**
     * Gets an iterator with all the events in the system with a given name
     * @param name - name of the event
     * @return Iterator with the events with a given name
     * @throws AccountDoesNotExist- account does not exist
     * @throws NoEventsInAccount- no events in account
     */
    Iterator<Event> eventsIterator(String name) throws AccountDoesNotExist, NoEventsInAccount;



    /**
     * Checks if there isn't any event with the given name promoted by a given account
     * @param eventName - name of the event
     * @param promoter - name of the promoter
     * @return true or false
     */
    boolean noEventWithName(String eventName, String promoter);

    /**
     * Checks if someone was invited to an event
     * @param name - name of the account
     * @param eventName - name of the event
     * @return true or false
     */
    boolean notOnList(String name, String eventName);

    /**
     * Checks if someone already responded to an invitation (rejected or accepted)
     * @param name - name of the account
     * @param eventName - name of the event
     * @return true or false
     */
    boolean alreadyResponded(String  name, String eventName);

    /**
     * Checks if an account already accepted an event at the same time of another event
     * @param name - name of the account
     * @param eventName - name of the event
     * @return true or false
     */
    boolean alreadyAcceptedAtSameTime(String name, String eventName);

    /**
     * Checks
     * @param inviteName - name of the
     * @param eventName - name of the event
     * @return true or false
     */
    boolean highPriorityPromoter(String inviteName,String eventName);

    /**
     * Checks
     * @param inviteName - name of the account
     * @param eventName - name of the event
     * @return true or false
     */
    boolean highPriorityInvited(String inviteName,String eventName) ;

    /**
     * Checks if an account has two "high" priority events at the same time
     * @param inviteName - name of the account
     * @param eventName - name of the event
     * @return true or false
     */
    boolean highPriorEventAtSameTime(String inviteName,String eventName);

    /**
     * decline the events that were accepted and were mid at the same time that this new one high
     * @param inviteName - name of the account
     * @param eventName - name of the event
     * @return the event that is going to be rejected
     */
    Event noChoiceDecline(String inviteName,String eventName);



    /**
     * Sees if there are the events that are at the same time that the user is invited
     * @param name - name of the account
     * @param eventName - name of the event
     * @return true or false
     */
    boolean invitesAtSameTime(String name,String eventName);

    /**
     * decline the events at the same time of the accepted
     * @param name - name of the account
     * @param eventName - name of the event
     * @return the  events that are declined
     */
    Iterator<Event> declineAutomatically(String name, String eventName);

    /**
     * remove the event of the promoter
     * @param event - name of the event
     * @param name - name of the account
     * @return event that is removed
     */
    String remove(String event, String name);



    /**
     * format the date of the event
     * @param eventName - name of the event
     * @return date in the right format
     */
    String getDateWithFormat(String eventName);

    /**
     * Accounts that have the same event
     * @param eventName - name of the event
     * @param name - name of the account
     * @return account iterator
     * @throws AccountDoesNotExist-account does not exist
     * @throws EventDoesNotExist-event does not exist
     */
    Iterator<Account> eventAccountsIterator(String eventName,String name)throws AccountDoesNotExist,EventDoesNotExist;

    /**
     * sees if the account has accepted the event
     * @param name - name of the account
     * @param eventName - name of the event
     * @return true or false
     */
    boolean hasAcceptedEvent(String name, String eventName);

    /**
     * sees if the account has rejected the event
     * @param name - name of the account
     * @param eventName - name of the event
     * @return true or false
     */
    boolean hasRejectedEvent(String name, String eventName);


    /**
     * returns the event of that name
     * @param eventName - name of the event
     * @return event
     */
    Event getEvent(String eventName);

    /**
     * If the staff has already an event at the same time
     * @param name - name of the account
     * @param eventName - name of the event
     * @return true or false
     */
    boolean staffSameTime(String name, String eventName);

    /**
     * See if there are no topics of what we get
     * @param topics - topics to be checked
     * @return true or false
     */
    boolean noTopics(String topics);

    /**
     * Gets the events with the corresponding topics
     * @param topics - topics to be checked
     * @return the arrayList of the events
     * @throws NoTopics see if there are these topics
     */
    ArrayList<Event> eventsWithTopics(String topics) throws NoTopics;



}
