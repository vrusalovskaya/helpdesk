package org.helpdesk;

import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        HashMap<UUID, Ticket> ticketStorage = new HashMap<>();
        try (Scanner in = new Scanner(System.in)) {
            boolean keepGoing = true;
            while (keepGoing) {

                System.out.println("Hello! How can we help you today? Kindly specify the number of the needed action" +
                        "\n 1. Create the ticket" +
                        "\n 2. Show ticket by it's ID " +
                        "\n 3. Show all tickets registered under specific email" +
                        "\n 4. Update ticket by ID" +
                        "\n 5. Delete ticket by ID");

                String command = in.nextLine();

                switch (command) {
                    case "1":
                        System.out.print("We are sorry to hear that you have faced some issues. Kindly enter your email for ticket creation: ");
                        String email = in.nextLine();

                        System.out.print("Thanks! Please specify the issue you have faced (max 255 digits): ");
                        String description = in.nextLine();

                        System.out.print("Specify the urgency of the issue (low/medium/high) ");
                        String taskUrgency = in.nextLine();

                        if (taskUrgency == "") {
                            taskUrgency = "low";
                        }

                        Ticket ticket = new Ticket(email, description, taskUrgency);
                        ticketStorage.put(ticket.getUuid(), ticket);

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

                        System.out.println("Specify the information you would like to update (enter the number):" +
                                "\n 1. Email" +
                                "\n 2. Description" +
                                "\n 3. Priority");
                        String command4 = in.nextLine();

                        switch (command4) {
                            case "1":
                                System.out.println("Enter new email: ");
                                String updatedEmail = in.nextLine();
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
                        break;

                    case "5":
                        System.out.println("Enter the ID of the ticket you would like to delete:");
                        UUID deletedID = UUID.fromString(in.nextLine());
                        ticketStorage.remove(deletedID);
                        System.out.println("Ticket was successfully deleted");
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
}