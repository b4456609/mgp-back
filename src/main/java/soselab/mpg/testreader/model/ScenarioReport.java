package soselab.mpg.testreader.model;

/**
 * Created by bernie on 2017/2/16.
 */
public class ScenarioReport implements DetailReport {
    private String scenarioName;
    private long failCount;

    public ScenarioReport() {
    }

    public ScenarioReport(String scenarioName, long failCount) {
        this.scenarioName = scenarioName;
        this.failCount = failCount;
    }

    public String getScenarioName() {
        return scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    @Override
    public String getName() {
        return this.getScenarioName();
    }

    @Override
    public long getFailCount() {
        return 0;
    }

    public void setFailCount(long failCount) {
        this.failCount = failCount;
    }

    @Override
    public String getReport() {
        return "";
    }

    @Override
    public String toString() {
        return "ScenarioReport{" +
                "scenarioName='" + scenarioName + '\'' +
                ", failCount=" + failCount +
                '}';
    }
}
