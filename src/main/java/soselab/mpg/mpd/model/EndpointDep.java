package soselab.mpg.mpd.model;

/**
 * Created by bernie on 1/9/17.
 */
public class EndpointDep {
    private String to;

    private String from;

    public EndpointDep() {
    }

    public EndpointDep(String from, String to) {
        this.to = to;
        this.from = from;
    }

    public String getTo ()
    {
        return to;
    }

    public void setTo (String to)
    {
        this.to = to;
    }

    public String getFrom ()
    {
        return from;
    }

    public void setFrom (String from)
    {
        this.from = from;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [to = "+to+", from = "+from+"]";
    }
}
