package soselab.mpg.testreader.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.util.List;

public class TestReport {
    @Id
    private String id;
    @CreatedDate
    private Long createdDate;
    private List<ProviderReport> testReports;

    public TestReport() {
    }

    public TestReport(List<ProviderReport> testReports) {
        this.testReports = testReports;
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
                '}';
    }
}
