import java.time.LocalDateTime;
import java.util.*;

public abstract class AccountClass implements Account{
    private String name;
    private String type;
    private Map<String, Event> events;
    private Map<String, Event> createdEvents;
    private Map<String, Event> unansweredEvents;
    private List<Event> unansweredList;
    private Map<String, Event> acceptedEvents;
    private Map<String, Event> rejectedEvents;

    public AccountClass(String name, String type){
        this.name=name;
        this.type = type;
        rejectedEvents = new HashMap<>();
        acceptedEvents = new HashMap<>();
        unansweredList = new ArrayList<>();
        unansweredEvents = new HashMap<>();
        createdEvents =  new HashMap<>();
        events = new LinkedHashMap<>();
    }

    public String getName(){
        return name.trim();
    }

    public String getType(){return type;}

    public void addEventToAccount(EventClass event, String name){
        createdEvents.put(name, event);
        acceptedEvents.put(name, event);
        events.put(name,event);
    }

    public boolean eventWithSameName(String eventName){
        return (acceptedEvents.containsKey(eventName));
    }


    public boolean getOccupation(LocalDateTime date){
        boolean free=true;
        for(int i=0;i<events.size();i++){
            if(!events.get(i).isDifferent(date)){
                free=false;
                return free;
            }
        }
        return free;
    }

    public void addAcceptedEvent(Event event){
        acceptedEvents.put(event.getEventName(), event);
        unansweredEvents.remove(event.getEventName());
        unansweredList.remove(event);
    }

    public void addDeclinedEvent(Event event){
        rejectedEvents.put(event.getEventName(), event);
        unansweredEvents.remove(event.getEventName());
        unansweredList.remove(event);

    }

    public void addInvitedEvent(Event event){
        unansweredList.add(event);
        unansweredEvents.put(event.getEventName(), event);
        events.put(event.getEventName(), event);
    }

    public void noChoiceDecline(Event event){
        rejectedEvents.put(event.getEventName(), event);
        acceptedEvents.remove(event.getEventName());
    }


    public boolean noEvents(){
        return (events.isEmpty());
    }

    public boolean isAlreadyInv(String eventName,String promoterName){
        return (events.containsKey(eventName) && events.get(eventName).getPromoter().equals(promoterName));
    }

    public Iterator<Event> createdEventsIterator(){
        return events.values().iterator();
    }

    public Iterator<Event> acceptedEventsIterator() {
        return acceptedEvents.values().iterator();
    }

    public Iterator<Event> eventsIterator(){
        return events.values().iterator();
    }

    public boolean noEventWithName(String eventName){
        return (!events.containsKey(eventName));
    }

    public boolean hasAlreadyAcceptedAtSameTime(LocalDateTime time){
        boolean found = false;
        for(Event event: acceptedEvents.values()) {
            if (event.getDate().isEqual(time)) {
                found = true;
                break;
            }
        }
        return found;
    }



    public Iterator<Event> unansweredEvents(){
        return unansweredList.iterator();
    }
    public Iterator<Event> acceptedEvents(){return acceptedEvents.values().iterator();}


    public boolean highPriorEventAtSameTime(LocalDateTime time){
        boolean found = false;
        for(Event event: acceptedEvents.values()) {
            if (event.getPriority().equals("high") && event.getDate().isEqual(time)) {
                found = true;
                break;
            }
        }
        return found;
    }

    public void remove(String event){
        acceptedEvents.remove(event);
        events.remove(event);
    }

    public Event eventAtSameTime(LocalDateTime time){
        Event evt = null;
        for(Event event: acceptedEvents.values()){
            if (event.getDate().isEqual(time)){
                evt = event;
                break;
            }
        }
        return evt;
    }

    public Event eventAtSameTimeStaff(LocalDateTime time){
        Event evt = null;
        for(Event event: acceptedEvents.values()){
            if (event.getDate().isEqual(time) && event.getPriority().equals("mid")){
                evt = event;
            }
        }
        return evt;
    }

    public Event staffEventAtSameTime(LocalDateTime time,String eventName){
        Event aux = null;
        for(Event event: acceptedEvents.values()){
            if(event.getDate().isEqual(time) && !event.getEventName().equals(eventName)){
                aux = event;
                break;
            }
        }
        return aux;
    }

    public ArrayList<Event> eventsWithTopics(String topics){
        String [] splitTopics = topics.split(" ");
        ArrayList<String> splited = new ArrayList<>(Arrays.asList(splitTopics));
        ArrayList<Event> returned = new ArrayList<>();
        for(Event event : createdEvents.values())
            for(String str : splited) {
                Iterator<String> it = event.getTopics();
                while(it.hasNext()) {
                    String s = it.next();
                    if (s.equals(str))
                        returned.add(event);
                }
            }
        return returned;
    }


}
