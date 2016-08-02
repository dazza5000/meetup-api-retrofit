package zeroturnaround.org.jrebel4androidgettingstarted;

/**
 * Created by daz on 3/26/16.
 */
public class Event {
    private String name;
    private String description;
    private String event_url;
    private String id;
    private String time;


    @Override
    public String toString() {
        return name;
    }

    public String getEvent_url() {
        return event_url;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }
}
