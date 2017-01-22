package soselab.mpg.mpd.model;

/**
 * Created by bernie on 1/9/17.
 */
public class ServiceCall {
    private String id;

    private String path;

    private String method;

    private String provider;

    public ServiceCall() {
    }

    public ServiceCall(String id, String path, String method, String provider) {
        this.id = id;
        this.path = path;
        this.method = method;
        this.provider = provider;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getPath ()
    {
        return path;
    }

    public void setPath (String path)
    {
        this.path = path;
    }

    public String getMethod ()
    {
        return method;
    }

    public void setMethod (String method)
    {
        this.method = method;
    }

    public String getProvider ()
    {
        return provider;
    }

    public void setProvider (String provider)
    {
        this.provider = provider;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", path = "+path+", method = "+method+", provider = "+provider+"]";
    }
}
