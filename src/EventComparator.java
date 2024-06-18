import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class EventComparator implements Comparator<Event>{

    private ArrayList<String> splited;

    public EventComparator(String topics){
        String [] splitTopics = topics.split(" ");
        this.splited = new ArrayList<>(Arrays.asList(splitTopics));
    }

    public int compare(Event e1, Event e2){
        int returned;
        int commonTopics1 = commonTopics(e1);
        int commonTopics2 = commonTopics(e2);
        if(commonTopics1 > commonTopics2)
            returned =  -1;
        else if(commonTopics1 < commonTopics2)
            returned = 1;
        else {
            returned = e1.getEventName().compareTo(e2.getEventName());
            if(returned == 0)
                returned = e1.getPromoter().compareTo(e2.getPromoter());
        }
        return returned;
    }

    private int commonTopics(Event event){
        int counter = 0;
        Iterator<String> it = event.getTopics();
        while (it.hasNext()){
            String str = it.next();
            if(splited.contains(str))
                counter++;
        }
        return counter;
    }
}
