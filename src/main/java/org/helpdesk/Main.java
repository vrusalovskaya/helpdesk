package org.helpdesk;

import java.util.*;

public class Main {
    public static void main (String[] args) throws Exception {

        HashMap <UUID, Ticket> ticketStorage = new HashMap<>();

        TicketStorage storage = new TicketStorage();
        ArrayList<Ticket> allTickets = storage.getALLTickets();
        for (Ticket ticket : allTickets) {
            ticketStorage.put(ticket.getUuid(), ticket);
        }


        try (Scanner in = new Scanner(System.in)) {
            boolean keepGoing = true;
            while (keepGoing) {

                System.out.println("""
                        Hello! How can we help you today? Kindly specify the number of the needed action
                         1. Create the ticket
                         2. Show ticket by it's ID\s
                         3. Show all tickets registered under specific email
                         4. Update ticket by ID
                         5. Delete ticket by ID
                         6. Print all tickets""");

                String command = in.nextLine();

                while(!command.matches("[123456]")){
                    System.out.println("The command was not correctly specified. Please enter a number from 1 to 5");
                    command = in.nextLine();
                }


                switch (command) {
                    case "1":
                        System.out.print("We are sorry to hear that you have faced some issues. Kindly enter your email for ticket creation: ");
                        String email = in.nextLine();
                        while(!isValidEmailAddress(email)){
                            System.out.println("The email address is not correct. Please specify valid email");
                            email = in.nextLine();
                        }

                        System.out.print("Thanks! Please specify the issue you have faced (max 255 digits): ");
                        String description = in.nextLine();
                        while(description.length()>255){
                            System.out.println("The limit was exceeded. Please use no more than 255 digits to specify the description of the issue");
                            description = in.nextLine();
                        }

                        System.out.print("Specify the urgency of the issue (low/medium/high) or press \"enter\" if you would like to skip this step");
                        String taskUrgency = in.nextLine();
                        while(!isValidTaskUrgency(taskUrgency)){
                            System.out.println("The urgency of the ticket was not defined correctly. Kindly specify one of required values (low/medium/high) or press \"enter\"");
                            taskUrgency = in.nextLine();
                        }

                        if (Objects.equals(taskUrgency, "")) {
                            taskUrgency = "low";
                        }

                        Ticket ticket = new Ticket(email, description, taskUrgency);
                        ticketStorage.put(ticket.getUuid(), ticket);
                        storage.addTicket(ticket);

                        System.out.println("Your ticket was successfully created. You can track it by the following number: " + ticket.getUuid());
                        break;

                    case "2":
                        System.out.println("Enter the ID of the ticket you would like to review:");
                        UUID reviewedID = UUID.fromString(in.nextLine());

                        Ticket reviewedTicket = ticketStorage.get(reviewedID);
                        printTicket(reviewedTicket);

                        break;

                    case "3":
                        System.out.println("Enter the email:");
                        String reviewedEmail = in.nextLine();

                        int numberOfMatches = 0;

                        for (Ticket ticket3 : ticketStorage.values()) {
                            if (reviewedEmail.equals(ticket3.getEmail())) {
                                printTicket(ticket3);
                                ++numberOfMatches;
                            }
                        }
                        if (numberOfMatches == 0) {
                            System.out.println("No tickets were found under this email");
                        }
                        break;

                    case "4":
                        System.out.println("Enter the ID of the ticket you would like to update:");
                        UUID updatedID = UUID.fromString(in.nextLine());
                        Ticket updatedTicket = ticketStorage.get(updatedID);

                        System.out.println("""
                                Specify the information you would like to update (enter the number):
                                 1. Email
                                 2. Description
                                 3. Priority""");
                        String command4 = in.nextLine();

                        while(!command4.matches("[123]")){
                            System.out.println("The command was not correctly specified. Please enter from 1 to 3");
                            command4 = in.nextLine();
                        }

                        switch (command4) {
                            case "1":
                                System.out.println("Enter new email: ");
                                String updatedEmail = in.nextLine();

                                while(!isValidEmailAddress(updatedEmail)){
                                    System.out.println("The email address is not correct. Please specify valid email ");
                                    updatedEmail = in.nextLine();
                                }

                                updatedTicket.setEmail(updatedEmail);
                                System.out.println("Email was successfully updated. Here is the relevant information about ticket: ");
                                printTicket(updatedTicket);
                                break;

                            case "2":
                                System.out.println("Enter new description: ");
                                String updatedDescription = in.nextLine();
                                updatedTicket.setDescription(updatedDescription);
                                System.out.println("Description was successfully updated. Here is the relevant information about ticket: ");
                                printTicket(updatedTicket);
                                break;

                            case "3":
                                System.out.println("Enter the priority which should be set for the ticket (low/medium/high): ");
                                String updatedPriority = in.nextLine();
                                updatedTicket.setTaskUrgency(updatedPriority);
                                System.out.println("The priority of the ticket was successfully updated. Here is the relevant information about ticket: ");
                                printTicket(updatedTicket);
                                break;
                        }
                        storage.updateTicket(updatedTicket);
                        break;

                    case "5":
                        System.out.println("Enter the ID of the ticket you would like to delete:");
                        UUID deletedID = UUID.fromString(in.nextLine());
                        ticketStorage.remove(deletedID);
                        storage.deleteTicket(deletedID);
                        System.out.println("Ticket was successfully deleted");
                        break;

                    case "6":
                        for (Ticket value : ticketStorage.values()) {
                            printTicket(value);
                        }
                        break;
                }
            }
        }

    }

    public static void printTicket(Ticket ticket) {
        System.out.println("Ticket ID: " + ticket.getUuid() +
                "\n Email: " + ticket.getEmail() +
                "\n Description: " + ticket.getDescription() +
                "\n Priority: " + ticket.getTaskUrgency());
    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }


    public static boolean isValidTaskUrgency(String taskUrgency){
        return taskUrgency.equals("low") || taskUrgency.equals("medium") || taskUrgency.equals("high") || taskUrgency.isEmpty();
    }
}