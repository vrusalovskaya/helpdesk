package org.helpdesk;

import java.util.UUID;

public final class Ticket {
    private final UUID uuid;
    private String email;
    private String description;
    private UrgencyType taskUrgency;

    public Ticket(String email, String description, UrgencyType taskUrgency) {
        this(UUID.randomUUID(), email, description, taskUrgency);
    }

    Ticket(UUID uuid, String email, String description, UrgencyType taskUrgency) {
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

    public UrgencyType getTaskUrgency() {
        return taskUrgency;
    }

    public void setTaskUrgency(UrgencyType taskUrgency) {
        this.taskUrgency = taskUrgency;
    }

    public UUID getUuid() {
        return uuid;
    }
}

