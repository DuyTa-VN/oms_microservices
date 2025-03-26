package vn.duyta.productservice.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestResponse<T>{
    private int statusCode;
    private String error;

    //message co the la String hoac ArrayList
    private Object message;
    private T data;
}
