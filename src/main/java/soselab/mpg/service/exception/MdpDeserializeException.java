package soselab.mpg.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by bernie on 1/11/17.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MdpDeserializeException extends RuntimeException {
}
