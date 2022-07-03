package org.airbnb.com.main.customer;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * @author sandeep.rana
 */
@Getter
public class ResponseDto implements Serializable {

    private HttpStatus status;

    private String message;

    private Object data;


    public ResponseDto setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public ResponseDto setMessage(String message) {
        this.message = message;
        return this;
    }

    public ResponseDto setData(Object data) {
        this.data = data;
        return this;
    }
}
