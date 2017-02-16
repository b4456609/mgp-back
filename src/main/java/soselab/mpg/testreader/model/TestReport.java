package soselab.mpg.testreader.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class TestReport {
    @Id
    private String id;
    @CreatedDate
    private Long createdDate;
    private String type;
    private List<DetailReport> testReports;
    private String visualData;
    private List<String> rawReports;

    public TestReport() {
    }

    public TestReport(String type, List<DetailReport> testReports, String visualData, List<String> rawReports) {
        this.type = type;
        this.testReports = testReports;
        this.visualData = visualData;
        this.rawReports = rawReports;
    }

    public List<String> getRawReports() {
        return rawReports;
    }

    public void setRawReports(List<String> rawReports) {
        this.rawReports = rawReports;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVisualData() {
        return visualData;
    }

    public void setVisualData(String visualData) {
        this.visualData = visualData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public List<DetailReport> getTestReports() {
        return testReports;
    }

    public void setTestReports(List<DetailReport> testReports) {
        this.testReports = testReports;
    }

    @Override
    public String toString() {
        return "TestReport{" +
                "id='" + id + '\'' +
                ", createdDate=" + createdDate +
                ", testReports=" + testReports +
                ", visualData='" + visualData + '\'' +
                '}';
    }
}
