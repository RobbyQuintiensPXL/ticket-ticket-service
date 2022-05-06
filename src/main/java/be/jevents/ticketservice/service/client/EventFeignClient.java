package be.jevents.ticketservice.service.client;

import be.jevents.ticketservice.model.Event;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("event-service:8081")
public interface EventFeignClient {

    @RequestMapping(
      method = RequestMethod.GET,
            value = "/events/{eventId}",
            consumes = "application/json")
    Event getEvent(@PathVariable("eventId") Long eventId);

}
