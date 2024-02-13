package org.helpdesk;

import com.opencsv.CSVReader;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;

public class TicketStorage {

    private static final String filePath = "./db.csv";
public TicketStorage(){

}

public void addTicket(Ticket ticket){
    try( FileWriter writer = new FileWriter(filePath, true))
    {
        writer.write(ticket.getUuid().toString() + "," + ticket.getEmail() + "," + ticket.getDescription() + "," + ticket.getTaskUrgency());
        writer.write('\n');
    }
    catch(Exception ex){
        System.out.println(ex.getMessage());
    }
}

    public static ArrayList<Ticket> getALLTickets() {
        ArrayList<Ticket> allTickets = new ArrayList<>();
        try (FileReader filereader = new FileReader(filePath)) {

            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {
                Ticket ticket = new Ticket(
                        UUID.fromString(nextRecord[0]),
                        nextRecord[1],
                        nextRecord[2],
                        nextRecord[3]);
                allTickets.add(ticket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allTickets;
    }

    public void updateTicket(Ticket updatedTicket){

    ArrayList<Ticket> allTickets = getALLTickets();
    try(FileWriter writer = new FileWriter(filePath, false))
        {
            for (Ticket ticket : allTickets) {
                if (ticket.getUuid().equals(updatedTicket.getUuid())){
                    int index = allTickets.indexOf(ticket);
                    allTickets.set(index, updatedTicket);
                }
            }

            for (Ticket ticket : allTickets) {
                this.addTicket(ticket);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch(Exception ex){
            System.out.println(ex.getMessage());

    }
    }

    public void deleteTicket (UUID deletedID){
        ArrayList<Ticket> allTickets = getALLTickets();
        try(FileWriter writer = new FileWriter(filePath, false))
        {
            allTickets.removeIf(ticket -> ticket.getUuid().equals(deletedID));

            for (Ticket ticket : allTickets) {
                this.addTicket(ticket);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch(Exception ex){
            System.out.println(ex.getMessage());

        }
    }



}
