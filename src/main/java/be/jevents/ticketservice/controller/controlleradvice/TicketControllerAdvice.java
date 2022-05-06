package be.jevents.ticketservice.controller.controlleradvice;

import be.jevents.ticketservice.exception.TicketException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TicketControllerAdvice {

    @ResponseBody
    @ExceptionHandler(TicketException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String notFoundHandler(TicketException ex) {
        return ex.getMessage();
    }
}
