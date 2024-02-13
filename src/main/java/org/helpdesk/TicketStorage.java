package org.helpdesk;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class TicketStorage {

    private final String filePath;

    public TicketStorage(String filePath) {
        this.filePath = filePath;
    }

    public void addTicket(Ticket ticket) throws IOException {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(ticket.getUuid().toString() + "," + ticket.getEmail() + "," + ticket.getDescription() + "," + ticket.getTaskUrgency());
            writer.write('\n');
        }
    }

    public List<Ticket> getTickets() throws IOException, CsvValidationException {
        ArrayList<Ticket> tickets = new ArrayList<>();
        try (
                FileReader filereader = new FileReader(filePath);
                CSVReader csvReader = new CSVReader(filereader)
        ) {
            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {
                Ticket ticket = new Ticket(
                        UUID.fromString(nextRecord[0]),
                        nextRecord[1],
                        nextRecord[2],
                        UrgencyType.valueOf(nextRecord[3]));
                tickets.add(ticket);
            }
        }
        return tickets;
    }

    public void updateTicket(Ticket updatedTicket) throws CsvValidationException, IOException {
        var tickets = getTickets();

        clearDatabase();

        for (Ticket ticket : tickets) {
            if (ticket.getUuid().equals(updatedTicket.getUuid())) {
                this.addTicket(updatedTicket);
            } else {
                this.addTicket(ticket);
            }
        }
    }

    public void deleteTicket(UUID deletedID) throws CsvValidationException, IOException {
        var tickets = getTickets();

        clearDatabase();

        tickets.removeIf(ticket -> ticket.getUuid().equals(deletedID));

        for (Ticket ticket : tickets) {
            this.addTicket(ticket);
        }
    }

    private void clearDatabase() throws IOException {
        try (FileWriter writer = new FileWriter(filePath, false)) {
        }
    }

}
