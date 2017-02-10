package soselab.mpg.testreader.model;

/**
 * Created by Fan on 2017/2/10.
 */
public class ProviderReport {
    private String serviceName;
    private ServiceTestDetail serviceTestDetail;
    private String markdown;

    public ProviderReport(String serviceName, ServiceTestDetail serviceTestDetail, String markdown) {
        this.serviceName = serviceName;
        this.serviceTestDetail = serviceTestDetail;
        this.markdown = markdown;
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
