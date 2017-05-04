package soselab.mpg.testreader.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by bernie on 2/13/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportDTO {
    /**
     * timestamp :
     * type : service
     * visual :
     * report : [{"consumer":"a","name":"b","error":2,"report":""}]
     */

    private long timestamp;
    private String type;
    private String visual;
    private List<ReportBean> report;

    public ReportDTO() {
    }

    public ReportDTO(long timestamp, String type, String visual, List<ReportBean> report) {
        this.timestamp = timestamp;
        this.type = type;
        this.visual = visual;
        this.report = report;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVisual() {
        return visual;
    }

    public void setVisual(String visual) {
        this.visual = visual;
    }

    public List<ReportBean> getReport() {
        return report;
    }

    public void setReport(List<ReportBean> report) {
        this.report = report;
    }

    @Override
    public String toString() {
        return "ReportDTO{" +
                "timestamp='" + timestamp + '\'' +
                ", type='" + type + '\'' +
                ", visual='" + visual + '\'' +
                ", report=" + report +
                '}';
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ReportBean {
        private String name;
        private long error;
        private String report;
        private int runNumber;

        public ReportBean() {
        }

        public ReportBean(String name, long error, String report, int runNumber) {
            this.name = name;
            this.error = error;
            this.report = report;
            this.runNumber = runNumber;
        }

        @Override
        public String toString() {
            return "ReportBean{" +
                    "name='" + name + '\'' +
                    ", error=" + error +
                    ", report='" + report + '\'' +
                    ", runNumber=" + runNumber +
                    '}';
        }

        public int getRunNumber() {
            return runNumber;
        }

        public void setRunNumber(int runNumber) {
            this.runNumber = runNumber;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getError() {
            return error;
        }

        public void setError(long error) {
            this.error = error;
        }

        public String getReport() {
            return report;
        }

        public void setReport(String report) {
            this.report = report;
        }

    }
}
