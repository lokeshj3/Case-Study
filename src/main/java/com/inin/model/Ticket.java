package com.inin.model;

import java.time.LocalDateTime;
import java.util.HashSet;
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

    public Ticket(int id, String subject,String agent, Set tags){
        this.id = id;
        this.agent = agent;
        this.subject = subject;
        this.tags = tags != null ? new HashSet<>(tags) : null;
        this.created = this.modified = LocalDateTime.now();
    }

    public Ticket(Ticket ticket){
        this.id = ticket.getId();
        this.agent = ticket.getAgent();
        this.subject = ticket.getSubject();
        this.tags = ticket.getTags() != null ? new HashSet<>(ticket.getTags()) : null;
        this.created = ticket.getCreated();
        this.modified = ticket.getModified();
    }

    public int getId() {
        return this.id;
    }

    public String getSubject() {
        return this.subject;
    }

    public String getAgent() {
        return this.agent;
    }

    public LocalDateTime getCreated() {
        return this.created;
    }

    public LocalDateTime getModified() {
        return this.modified;
    }

    public Set<String> getTags() {
        return this.tags;
    }

    public void setAgent(String agent) {
        this.agent = agent;
        setModified();
    }

    public void setTags(Set<String> tags) {
        this.tags.clear();
        this.tags.addAll(tags);
        setModified();
    }
    private void setModified()
    {
        this.modified = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", agent='" + agent + '\'' +
                ", tags=" + tags +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }
}
