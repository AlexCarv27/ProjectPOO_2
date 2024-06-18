public enum Help {
    REGISTER("registers a new account"),
    ACCOUNTS("lists all registered accounts"),
    CREATE("creates a new event"),
    EVENTS("lists all events of an account"),
    INVITE("invites an user to an event"),
    RESPONSE("response to an invitation"),
    EVENT("shows detailed information of an event"),
    TOPICS("shows all events that cover a list of topics"),
    HELP("shows the available commands"),
    EXIT("terminates the execution of the program"),
    UNKNOWN("Unknown command %s. Type help to see available commands.\n");
    final String description;

    Help(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}


