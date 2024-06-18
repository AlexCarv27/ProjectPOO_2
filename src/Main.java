import exceptions.*;

import java.time.LocalDateTime;
import java.util.*;


public class Main {

    private static final String BYE = "Bye!";


    private static final String HELP_MESSAGE = "%s - %s\n";

    private static final String AVAILABLE_COMMANDS = "Available commands:";

    public static void main(String[] args) {
        AccountCollectionClass accounts = new AccountCollectionClass();
        Scanner in = new Scanner(System.in);
        Help command;
        String instruction;
        do {
            instruction = in.next().toUpperCase().trim();
            command = getCommand(instruction);
            executeCommand(instruction, in, accounts, command);
        } while (!command.name().equals(Help.EXIT.name()));
        in.close();
    }

    private static Help getCommand(String instruction) {

        try {
            return Help.valueOf(instruction);
        } catch (IllegalArgumentException e) {
            return Help.UNKNOWN;
        }

    }

    private static void executeCommand(String instruction, Scanner in, AccountCollection accounts, Help command) {
        switch (command) {
            case EXIT -> System.out.println(BYE);
            case HELP -> processHelp();
            case REGISTER -> register(in, accounts);
            case ACCOUNTS -> accounts(accounts);
            case CREATE -> create(in, accounts);
            case EVENTS -> events(in, accounts);
            case INVITE -> invite(in, accounts);
            case RESPONSE -> response(in, accounts);
            case EVENT -> event(in,accounts);
            case TOPICS -> topics(in,accounts);
            default -> System.out.println("Unknown command " + instruction + ". Type help to see available commands.");

        }
    }

    private static void processHelp() {
        System.out.println(AVAILABLE_COMMANDS);
        for (Help command : Help.values()){
            if (command != Help.UNKNOWN) {
                String name = command.name().toLowerCase();
                String description = command.getDescription();
                System.out.printf(HELP_MESSAGE, name, description);
            }
        }
    }

    private static void register(Scanner in, AccountCollection accounts) {
        String name = in.next().trim();
        String type = in.next().trim();
        try{
            accounts.addAccount(name, type);
            System.out.println(name + " was registered.");
        } catch (AccountAlreadyExists e){
            System.out.printf("Account %s already exists.\n",name);
        }catch(UnknownAccountType e) {
            System.out.printf("Unknown account type.\n");
        }
    }

    private static void accounts(AccountCollection accounts) {
        List<Account> collection = new ArrayList<>();
        try {
            Iterator<Account> it = accounts.iteratorAccount();
            System.out.println("All accounts:");
            while (it.hasNext()) {
                Account c = it.next();
                collection.add(c);
            }
            collection.sort(new Comparator<Account>() {
                @Override
                public int compare(Account p1, Account p2) {
                    return p1.getName().compareTo(p2.getName());
                }
            });
            for (Account element : collection)
                System.out.println(element.getName() + " [" + element.getType() + "]");
        }catch(NoAccountRegistered e){
            System.out.println("No account registered.");
        }
    }

    private static void create(Scanner in, AccountCollection accounts) {
        String name = in.nextLine().trim(); String eventName = in.nextLine().trim();
        String prior = in.next().trim(); int y = in.nextInt(); int m = in.nextInt();
        int d = in.nextInt(); int h = in.nextInt(); in.nextLine();
        String topic = in.nextLine().trim();
        LocalDateTime time = LocalDateTime.of(y, m, d, h, 0);
     try {
            accounts.addEvent(name, eventName, prior, time, topic);
            if(accounts.invitesAtSameTime(name,eventName)) {
                accounts.declineAutomatically(name, eventName);
            }
            System.out.println(eventName + " is scheduled.");
            }catch(AccountDoesNotExist e){
            System.out.println("Account " + name + " does not exist.");
        }catch(UnknownPriority e){
            System.out.println("Unknown priority type.");
        }catch(GuestAccountCannotCreate e){
            System.out.printf("Guest account %s cannot create events.\n", name);
        }catch(CannotCreateHighEvents e){
            System.out.printf("Account %s cannot create high priority events.\n", name);
        }catch(EventAlreadyExistsInAccount e){
            System.out.printf("%s already exists in account %s.\n", eventName, name);

        }catch(AccountIsBusy e){
            System.out.printf("Account %s is busy.\n", name);
        }

    }

    private static void events(Scanner in, AccountCollection accounts) {
        String name = in.nextLine().trim();
        try{

            Iterator<Event> it = accounts.eventsIterator(name);
            System.out.println("Account " + name + " events:");
            while (it.hasNext()) {
                Event e = it.next();
                System.out.println(e.getEventName() + " status [invited " + e.getInvite().getTotalInvites() + "] [accepted " +
                        e.getInvite().getAccepted() + "] [rejected " + e.getInvite().getDeclined() + "] [unanswered " + e.getInvite().getUnanswered() + "]");
            }
        }catch (AccountDoesNotExist e){
            System.out.println("Account " + name + " does not exist.");
        }catch (NoEventsInAccount e){
            System.out.println("Account " + name + " has no events.");
        }
        }



