import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class EventClass implements Event{
    private String promoter;
    private String eventName;
    private String priority;
    private LocalDateTime date;
    private String topics;
    private String[] splitTopics;
    private List<String> splitList;
    private InviteClass invites;


    public EventClass(String promoter, String eventName, String priority, LocalDateTime date, String topics){
        this.promoter = promoter;
        this.eventName = eventName;
        this.priority = priority;
        this.date = date;
        this.topics = topics;
        splitTopics = topics.split(" ");
        splitList = new ArrayList<>(Arrays.asList(splitTopics));
        this.invites = new InviteClass();
    }

    public String getPromoter(){return promoter;}

    public String getEventName(){return eventName;}

    public String getPriority(){return priority;}

    public String getRawTopics(){return  topics;}

    public Iterator <String> getTopics(){return splitList.iterator();}

    public Invites getInvite(){return invites;}

    public boolean isDifferent( LocalDateTime d2){
        return(!date.isEqual(d2));
    }

    public void addInvite(Account account){
        invites.addInvite(account);
    }

    public void accept(Account account){
        invites.accept(account);
    }

    public void decline(Account account){
        invites.decline(account);
    }

    public void noChoiceDecline(Account account){invites.noChoiceDecline(account);}

    public LocalDateTime getDate(){return date;}

    public Iterator<Account> eventAccountsIterator(){
        return invites.eventIterator();
    }



    }

