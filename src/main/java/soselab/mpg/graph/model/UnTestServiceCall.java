package soselab.mpg.graph.model;

import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
public class UnTestServiceCall {
    private String from;
    private String to;

    public UnTestServiceCall() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
