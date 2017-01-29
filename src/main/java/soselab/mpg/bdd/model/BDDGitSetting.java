package soselab.mpg.bdd.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class BDDGitSetting {
    @Id
    private ObjectId id;
    private String url;
    private String commitId;
    private String commitMessage;

    public BDDGitSetting(String url, String commitId, String commitMessage) {
        this.commitId = commitId;
        this.commitMessage = commitMessage;
        id = new ObjectId();
        this.url = url;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public String getCommitMessage() {
        return commitMessage;
    }

    public void setCommitMessage(String commitMessage) {
        this.commitMessage = commitMessage;
    }
}
