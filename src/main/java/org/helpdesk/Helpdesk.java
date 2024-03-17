package org.helpdesk;

import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Helpdesk {

    private final ITicketStorage storage;

    public Helpdesk(ITicketStorage storage) {
        this.storage = storage;
    }

    public Ticket createTicket(String email, String description, UrgencyType taskUrgencyEnum) throws Exception {
        Ticket ticket = new Ticket(email, description, taskUrgencyEnum);
        storage.addTicket(ticket);
        return ticket;
    }

    public Ticket getTicket (UUID id) throws Exception {
        return storage.getTicket(id);
    }

    public List<Ticket> getTickets() throws Exception{
        return storage.getTickets();
    }

    public List<Ticket> getTicketsByEmail (String email) throws Exception {
        List<Ticket> tickets = new ArrayList<>();

        for (Ticket ticket : storage.getTickets()) {
            if (email.equals(ticket.getEmail())) {
                tickets.add(ticket);
            }
        }

        return tickets;
    }

    public void updateTicket(Ticket ticket) throws Exception{
        storage.updateTicket(ticket);
    }

    public void deleteTicket(UUID id) throws Exception{
        storage.deleteTicket(id);
    }
}
