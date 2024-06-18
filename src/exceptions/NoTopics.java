package exceptions;

public class NoTopics extends Exception{
    private static final String NO_TOPICS = "No events on those topics.\n";

    public NoTopics(){super(NO_TOPICS);}
}
