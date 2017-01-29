package soselab.mpg.bdd.client.dto;

/**
 * Created by bernie on 2017/1/29.
 */
public class LatestCommitStatusDTO {
    private String id;
    private String msg;

    public LatestCommitStatusDTO() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "LatestCommitStatusDTO{" +
                "id='" + id + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
