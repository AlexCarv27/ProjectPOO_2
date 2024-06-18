
import exceptions.*;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class AccountCollectionClass implements AccountCollection{
    private Map<String, Account> accounts;
    private Map<String, Event> events;

    public AccountCollectionClass(){
        accounts = new HashMap<>();
        events = new HashMap<>();
    }

    public boolean isNameValid(String name){

        return accounts.containsKey(name);
    }

    public boolean isTypeValid(String type){
        boolean valid = false;
        if(type.equals("staff") || type.equals("manager") || type.equals("guest"))
            valid = true;
        return valid;
    }

    public void addAccount(String c, String type) throws AccountAlreadyExists, UnknownAccountType{
        if(accounts.containsKey(c))
            throw new AccountAlreadyExists();
        if(!isTypeValid(type))
            throw new UnknownAccountType();

        AccountClass T = null;
        switch (type){
            case "staff" : T = new StaffClass(c,type); break;
            case "manager" : T = new ManagerClass(c,type); break;
            case "guest" : T = new GuestClass(c,type); break;
        }
         accounts.put(c,T);
    }

    public boolean isEmpty(){
        return accounts.isEmpty();
    }

    public Iterator<Account> iteratorAccount() throws NoAccountRegistered {
        if(isEmpty())
            throw new NoAccountRegistered();

        return accounts.values().iterator();
    }



    public boolean hasPermission(String name){
        String aux = getTypeByName(name);
        return (aux.equals("staff"));
    }





    public void addEvent(String promoter, String name, String prior, LocalDateTime date, String topics)
            throws AccountIsBusy, EventAlreadyExistsInAccount, AccountDoesNotExist ,UnknownPriority,GuestAccountCannotCreate,CannotCreateHighEvents{
        if(isEmpty() || !isNameValid(promoter))
            throw new AccountDoesNotExist();
        if(!(prior.equals("mid")) && !(prior.equals("high")))
            throw new UnknownPriority();
        if(isGuest(promoter))
            throw new GuestAccountCannotCreate();
        if(prior.equals("high") && hasPermission(promoter))
            throw new CannotCreateHighEvents();
        if(!noEvents() && eventsWithSameName(promoter, name))
            throw new EventAlreadyExistsInAccount();
        if(isBusyPromoter(promoter, date))
            throw new AccountIsBusy();

        EventClass event = new EventClass(promoter, name, prior, date, topics);
        Account account = accounts.get(promoter);
        account.addEventToAccount(event, name);
        events.put(name, event);
    }



    public boolean isGuest(String name){
        boolean b = false;
        var c = accounts.get(name);
        if(c.getType().equals("guest"))
            b = true;
        return b;
    }

    public boolean noEvents(){
        return events.isEmpty();
    }

    public boolean eventsWithSameName(String name, String eventName){
        return accounts.get(name).eventWithSameName(eventName);
    }

    public void invite(String name, String eventName, String promoterName) {
        Account account = accounts.get(name);
        Event event = getEvent(eventName);
        event.addInvite(account);
        account.addInvitedEvent(event);
    }

    public void accept(String name,String eventName){
        Account account = accounts.get(name);
        Event event = getEvent(eventName);
        event.accept(account);
        account.addAcceptedEvent(event);
    }

    public void decline(String name,String eventName){
        Account account = accounts.get(name);
        Event event = getEvent(eventName);
        event.decline(account);
        account.addDeclinedEvent(event);
    }

    public boolean isBusyPromoter(String inviteName, LocalDateTime time){
        return accounts.get(inviteName).hasAlreadyAcceptedAtSameTime(time);
    }

    public boolean noEventsInAccount(String name){
        Account account = accounts.get(name);
        return account.noEvents();
    }

    public Iterator<Event> eventsIterator(String name)throws AccountDoesNotExist,NoEventsInAccount{
        if(isEmpty() || !isNameValid(name))
            throw new AccountDoesNotExist();
        if(noEventsInAccount(name))
            throw new NoEventsInAccount();


        Account account = accounts.get(name);
        return account.eventsIterator();
    }



    public boolean isAlreadyInv(String inviteName,String eventName,String promoterName){
        return accounts.get(inviteName).isAlreadyInv(eventName,promoterName);
    }

    public boolean noEventWithName(String eventName, String promoter){
        Account account = accounts.get(promoter);
        return account.noEventWithName(eventName);
    }

    public boolean notOnList(String name, String eventName){
        Event event = getEvent(eventName);
        Invites invite = event.getInvite();
        return invite.notOnList(name);
    }

    public boolean alreadyResponded(String  name, String eventName){
        Event event = getEvent(eventName);
        Invites invite = event.getInvite();
        return invite.hasAlreadyResponded(name);
    }

        public boolean alreadyAcceptedAtSameTime(String name, String eventName){
        Event event = getEvent(eventName);
        Account account = accounts.get(name);
        LocalDateTime time = event.getDate();
        return account.hasAlreadyAcceptedAtSameTime(time) && !(account.getType().equals("staff"));
    }

    public boolean staffSameTime(String name, String eventName){
        Event event = getEvent(eventName);
        Account account = accounts.get(name);
        LocalDateTime time = event.getDate();
        return account.hasAlreadyAcceptedAtSameTime(time) && (account.getType().equals("staff"));
    }


    public boolean highPriorityPromoter(String inviteName,String eventName){
        boolean found=false;
        Account account = accounts.get(inviteName);
        Event event = getEvent(eventName);
        if(account.eventAtSameTime(event.getDate()).getPromoter().equals(inviteName))
            found=true;
       return found;
    }

    public boolean highPriorityInvited(String inviteName, String eventName) {
        boolean found=false;
        Account account = accounts.get(inviteName);
        Event event = getEvent(eventName);
        String promName = event.getPromoter();
        if(!account.highPriorEventAtSameTime(event.getDate()) && events.get(eventName).getPriority().equals("high") && account.getType().equals("staff")){
            invite(inviteName,eventName,promName);
            accept(inviteName, eventName);
            found=true;
        }
        return found;
    }


    public boolean highPriorEventAtSameTime(String inviteName,String eventName){
        return accounts.get(inviteName).highPriorEventAtSameTime(events.get(eventName).getDate());
    }


    public Event noChoiceDecline(String inviteName,String eventName){
        Event aux = null;
        Event event = getEvent(eventName);
        Account account = accounts.get(inviteName);
        LocalDateTime time= event.getDate();
        if(staffSameTime(inviteName,eventName) && account.staffEventAtSameTime(time,eventName)!=null
                       && account.staffEventAtSameTime(time,eventName).getPriority().equals("mid")){
            aux = account.staffEventAtSameTime(time,eventName);
            account.staffEventAtSameTime(time,eventName).noChoiceDecline(account);
            account.noChoiceDecline(account.staffEventAtSameTime(time,eventName));
        }
        return aux;
    }



   public boolean invitesAtSameTime(String name, String eventName) {
       boolean found = false;
       Account account = accounts.get(name);
       Event event = getEvent(eventName);
       LocalDateTime time = event.getDate();
       Iterator<Event> it = account.unansweredEvents();
       while (it.hasNext()) {
           Event e = it.next();
           if(e.getDate().isEqual(time)) {
               found =true;
               return found;
           }
       }

       return found;
   }


    public Iterator<Event> declineAutomatically(String name, String eventName){
        ArrayList<Event> sameTimeEvents = new ArrayList<>();
        Account account = accounts.get(name);
        Event event = getEvent(eventName);
        LocalDateTime time = event.getDate();
        Iterator<Event> it = account.unansweredEvents();
        while(it.hasNext()){
            Event e = it.next();
            if(e.getDate().isEqual(time)) {
                sameTimeEvents.add(e);
                e.decline(account);
                it.remove();
                account.addDeclinedEvent(e);
            }
        }
        return sameTimeEvents.iterator();
    }

    public String remove(String eventName, String name){
        String event2 = null;
        Account account = accounts.get(name);
        Event event = events.get(eventName);
        if(account.hasAlreadyAcceptedAtSameTime(event.getDate())){
            event2 = account.eventAtSameTime(event.getDate()).getEventName();
            account.remove(event2);
            Iterator<Account> it = events.get(event2).getInvite().eventIterator();
            while(it.hasNext()){
                Account aux = it.next();
                aux.remove(event2);
            }
        }
        return event2;
    }





    public String getDateWithFormat(String eventName){
        Event event = events.get(eventName);
        LocalDateTime time = event.getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH");
        return time.format(formatter);
    }

    public Iterator<Account> eventAccountsIterator(String eventName,String name) throws AccountDoesNotExist,EventDoesNotExist{
        if(!isNameValid(name))
            throw new AccountDoesNotExist();
        if( (noEvents() || !eventsWithSameName(name, eventName)))
            throw new EventDoesNotExist();

        Event event = events.get(eventName);
        return event.eventAccountsIterator();
    }

    public boolean hasAcceptedEvent(String name, String eventName){
        Event event = events.get(eventName);
        Invites invite = event.getInvite();
        return invite.hasAcceptedEvent(name);
    }

    public boolean hasRejectedEvent(String name, String eventName){
        Event event = events.get(eventName);
        Invites invite = event.getInvite();
        return invite.hasRejectedEvent(name);
    }



    public Event getEvent(String eventName){return events.get(eventName);}

    public boolean noTopics(String topics){
        boolean noEvents = true;
        String [] splitTopics = topics.split(" ");
        ArrayList<String> splited = new ArrayList<>(Arrays.asList(splitTopics));
        for(Event event: events.values()){
            Iterator<String> it =  event.getTopics();
            for(String str: splited)
                while(it.hasNext()) {
                    String string = it.next();
                    if (str.equals(string)) {
                        noEvents = false;
                        break;
                    }
                }
        }
        return noEvents;
    }

    public ArrayList<Event> eventsWithTopics(String topics) throws NoTopics{
        if(noTopics(topics))
            throw new NoTopics();
        ArrayList<Event> returned = new ArrayList<>();
        for(Account account : accounts.values()) {
            ArrayList<Event> aux = account.eventsWithTopics(topics);
            for(Event event : aux)
                if(!returned.contains(event))
                 returned.add(event);
        }
        returned.sort(new EventComparator(topics));
        return returned;
    }


    //Método auxiliares
    private String getTypeByName(String name){
        String type = " ";
        if(accounts.containsKey(name))
            type = accounts.get(name).getType();
        return type;
    }




}
