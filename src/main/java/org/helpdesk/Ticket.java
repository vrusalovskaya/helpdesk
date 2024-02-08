package org.helpdesk;

import java.util.UUID;

public class Ticket {
    private UUID uuid;
    private String email;
    private String description;
    private String taskUrgency;

    public Ticket(String email, String description, String taskUrgency) {
        this.uuid = UUID.randomUUID();
        this.email = email;
        this.description = description;
        this.taskUrgency = taskUrgency;
    }

    public Ticket(String email, String description) {
        this(email, description, "low");
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTaskUrgency() {
        return taskUrgency;
    }

    public void setTaskUrgency(String taskUrgency) {
        this.taskUrgency = taskUrgency;
    }
}