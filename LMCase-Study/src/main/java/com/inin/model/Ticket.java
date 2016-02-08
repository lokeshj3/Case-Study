package com.inin.model;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by root on 8/2/16.
 */
public class Ticket {
    private int id;
    private String subject;
    private String agent;
    private Set<String> tags;
    private LocalDateTime created;
    private LocalDateTime modified;

    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getAgent() {
        return agent;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }
}