    private static void invite(Scanner in, AccountCollection accounts) {
        String inviteName = in.next().trim();
        String promoterName = in.next().trim();
        String eventName = in.nextLine().trim();
        if (!accounts.isNameValid(promoterName)) {
            System.out.println("Account " + promoterName + " does not exist.");
        } else if(!accounts.isNameValid(inviteName)){
            System.out.println("Account " + inviteName + " does not exist.");
        }
         else if (accounts.noEvents() || !accounts.eventsWithSameName(promoterName, eventName)) {
            System.out.println(eventName + " does not exist in account " + promoterName + ".");
        } else if (accounts.isAlreadyInv(inviteName, eventName, promoterName)) {
                      System.out.println("Account " + inviteName + " was already invited.");
        } else if (accounts.alreadyAcceptedAtSameTime(inviteName, eventName)) {
                      System.out.println("Account " + inviteName + " already attending another event.");
        } else if(accounts.highPriorEventAtSameTime(inviteName,eventName)){
                      System.out.println("Account " +inviteName+" already attending another event.");
        }else if(accounts.highPriorityInvited(inviteName,eventName)) {
            System.out.println(inviteName + " accepted the invitation.");
            if (accounts.highPriorityPromoter(inviteName, eventName)) {
                String event = accounts.remove(eventName, inviteName);
                System.out.println(event + " promoted by " + inviteName + " was removed.");
            } else if  (accounts.invitesAtSameTime(inviteName, eventName) || accounts.staffSameTime(inviteName, eventName)) {
                    Iterator<Event> it = accounts.declineAutomatically(inviteName, eventName);
                    Event ev = accounts.noChoiceDecline(inviteName, eventName);
                    if(ev!= null){
                        System.out.println(ev.getEventName() + " promoted by " + ev.getPromoter() + " was rejected.");
                    }
                    while (it.hasNext()) {
                        Event event = it.next();
                        System.out.println(event.getEventName() + " promoted by " + event.getPromoter() + " was rejected.");
                    }
                }
         }
        else{
                accounts.invite(inviteName, eventName,promoterName);
                System.out.println(inviteName + " was invited.");

            }
        }


    public static void response(Scanner in, AccountCollection accounts) {
        String name = in.nextLine().trim(); String promoter = in.next().trim();
        String eventName = in.nextLine().trim(); String response = in.next().trim();
        if (!accounts.isNameValid(promoter)) {
            System.out.println("Account " + promoter + " does not exist.");
        } else if(!accounts.isNameValid(name)){
            System.out.println("Account " + name + " does not exist.");
        }else if(!response.equals("accept") && !response.equals("reject")) {
            System.out.println("Unknown event response.");
        } else if (accounts.noEventWithName(eventName, promoter)){
            System.out.println(eventName + " does not exist in account " + promoter + ".");
        }else if(accounts.alreadyResponded(name, eventName)) {
            System.out.println("Account " + name + " has already responded.");
        }else if(accounts.notOnList(name, eventName)){
            System.out.println("Account " + name + " is not on the invitation list.");
        }else if(accounts.alreadyAcceptedAtSameTime(name, eventName)){
            System.out.println(name + " already attending another event.");
        }else{
            if(response.equals("accept")) {
                System.out.println("Account " +name + " has replied " + response + " to the invitation.");
                accounts.accept(name, eventName);
                if(accounts.invitesAtSameTime(name,eventName)) {
                    Iterator<Event> it = accounts.declineAutomatically(name, eventName);
                    while (it.hasNext()) {
                        Event event = it.next();
                        System.out.println(event.getEventName() + " promoted by " + event.getPromoter() + " was rejected.");
                    }
                }
            }if(response.equals("reject")) {
                System.out.println("Account "+name + " has replied " + response + " to the invitation.");
                accounts.decline(name, eventName);
            }

        }
    }

    private static void event(Scanner in, AccountCollection accounts) {
        String name = in.next().trim(); String eventName = in.nextLine().trim();
        try{
            Iterator<Account> it = accounts.eventAccountsIterator(eventName,name);
            System.out.println(eventName + " occurs on " + accounts.getDateWithFormat(eventName) + "h:");
            System.out.println(name + " [accept]");
            String state;
            while(it.hasNext()){
                String aux=it.next().getName();
                if(accounts.hasAcceptedEvent(aux, eventName))
                    state = "accept";
                else if(accounts.hasRejectedEvent(aux, eventName))
                    state = "reject";
                else
                    state = "no_answer";
                System.out.println(aux + " ["+ state +"]");
            }
        }catch (AccountDoesNotExist e){
            System.out.println("Account " + name + " does not exist.");
        }catch(EventDoesNotExist e){
            System.out.println(eventName + " does not exist in account " + name + ".");
        }
    }
    private static void topics(Scanner in, AccountCollection accounts) {
        String topics = in.nextLine().trim();
        try{
            ArrayList<Event> eventsWithTopics = accounts.eventsWithTopics(topics);
            System.out.println("Events on topics " + topics + ":");
            for(Event event: eventsWithTopics)
                System.out.println(event.getEventName() + " promoted by " + event.getPromoter() + " on " + event.getRawTopics());
        }catch(NoTopics e){
            System.out.println("No events on those topics.");
        }
    }
}