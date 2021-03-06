package soselab.mpg.testreader.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by bernie on 2017/2/10.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceTestDetail {

    private MetaDataBean metaData;
    private ProviderBean provider;
    private List<ExecutionBean> execution;

    public MetaDataBean getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaDataBean metaData) {
        this.metaData = metaData;
    }

    public ProviderBean getProvider() {
        return provider;
    }

    public void setProvider(ProviderBean provider) {
        this.provider = provider;
    }

    public List<ExecutionBean> getExecution() {
        return execution;
    }

    public void setExecution(List<ExecutionBean> execution) {
        this.execution = execution;
    }

    @Override
    public String toString() {
        return "ServiceTestDetail{" +
                "metaData=" + metaData +
                ", provider=" + provider +
                ", execution=" + execution +
                '}';
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MetaDataBean {
        private String date;
        private String pactJvmVersion;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getPactJvmVersion() {
            return pactJvmVersion;
        }

        public void setPactJvmVersion(String pactJvmVersion) {
            this.pactJvmVersion = pactJvmVersion;
        }

        @Override
        public String toString() {
            return "MetaDataBean{" +
                    "date='" + date + '\'' +
                    ", pactJvmVersion='" + pactJvmVersion + '\'' +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProviderBean {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "ProviderBean{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ExecutionBean {
        private ConsumerBean consumer;
        private List<InteractionsBean> interactions;

        public ConsumerBean getConsumer() {
            return consumer;
        }

        public void setConsumer(ConsumerBean consumer) {
            this.consumer = consumer;
        }

        public List<InteractionsBean> getInteractions() {
            return interactions;
        }

        public void setInteractions(List<InteractionsBean> interactions) {
            this.interactions = interactions;
        }

        @Override
        public String toString() {
            return "ExecutionBean{" +
                    "consumer=" + consumer +
                    ", interactions=" + interactions +
                    '}';
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ConsumerBean {
            private String name;
            private SourceBean source;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public SourceBean getSource() {
                return source;
            }

            public void setSource(SourceBean source) {
                this.source = source;
            }

            @Override
            public String toString() {
                return "ConsumerBean{" +
                        "name='" + name + '\'' +
                        ", source=" + source +
                        '}';
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class SourceBean {
                private String url;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                @Override
                public String toString() {
                    return "SourceBean{" +
                            "url='" + url + '\'' +
                            '}';
                }
            }
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class InteractionsBean {
            private InteractionBean interaction;
            private VerificationBean verification;

            public InteractionBean getInteraction() {
                return interaction;
            }

            public void setInteraction(InteractionBean interaction) {
                this.interaction = interaction;
            }

            public VerificationBean getVerification() {
                return verification;
            }

            public void setVerification(VerificationBean verification) {
                this.verification = verification;
            }

            @Override
            public String toString() {
                return "InteractionsBean{" +
                        "interaction=" + interaction +
                        ", verification=" + verification +
                        '}';
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class InteractionBean {
                private String providerState;
                private ResponseBean response;
                private String description;
                private RequestBean request;

                public String getProviderState() {
                    return providerState;
                }

                public void setProviderState(String providerState) {
                    this.providerState = providerState;
                }

                public ResponseBean getResponse() {
                    return response;
                }

                public void setResponse(ResponseBean response) {
                    this.response = response;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public RequestBean getRequest() {
                    return request;
                }

                public void setRequest(RequestBean request) {
                    this.request = request;
                }

                @Override
                public String toString() {
                    return "InteractionBean{" +
                            "providerState='" + providerState + '\'' +
                            ", response=" + response +
                            ", description='" + description + '\'' +
                            ", request=" + request +
                            '}';
                }

                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class ResponseBean {
                    private String JSONREGEXP;
                    private BodyBean body;
                    private HeadersBean headers;
                    private String XMLREGEXP;
                    private int status;
                    private String HTMLREGEXP;
                    private String XMLREGEXP2;

                    public String getJSONREGEXP() {
                        return JSONREGEXP;
                    }

                    public void setJSONREGEXP(String JSONREGEXP) {
                        this.JSONREGEXP = JSONREGEXP;
                    }

                    public BodyBean getBody() {
                        return body;
                    }

                    public void setBody(BodyBean body) {
                        this.body = body;
                    }

                    public HeadersBean getHeaders() {
                        return headers;
                    }

                    public void setHeaders(HeadersBean headers) {
                        this.headers = headers;
                    }

                    public String getXMLREGEXP() {
                        return XMLREGEXP;
                    }

                    public void setXMLREGEXP(String XMLREGEXP) {
                        this.XMLREGEXP = XMLREGEXP;
                    }

                    public int getStatus() {
                        return status;
                    }

                    public void setStatus(int status) {
                        this.status = status;
                    }

                    public String getHTMLREGEXP() {
                        return HTMLREGEXP;
                    }

                    public void setHTMLREGEXP(String HTMLREGEXP) {
                        this.HTMLREGEXP = HTMLREGEXP;
                    }

                    public String getXMLREGEXP2() {
                        return XMLREGEXP2;
                    }

                    public void setXMLREGEXP2(String XMLREGEXP2) {
                        this.XMLREGEXP2 = XMLREGEXP2;
                    }

                    @Override
                    public String toString() {
                        return "ResponseBean{" +
                                "JSONREGEXP='" + JSONREGEXP + '\'' +
                                ", body=" + body +
                                ", headers=" + headers +
                                ", XMLREGEXP='" + XMLREGEXP + '\'' +
                                ", status=" + status +
                                ", HTMLREGEXP='" + HTMLREGEXP + '\'' +
                                ", XMLREGEXP2='" + XMLREGEXP2 + '\'' +
                                '}';
                    }

                    @JsonIgnoreProperties(ignoreUnknown = true)
                    public static class BodyBean {
                        private String value;
                        private boolean missing;
                        private boolean present;
                        private String state;
                        private boolean empty;

                        public String getValue() {
                            return value;
                        }

                        public void setValue(String value) {
                            this.value = value;
                        }

                        public boolean isMissing() {
                            return missing;
                        }

                        public void setMissing(boolean missing) {
                            this.missing = missing;
                        }

                        public boolean isPresent() {
                            return present;
                        }

                        public void setPresent(boolean present) {
                            this.present = present;
                        }

                        public String getState() {
                            return state;
                        }

                        public void setState(String state) {
                            this.state = state;
                        }

                        public boolean isEmpty() {
                            return empty;
                        }

                        public void setEmpty(boolean empty) {
                            this.empty = empty;
                        }

                        @Override
                        public String toString() {
                            return "BodyBean{" +
                                    "value='" + value + '\'' +
                                    ", missing=" + missing +
                                    ", present=" + present +
                                    ", state='" + state + '\'' +
                                    ", empty=" + empty +
                                    '}';
                        }
                    }

                    @JsonIgnoreProperties(ignoreUnknown = true)
                    public static class HeadersBean {
                        @JsonProperty("Content-Type")
                        private String ContentType;

                        public String getContentType() {
                            return ContentType;
                        }

                        public void setContentType(String ContentType) {
                            this.ContentType = ContentType;
                        }

                        @Override
                        public String toString() {
                            return "HeadersBean{" +
                                    "ContentType='" + ContentType + '\'' +
                                    '}';
                        }
                    }
                }

                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class RequestBean {
                    private Object query;
                    private String path;
                    private BodyBeanX body;
                    private HeadersBeanX headers;
                    private String method;

                    public Object getQuery() {
                        return query;
                    }

                    public void setQuery(Object query) {
                        this.query = query;
                    }

                    public String getPath() {
                        return path;
                    }

                    public void setPath(String path) {
                        this.path = path;
                    }

                    public BodyBeanX getBody() {
                        return body;
                    }

                    public void setBody(BodyBeanX body) {
                        this.body = body;
                    }

                    public HeadersBeanX getHeaders() {
                        return headers;
                    }

                    public void setHeaders(HeadersBeanX headers) {
                        this.headers = headers;
                    }

                    public String getMethod() {
                        return method;
                    }

                    public void setMethod(String method) {
                        this.method = method;
                    }

                    @Override
                    public String toString() {
                        return "RequestBean{" +
                                "query=" + query +
                                ", path='" + path + '\'' +
                                ", body=" + body +
                                ", headers=" + headers +
                                ", method='" + method + '\'' +
                                '}';
                    }

                    @JsonIgnoreProperties(ignoreUnknown = true)
                    public static class BodyBeanX {
                        private Object value;
                        private boolean missing;
                        private boolean present;
                        private String state;
                        private boolean empty;

                        public Object getValue() {
                            return value;
                        }

                        public void setValue(Object value) {
                            this.value = value;
                        }

                        public boolean isMissing() {
                            return missing;
                        }

                        public void setMissing(boolean missing) {
                            this.missing = missing;
                        }

                        public boolean isPresent() {
                            return present;
                        }

                        public void setPresent(boolean present) {
                            this.present = present;
                        }

                        public String getState() {
                            return state;
                        }

                        public void setState(String state) {
                            this.state = state;
                        }

                        public boolean isEmpty() {
                            return empty;
                        }

                        public void setEmpty(boolean empty) {
                            this.empty = empty;
                        }

                        @Override
                        public String toString() {
                            return "BodyBeanX{" +
                                    "value=" + value +
                                    ", missing=" + missing +
                                    ", present=" + present +
                                    ", state='" + state + '\'' +
                                    ", empty=" + empty +
                                    '}';
                        }
                    }

                    @JsonIgnoreProperties(ignoreUnknown = true)
                    public static class HeadersBeanX {
                        private String Accept;

                        public String getAccept() {
                            return Accept;
                        }

                        public void setAccept(String Accept) {
                            this.Accept = Accept;
                        }

                        @Override
                        public String toString() {
                            return "HeadersBeanX{" +
                                    "Accept='" + Accept + '\'' +
                                    '}';
                        }
                    }
                }
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class VerificationBean {
                private String result;
                private List<String> status;

                public String getResult() {
                    return result;
                }

                public void setResult(String result) {
                    this.result = result;
                }

                public List<String> getStatus() {
                    return status;
                }

                public void setStatus(List<String> status) {
                    this.status = status;
                }

                @Override
                public String toString() {
                    return "VerificationBean{" +
                            "result='" + result + '\'' +
                            ", status=" + status +
                            '}';
                }
            }
        }
    }
}
