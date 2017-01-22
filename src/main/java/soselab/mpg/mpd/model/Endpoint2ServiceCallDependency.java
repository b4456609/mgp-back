package soselab.mpg.mpd.model;

/**
 * Created by bernie on 1/9/17.
 */
public class Endpoint2ServiceCallDependency {
    private String to;

    private String from;

    public Endpoint2ServiceCallDependency() {
    }

    public Endpoint2ServiceCallDependency(String from, String to) {
        this.to = to;
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String toString() {
        return "Endpoint2ServiceCallDependency{" +
                "to='" + to + '\'' +
                ", from='" + from + '\'' +
                '}';
    }
}
