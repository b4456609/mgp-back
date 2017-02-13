package soselab.mpg.testreader.controller;

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
     * report : [{"consumer":"a","provider":"b","error":2,"report":""}]
     */

    private String timestamp;
    private String type;
    private String visual;
    private List<ReportBean> report;

    public ReportDTO() {
    }

    public ReportDTO(String timestamp, String type, String visual, List<ReportBean> report) {
        this.timestamp = timestamp;
        this.type = type;
        this.visual = visual;
        this.report = report;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
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
        private String provider;
        private long error;
        private String report;

        public ReportBean() {
        }

        public ReportBean(String provider, long error, String report) {
            this.provider = provider;
            this.error = error;
            this.report = report;
        }

        public String getProvider() {
            return provider;
        }

        public void setProvider(String provider) {
            this.provider = provider;
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

        @Override
        public String toString() {
            return "ReportBean{" +
                    ", provider='" + provider + '\'' +
                    ", error=" + error +
                    ", report='" + report + '\'' +
                    '}';
        }
    }
}
