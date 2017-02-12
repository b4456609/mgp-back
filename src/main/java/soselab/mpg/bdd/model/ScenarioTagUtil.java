package soselab.mpg.bdd.model;

public class ScenarioTagUtil {
    /**
     * Translate the scenario endpoint id annotation to Endpoint id
     * Example: easylearn_note_endpoint_/_POST -> easylearn_note endpoint / POST
     *
     * @param tag scenario's tag
     * @return endpoint id format
     */
    public static String translateToEndpointId(String tag) {
        StringBuilder tagStringBuilder = new StringBuilder(tag);
        for (int j = 0; j < 3; j++) {
            int i = tagStringBuilder.lastIndexOf("_");
            tagStringBuilder.setCharAt(i, ' ');
        }
        return tagStringBuilder.toString();
    }
}
