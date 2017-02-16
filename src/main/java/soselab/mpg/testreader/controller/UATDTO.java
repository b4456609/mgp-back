package soselab.mpg.testreader.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by bernie on 2017/2/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UATDTO {
    /**
     * line : 1
     * elements : [{"line":3,"name":"add pack","description":"","id":"add-pack;add-pack","type":"scenario","keyword":"Scenario","steps":[{"result":{"duration":247295911,"status":"passed"},"line":4,"name":"I am a  logged-in  user","match":{"location":"User.i_am_a_logged_in_user()"},"keyword":"Given "},{"result":{"duration":1683376215,"status":"passed"},"line":5,"name":"I add pack with following content","match":{"location":"AddPackTest.i_add_pack_with_following_content(AddPack>)"},"rows":[{"cells":["title","description","content"],"line":6},{"cells":["test","test descritpion","afwfawe"],"line":7}],"keyword":"When "},{"result":{"duration":46547,"status":"passed"},"line":9,"name":"the pack create","match":{"location":"AddPackTest.the_pack_create()"},"keyword":"Then "}],"tags":[{"line":2,"name":"@easylearn_pack_endpoint_/_POST"}]}]
     * name : add pack
     * description :
     * id : add-pack
     * keyword : Feature
     * uri : soselab/easylearn/addPack.feature
     */

    private int line;
    private String name;
    private String description;
    private String id;
    private String keyword;
    private String uri;
    private List<ElementsBean> elements;

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<ElementsBean> getElements() {
        return elements;
    }

    public void setElements(List<ElementsBean> elements) {
        this.elements = elements;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ElementsBean {
        /**
         * line : 3
         * name : add pack
         * description :
         * id : add-pack;add-pack
         * type : scenario
         * keyword : Scenario
         * steps : [{"result":{"duration":247295911,"status":"passed"},"line":4,"name":"I am a  logged-in  user","match":{"location":"User.i_am_a_logged_in_user()"},"keyword":"Given "},{"result":{"duration":1683376215,"status":"passed"},"line":5,"name":"I add pack with following content","match":{"location":"AddPackTest.i_add_pack_with_following_content(AddPack>)"},"rows":[{"cells":["title","description","content"],"line":6},{"cells":["test","test descritpion","afwfawe"],"line":7}],"keyword":"When "},{"result":{"duration":46547,"status":"passed"},"line":9,"name":"the pack create","match":{"location":"AddPackTest.the_pack_create()"},"keyword":"Then "}]
         * tags : [{"line":2,"name":"@easylearn_pack_endpoint_/_POST"}]
         */

        private int line;
        private String name;
        private String description;
        private String id;
        private String type;
        private String keyword;
        private List<StepsBean> steps;
        private List<TagsBean> tags;

        public int getLine() {
            return line;
        }

        public void setLine(int line) {
            this.line = line;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public List<StepsBean> getSteps() {
            return steps;
        }

        public void setSteps(List<StepsBean> steps) {
            this.steps = steps;
        }

        public List<TagsBean> getTags() {
            return tags;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class StepsBean {
            /**
             * result : {"duration":247295911,"status":"passed"}
             * line : 4
             * name : I am a  logged-in  user
             * match : {"location":"User.i_am_a_logged_in_user()"}
             * keyword : Given
             * rows : [{"cells":["title","description","content"],"line":6},{"cells":["test","test descritpion","afwfawe"],"line":7}]
             */

            private ResultBean result;
            private int line;
            private String name;
            private MatchBean match;
            private String keyword;
            private List<RowsBean> rows;

            public ResultBean getResult() {
                return result;
            }

            public void setResult(ResultBean result) {
                this.result = result;
            }

            public int getLine() {
                return line;
            }

            public void setLine(int line) {
                this.line = line;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public MatchBean getMatch() {
                return match;
            }

            public void setMatch(MatchBean match) {
                this.match = match;
            }

            public String getKeyword() {
                return keyword;
            }

            public void setKeyword(String keyword) {
                this.keyword = keyword;
            }

            public List<RowsBean> getRows() {
                return rows;
            }

            public void setRows(List<RowsBean> rows) {
                this.rows = rows;
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class ResultBean {
                /**
                 * duration : 247295911
                 * status : passed
                 */

                private int duration;
                private String status;

                public int getDuration() {
                    return duration;
                }

                public void setDuration(int duration) {
                    this.duration = duration;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class MatchBean {
                /**
                 * location : User.i_am_a_logged_in_user()
                 */

                private String location;

                public String getLocation() {
                    return location;
                }

                public void setLocation(String location) {
                    this.location = location;
                }
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class RowsBean {
                /**
                 * cells : ["title","description","content"]
                 * line : 6
                 */

                private int line;
                private List<String> cells;

                public int getLine() {
                    return line;
                }

                public void setLine(int line) {
                    this.line = line;
                }

                public List<String> getCells() {
                    return cells;
                }

                public void setCells(List<String> cells) {
                    this.cells = cells;
                }
            }
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class TagsBean {
            /**
             * line : 2
             * name : @easylearn_pack_endpoint_/_POST
             */

            private int line;
            private String name;

            public int getLine() {
                return line;
            }

            public void setLine(int line) {
                this.line = line;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
