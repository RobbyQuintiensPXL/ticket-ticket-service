package be.jevents.ticketservice.repository;

import be.jevents.ticketservice.model.TicketUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketUserRepository extends JpaRepository<TicketUser, Long> {


}
