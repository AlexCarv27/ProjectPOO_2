import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

public interface
Account {
    /**
     * Gets the name of the account
     * @return String representing the name of the account
     */
    String getName();

    /**
     * Gets the type of the account (manager, staff, guest)
     * @return String representing the type of the account
     */
    String getType();

    /**
     * Adds an event to the account and automatically accepts it (this means creating an event)
     * @param event - Object of "Event" that is the event to be created
     * @param name - name of the event
     */
    void addEventToAccount(EventClass event, String name);

    /**
     * Checks if the account does not have an event at a given time
     * @param date - "LocalDateTime" object that represents a specific time
     * @return true or false
     */
    boolean getOccupation(LocalDateTime date);

    /**
     * Checks if the account already created an event with a given name, by checking accepted events
     * @param name - name to be checked if already is the name of a created event by the account
     * @return true or false
     */
    boolean eventWithSameName(String name);

    /**
     * Accepts an invitation to an event, by adding it to the accepted events and removing it from the unanswered
     * @param event - event to be accepted
     */
    void addAcceptedEvent(Event event);

    /**
     * Rejects an invitation to an event, by adding it to the rejected events and removing it from the unanswered
     * @param event - event to be rejected
     */
    void addDeclinedEvent(Event event);

    /**
     * Adds to the account an invitation to a given event
     * @param event - event that the account was invited to
     */
    void addInvitedEvent(Event event);

    /**
     * Automatically rejects an event that has no conditions to be accepted without adding the invitation
     * @param event - event to be rejected
     */
    void noChoiceDecline(Event event);

    /**
     * Check if the account has no events associated to it (created events or invitations)
     * @return true or false
     */
    boolean noEvents();

    /**
     * Gets an iterator of all events associated with the account (created or invitations)
     * @return Iterator of objects of the "Event" class that represent the events associated with the account
     */
    Iterator<Event> eventsIterator();

    /**
     * Gets an Iterator of all events the account has accepted
     * @return Iterator of objects of the "Event" class that represent the events the account accepted
     */
    Iterator<Event> acceptedEventsIterator();

    /**
     * Checks if the account was already invited to an event, by checking the name and promoter of the event
     * @param eventName - name of the event to be checked
     * @param promoterName - name of the promoter of the event
     * @return true or false
     */
    boolean isAlreadyInv(String eventName, String promoterName);

    /**
     * Checks if the account has doesn't have any event associated with a given name
     * @param eventName - name to be checked
     * @return true or false
     */
    boolean noEventWithName(String eventName);

    /**
     * Checks if an event was already accepted at a given time
     * @param time - "LocalDateTime" object representing the time to be checked
     * @return true or false
     */
    boolean hasAlreadyAcceptedAtSameTime(LocalDateTime time);

    /**
     * Gets an iterator of events that the account was invited but didn't respond to yet
     * @return Iterator with the unanswered events
     */
    Iterator<Event> unansweredEvents();

    /**
     * Gets an iterator of events that the account was invited to and accepted
     * @return Iterator with accepted events
     */
    Iterator<Event> acceptedEvents();

    /**
     * Checks if the account has accepted a high priority event at a given time
     * @param time - "LocalDateTime" object representing the time to be checked
     * @return true or false
     */
    boolean highPriorEventAtSameTime(LocalDateTime time);

    /**
     * Removes an event created by the account that the account can't attend to
     * @param event - event to be removed
     */
    void remove(String event);

    /**
     * Checks if an event was already accepted at a given time and returns it
     * @param time - "LocalDateTime" object representing the time to be checked
     * @return event in case it results
     */
    Event eventAtSameTime(LocalDateTime time);

    /**
     * Checks if a "mid" priority event with a given name was already accepted at a given time and returns it
     * @param time - "LocalDateTime" object representing the time to be checked
     * @param eventName - name of the event to be checked
     * @return the event in case it results
     */
    Event staffEventAtSameTime(LocalDateTime time, String eventName);

    /**
     *
     * @param time
     * @return
     */
    Event eventAtSameTimeStaff(LocalDateTime time);

    /**
     * Gets the events that contain at least one of the given topics, place them in an ArrayList and returns it
     * @param topics - String containing the topics to be checked
     * @return  ArrayList containing the events that have at least one of the given topics
     */
    ArrayList<Event> eventsWithTopics(String topics);

}
