package soselab.mpg.pact.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No pact config found")
public class NoPactConfigException extends RuntimeException {
}
