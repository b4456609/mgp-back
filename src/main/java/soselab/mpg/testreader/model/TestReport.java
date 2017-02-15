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
    private List<ProviderReport> testReports;
    private String visualData;

    public TestReport() {
    }

    public TestReport(List<ProviderReport> testReports, String visualData) {
        this.testReports = testReports;
        this.visualData = visualData;
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

    public List<ProviderReport> getTestReports() {
        return testReports;
    }

    public void setTestReports(List<ProviderReport> testReports) {
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
