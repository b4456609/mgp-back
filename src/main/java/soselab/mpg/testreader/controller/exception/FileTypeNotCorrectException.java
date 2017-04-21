package soselab.mpg.testreader.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "file type is not json or md")
public class FileTypeNotCorrectException extends RuntimeException {
}
