package soselab.mpg.graph.controller.dto;

public class NodesItemBuilder {
    private String id;
    private String label;
    private String className;
    private Integer group;

    public NodesItemBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public NodesItemBuilder setLabel(String label) {
        this.label = label;
        return this;
    }

    public NodesItemBuilder setClassName(String className) {
        this.className = className;
        return this;
    }

    public NodesItemBuilder setGroup(Integer group) {
        this.group = group;
        return this;
    }

    public NodesItem createNodesItem() {
        return new NodesItem(id, label, className, group);
    }
}