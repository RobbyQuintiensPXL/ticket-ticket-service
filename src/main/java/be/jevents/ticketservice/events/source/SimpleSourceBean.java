package be.jevents.ticketservice.events.source;

import be.jevents.ticketservice.events.model.TicketChangeModel;
import be.jevents.ticketservice.model.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class SimpleSourceBean {

//    private Source source;
//
//    private final static Logger LOGGER = LoggerFactory.getLogger(SimpleSourceBean.class);
//
//    public SimpleSourceBean(Source source){
//        this.source = source;
//    }
//
//    public void publishTicketChange(String action,Long ticketId){
//        action = "payment";
//        LOGGER.debug("Sending Kafka message for Ticket id: {}",
//                ticketId);
//        TicketChangeModel change = new TicketChangeModel(
//                TicketChangeModel.class.getTypeName(),
//                action,
//                ticketId
//        );
//
//        source.output().send(MessageBuilder
//                .withPayload(change)
//                .build());
//    }
//
//

}
