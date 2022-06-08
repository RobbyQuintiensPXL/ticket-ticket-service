package be.jevents.ticketservice.repository;

import be.jevents.ticketservice.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findTicketsByEventId(Long eventId);

    List<Ticket> findTicketByUsername(String username);

    Optional<Ticket> findTicketByTicketNumberAndEventIdAndTicketUser_Id(int ticketNumber, Long eventId, Long ticketUser);

}
