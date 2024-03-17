package org.helpdesk;

import java.util.Scanner;
import java.util.UUID;

public class Main {

    public static void main(String[] args) throws Exception {

        TicketStorage storage = new TicketStorage(args[0]);
        Helpdesk helpdesk = new Helpdesk(storage);

        try (Scanner in = new Scanner(System.in)) {
            boolean keepGoing = true;
            while (keepGoing) {

                System.out.println("""
                        Hello! How can we help you today? Kindly specify the number of the needed action
                         1. Create the ticket
                         2. Show ticket by it's ID
                         3. Show all tickets registered under specific email
                         4. Update ticket by ID
                         5. Delete ticket by ID
                         6. Print all tickets""");

                String command = in.nextLine();

                while (!command.matches("[123456]")) {
                    System.out.println("The command was not correctly specified. Please enter a number from 1 to 6");
                    command = in.nextLine();
                }

                switch (command) {
                    case "1":
                        System.out.print("We are sorry to hear that you have faced some issues. Kindly enter your email for ticket creation: ");
                        String email = in.nextLine();
                        while (!UserInputValidator.isValidEmailAddress(email)) {
                            System.out.println("The email address is not correct. Please specify valid email");
                            email = in.nextLine();
                        }

                        System.out.print("Thanks! Please specify the issue you have faced (max 255 digits): ");
                        String description = in.nextLine();
                        while (!UserInputValidator.isValidDescriptionLength(description)) {
                            System.out.println("The limit was exceeded. Please use no more than 255 digits to specify the description of the issue");
                            description = in.nextLine();
                        }

                        System.out.print("Specify the urgency of the issue (" + getEnumDescriptions() + ") or press \"enter\" if you would like to skip this step");
                        String taskUrgency = in.nextLine();

                        while (!UserInputValidator.isValidTaskUrgency(taskUrgency)) {
                            System.out.println("The urgency of the ticket was not defined correctly. Kindly specify one of required values (" + getEnumDescriptions() + " )or press \"enter\"");
                            taskUrgency = in.nextLine().toUpperCase();
                        }

                        UrgencyType taskUrgencyEnum = UserInputValidator.parseTaskUrgency(taskUrgency);

                        var ticket = helpdesk.createTicket(email, description, taskUrgencyEnum);

                        System.out.println("Your ticket was successfully created. You can track it by the following number: " + ticket.getUuid());
                        break;

                    case "2":
                        System.out.println("Enter the ID of the ticket you would like to review:");
                        UUID reviewedID = UUID.fromString(in.nextLine());

                        Ticket reviewedTicket = helpdesk.getTicket(reviewedID);
                        printTicket(reviewedTicket);

                        break;

                    case "3":
                        System.out.println("Enter the email:");
                        String reviewedEmail = in.nextLine();
                        var tickets = helpdesk.getTicketsByEmail(reviewedEmail);

                        if (tickets.isEmpty()) {
                            System.out.println("No tickets were found under this email");
                        } else {
                            for (var t : tickets) {
                                printTicket(t);
                            }
                        }
                        break;

                    case "4":
                        System.out.println("Enter the ID of the ticket you would like to update:");
                        UUID updatedID = UUID.fromString(in.nextLine());
                        Ticket updatedTicket = helpdesk.getTicket(updatedID);

                        System.out.println("""
                                Specify the information you would like to update (enter the number):
                                 1. Email
                                 2. Description
                                 3. Priority""");
                        String command4 = in.nextLine();

                        while (!command4.matches("[123]")) {
                            System.out.println("The command was not correctly specified. Please enter from 1 to 3");
                            command4 = in.nextLine();
                        }

                        switch (command4) {
                            case "1":
                                System.out.println("Enter new email: ");
                                String updatedEmail = in.nextLine();

                                while (!UserInputValidator.isValidEmailAddress(updatedEmail)) {
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
                                while (!UserInputValidator.isValidDescriptionLength(updatedDescription)) {
                                    System.out.println("The limit was exceeded. Please use no more than 255 digits to specify the description of the issue");
                                    updatedDescription = in.nextLine();
                                }
                                updatedTicket.setDescription(updatedDescription);
                                System.out.println("Description was successfully updated. Here is the relevant information about ticket: ");
                                printTicket(updatedTicket);
                                break;

                            case "3":
                                System.out.println("Enter the priority which should be set for the ticket (" + getEnumDescriptions() + "): ");
                                String updatedPriority = in.nextLine();

                                while (!UserInputValidator.isValidTaskUrgency(updatedPriority)) {
                                    System.out.println("The urgency of the ticket was not defined correctly. Kindly specify one of required values (" + getEnumDescriptions() + " )or press \"enter\"");
                                    updatedPriority = in.nextLine().toUpperCase();
                                }

                                updatedTicket.setTaskUrgency(UserInputValidator.parseTaskUrgency(updatedPriority));
                                System.out.println("The priority of the ticket was successfully updated. Here is the relevant information about ticket: ");
                                printTicket(updatedTicket);
                                break;
                        }
                        helpdesk.updateTicket(updatedTicket);
                        break;

                    case "5":
                        System.out.println("Enter the ID of the ticket you would like to delete:");
                        UUID deletedID = UUID.fromString(in.nextLine());
                        helpdesk.deleteTicket(deletedID);
                        System.out.println("Ticket was successfully deleted");
                        break;

                    case "6":
                        for (Ticket value : helpdesk.getTickets()) {
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

    private static String getEnumDescriptions() {
        var result = "";
        for (UrgencyType value : UrgencyType.values()) {
            result = result + "/" + value.toString();
        }
        return result.replaceFirst("/", "");
    }

}