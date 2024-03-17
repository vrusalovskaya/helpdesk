package org.helpdesk;

import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ITicketStorage {
    void addTicket(Ticket ticket) throws Exception;
    Ticket getTicket(UUID id) throws Exception;
    List<Ticket> getTickets() throws Exception;
    void updateTicket(Ticket updatedTicket) throws Exception;
    void deleteTicket(UUID deletedID) throws Exception;

}
