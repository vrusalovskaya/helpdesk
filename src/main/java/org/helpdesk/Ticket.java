package org.helpdesk;

import java.io.Serializable;
import java.util.UUID;

public class Ticket implements Serializable {
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
    public Ticket(UUID uuid, String email, String description, String taskUrgency) {
        this.uuid = uuid;
        this.email = email;
        this.description = description;
        this.taskUrgency = taskUrgency;
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

    public UUID getUuid() {
        return uuid;
    }
}
