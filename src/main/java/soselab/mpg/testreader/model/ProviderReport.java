package soselab.mpg.testreader.model;

/**
 * Created by Fan on 2017/2/10.
 */
public class ProviderReport implements DetailReport {
    private String serviceName;
    private ServiceTestDetail serviceTestDetail;
    private String markdown;
    private long failCount;
    private int runNumber;

    public ProviderReport() {
    }

    public ProviderReport(String serviceName, ServiceTestDetail serviceTestDetail, String markdown, long failCount,
                          int runNumber) {
        this.serviceName = serviceName;
        this.serviceTestDetail = serviceTestDetail;
        this.markdown = markdown;
        this.failCount = failCount;
        this.runNumber = runNumber;
    }

    @Override
    public String getName() {
        return this.getServiceName();
    }

    @Override
    public long getFailCount() {
        return failCount;
    }

    public void setFailCount(long failCount) {
        this.failCount = failCount;
    }

    @Override
    public String getReport() {
        return this.getMarkdown();
    }

    @Override
    public int getRunNumber() {
        return runNumber;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public ServiceTestDetail getServiceTestDetail() {
        return serviceTestDetail;
    }

    public void setServiceTestDetail(ServiceTestDetail serviceTestDetail) {
        this.serviceTestDetail = serviceTestDetail;
    }

    public String getMarkdown() {
        return markdown;
    }

    public void setMarkdown(String markdown) {
        this.markdown = markdown;
    }

    @Override
    public String toString() {
        return "ProviderReport{" +
                "serviceName='" + serviceName + '\'' +
                ", serviceTestDetail=" + serviceTestDetail +
                ", markdown='" + markdown + '\'' +
                '}';
    }
}
