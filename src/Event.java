import java.time.LocalDateTime;
import java.util.Iterator;

public interface Event {
   /**
    * Gets the name of the creator (promoter) of the event
    * @return String with the name of the promoter
    */
   String getPromoter();

   /**
    * Gets the name of the event
    * @return String the name of the event
    */
   String getEventName();

   /**
    * Gets the priority of an event (mid or high)
    * @return String with the priority of the event
    */
   String getPriority();

   /**
    * Gets an Iterator containing the Strings, separated by space, that represent the topics associated with the event
    * @return Iterator of Strings that represent the topics
    */
   Iterator <String> getTopics();

   /**
    * Gets the object of the class "Invites" that is associated with the event
    * @return Object of the class "Invite" associated with the event
    */
   Invites getInvite();

   /**
    * Checks if the date of the event is equal to a given date in format of an object of LocalDateTime class
    * @param d2 - "LocalDateTime" class object representing a date
    * @return true or false
    */
   boolean isDifferent(LocalDateTime d2);

   /**
    * Invites an account to an Event, by calling the method with same name from "Invites" class
    * @param account - account to be invited
    */
   void addInvite(Account account);

   /**
    * Accepts an invitation to the Event, by calling the method with same name from "Invites" class
    * @param account - account to accept the invitation
    */
   void accept(Account account);

   /**
    * Rejects an invitation to the Event, by calling the method with same name from "Invites" class
    * @param account - account to reject the invitation
    */
   void decline(Account account);

   /**
    * Automatically rejects an event that cannot be accepted, by calling the method with same name from "Invites" class
    * @param account - account to automatically reject the invitation
    */
   void noChoiceDecline(Account account);

   /**
    * Gets the date of the event
    * @return an object of the class "LocalDateTime" that represents the date of the event
    */
   LocalDateTime getDate();

   /**
    * Gets an iterator of the accounts invited to the event, by calling the "Invites" class method called eventIterator
    * @return Iterator that contains the invited accounts to the event
    */
   Iterator<Account> eventAccountsIterator();

   /**
    * Gets the original String that represents the topics without being separated by spaces (used to print this String)
    * @return - String containing the topics before separating it
    */
   String getRawTopics();



}
