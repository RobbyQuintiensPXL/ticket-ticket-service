package be.jevents.ticketservice.service;

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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class TicketService {

    private final Logger LOGGER = LoggerFactory.getLogger(TicketService.class);
    private final KafkaProducer kafkaProducer;
    private final TicketRepository ticketRepository;
    private final TicketUserRepository ticketUserRepository;
    private final EventFeignClient feignClient;

    public TicketService(TicketRepository ticketRepository,
                         EventFeignClient feignClient, TicketUserRepository ticketUserRepository,
                         KafkaProducer kafkaProducer) {
        this.ticketRepository = ticketRepository;
        this.feignClient = feignClient;
        this.ticketUserRepository = ticketUserRepository;
        this.kafkaProducer = kafkaProducer;
    }

    public Event getEventInfo(Long eventId) {
        return feignClient.getEvent(eventId);
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

        Optional<TicketUser> foundUser = ticketUserRepository.findByEmail(ticketResource.getEmail());
        LOGGER.info(foundUser.toString());
        TicketUser ticketUser = new TicketUser();
        if (foundUser.isEmpty()) {
            ticketUser.setName(ticketResource.getName());
            ticketUser.setFirstName(ticketResource.getFirstName());
            ticketUser.setStreet(ticketResource.getStreet());
            ticketUser.setZipCode(ticketResource.getZipCode());
            ticketUser.setCity(ticketResource.getCity());
            ticketUser.setCountry(ticketResource.getCountry());
            ticketUser.setEmail(ticketResource.getEmail());
            ticketUserRepository.save(ticketUser);
        } else {
            ticketUser = foundUser.get();
        }

        Event foundEvent = getEventInfo(ticketResource.getEventId());

        for (int i = 0; i < ticketResource.getNumberOfTickets(); i++) {
            Ticket ticket = new Ticket();
            ticket.setEventId(ticketResource.getEventId());
            ticket.setStatus("PAYED");
            ticket.setTicketUser(ticketUser);
            ticket.setUsername(username);
            ticket.setTicketNumber(getLatestTicketNumberFromEvent(foundEvent.getId()));
            ticketRepository.save(ticket);
        }

        LOGGER.info("Ticket(s) created");

        TicketEvent ticketEvent = createTicketEvent(ticketResource.getNumberOfTickets(), getLatestTicketNumberFromEvent(foundEvent.getId()) - ticketResource.getNumberOfTickets(), foundEvent, ticketUser);
        kafkaProducer.send(ticketEvent);
        LOGGER.info("TicketEvent sended to mailservice");

    }

    private int getLatestTicketNumberFromEvent(Long eventId) {
        long count = ticketRepository.findTicketsByEventId(eventId).size();
        int number = 0;
        if (count == 0) {
            return 1;
        }
        Optional<Integer> ticket = ticketRepository.findTicketsByEventId(eventId).stream().map(Ticket::getTicketNumber).skip(count - 1).findFirst();
        if (ticket.isPresent()) {
            number = ticket.get() + 1;
        }
        return number;
    }

    public TicketEvent createTicketEvent(int amount, int ticketId, Event event, TicketUser ticketUser) {
        return new TicketEvent(amount, ticketId, event, ticketUser);
    }

    public void validateTicket(int ticketNumber, Long eventId, Long ticketUser) {
        Optional<Ticket> foundTicket = ticketRepository.findTicketByTicketNumberAndEventIdAndTicketUser_Id(ticketNumber, eventId, ticketUser);

        if (foundTicket.isEmpty()) {
            throw new TicketException("No ticket found for id:" + ticketNumber + eventId + ticketUser);
        }

        if (foundTicket.get().isValidated()) {
            throw new TicketException("Ticket is already validated");
        }

        foundTicket.get().setValidated(true);
        LOGGER.info("Ticket: " + foundTicket.get().getUsername() + foundTicket.get().getEventId() + String.valueOf(foundTicket.get().getTicketNumber()) + " Validated");
        ticketRepository.save(foundTicket.get());
    }
}