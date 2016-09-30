package pl.langer.edu.restapi.api.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

/**
 * Created by DLanger on 2016-09-30.
 */
@JsonRootName("api_response")
public class BaseApiResponse {
    @JsonProperty("source_path")
    private String path;

    @JsonProperty("message")
    private String message;

    @JsonProperty("content")
    private Object content;

    public BaseApiResponse(String message, String path, Object content) {
        this.message = message;
        this.path = path;
        this.content = content;
    }

    public BaseApiResponse(String message, WebRequest request, Object content) {
        this(message, ((ServletWebRequest)request).getRequest().getRequestURI(), content);
    }

    public BaseApiResponse() {
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Object getContent() {
        return this.content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
