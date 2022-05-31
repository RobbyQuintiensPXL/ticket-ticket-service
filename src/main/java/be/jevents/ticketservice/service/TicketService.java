package be.jevents.ticketservice.service;

import be.jevents.ticketservice.config.PDFGenerator;
import be.jevents.ticketservice.createresource.CreateFullTicketResource;
import be.jevents.ticketservice.dto.TicketDTO;
import be.jevents.ticketservice.events.TicketEvent;
import be.jevents.ticketservice.exception.TicketException;
import be.jevents.ticketservice.model.Event;
import be.jevents.ticketservice.model.Ticket;
import be.jevents.ticketservice.model.TicketUser;
import be.jevents.ticketservice.repository.TicketRepository;
import be.jevents.ticketservice.repository.TicketUserRepository;
import be.jevents.ticketservice.service.client.EventFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class TicketService {

    private final Logger LOGGER = LoggerFactory.getLogger(TicketService.class);

    @Autowired
    private KafkaTemplate<String, TicketEvent> kafkaTemplate;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private PDFGenerator pdfGenerator;

    private final TicketRepository ticketRepository;

    private final TicketUserRepository ticketUserRepository;

    private final EventFeignClient feignClient;

    public TicketService(TicketRepository ticketRepository,
                         EventFeignClient feignClient, TicketUserRepository ticketUserRepository) {
        this.ticketRepository = ticketRepository;
        this.feignClient = feignClient;
        this.ticketUserRepository = ticketUserRepository;
    }

    public Event getEventInfo(Long eventId) {
        Event event = feignClient.getEvent(eventId);
        return event;
    }

    public TicketDTO getTicketInfo(Long ticketId) {
        Optional<Ticket> foundTicket = ticketRepository.findById(ticketId);
        if (foundTicket.isEmpty()) {
            throw new TicketException("Ticket not found");
        }

        Long id = foundTicket.get().getId();
        Event event = feignClient.getEvent(id);
        foundTicket.get().setEvent(event);
        return foundTicket.map(TicketDTO::new).orElse(null);
    }

    public int getSoldTicketsAmountForEvent(Long eventId) {
        List<TicketDTO> ticketList = ticketRepository.findTicketsByEventId(eventId).stream().map(TicketDTO::new).collect(Collectors.toList());
        if (ticketList.isEmpty()) {
            return 0;
        }
        return ticketList.size();
    }

    public List<TicketDTO> getEventsByUser(String username) {
        List<TicketDTO> ticketList = ticketRepository.findTicketByUsername(username).stream().map(TicketDTO::new).collect(Collectors.toList());
        if (ticketList.isEmpty()) {
            throw new TicketException("No tickets found");
        }
        return ticketList;
    }

    public void createTicket(CreateFullTicketResource ticketResource,
                             String username) {
        TicketUser ticketUser = new TicketUser();
        ticketUser.setName(ticketResource.getName());
        ticketUser.setFirstName(ticketResource.getFirstName());
        ticketUser.setStreet(ticketResource.getStreet());
        ticketUser.setZipCode(ticketResource.getZipCode());
        ticketUser.setCity(ticketResource.getCity());
        ticketUser.setCountry(ticketResource.getCountry());
        ticketUser.setEmail(ticketResource.getEmail());
        ticketUser.setNumberOfTickets(ticketResource.getNumberOfTickets());

        ticketUserRepository.save(ticketUser);

        Ticket ticket = new Ticket();
        ticket.setEventId(ticketResource.getEventId());
        ticket.setStatus("PAYED");
        ticket.setTicketUser(ticketUser);
        ticket.setUsername(username);

        ticketRepository.save(ticket);
        LOGGER.info("Ticket created");

        Event foundEvent = getEventInfo(ticketResource.getEventId());

        TicketEvent ticketEvent = createTicketEvent(ticket, foundEvent, ticketUser);
        kafkaProducer.send(ticketEvent);
        LOGGER.info("TicketEvent sended to mailservice");

/*        pdfGenerator.generatePDF("TEST");*/
    }

    public TicketEvent createTicketEvent(Ticket ticket, Event event, TicketUser ticketUser){
        return new TicketEvent(ticket, event, ticketUser);
    }
}
