import java.time.LocalDateTime;
import java.util.*;


public class InviteClass implements Invites{
    private List<Account> invited;
    private Map<String, Account> unanswered;
    private Map<String, Account> rejected;
    private Map<String, Account> accepted;
    private int totalInvites;

    public InviteClass(){
        invited = new ArrayList<>();
        unanswered = new HashMap<>();
        rejected = new HashMap<>();
        accepted = new HashMap<>();
        totalInvites = 1;
    }

    public void addInvite(Account account){
        invited.add(account);
        unanswered.put(account.getName(), account);
        totalInvites++;
    }

    public void accept(Account account){
        unanswered.remove(account.getName());
        accepted.put(account.getName(), account);
    }

    public void decline(Account account){
        unanswered.remove(account.getName());
        rejected.put(account.getName(), account);
    }
    public void noChoiceDecline(Account account){
        accepted.remove(account.getName());
        rejected.put(account.getName(), account);
    }

    public int getAccepted(){
        int i = 1;
        if(accepted != null)
            i = accepted.size()+1;
        return i;
    }
    public int getDeclined(){
        int i = 0;
        if(rejected != null)
            i = rejected.size();
        return i;
    }
    public int getUnanswered(){
        int i = 0;
        if(unanswered != null)
            i = unanswered.size();
        return i;
    }
    public int getTotalInvites(){
        return totalInvites;
    }

    public boolean notOnList(String name){
        return !unanswered.containsKey(name);
    }

    public boolean hasAlreadyResponded(String name){
        return (accepted.containsKey(name) || rejected.containsKey(name));
    }

    public Iterator<Account> eventIterator(){
        return invited.iterator();
    }

    public boolean hasAcceptedEvent(String name){
            return accepted.containsKey(name);
    }

    public boolean hasRejectedEvent(String name){
        return  rejected.containsKey(name);
    }

    public boolean hasNotRespondedEvent(String name){
        return unanswered.containsKey(name);
    }


}
