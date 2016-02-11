package com.helpdesk.ticket;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by root on 8/2/16.
 */
public class TicketModel {

    private int id;
    private String subject;
    private String agentName;
    private Set<String> tags;
    private LocalDateTime created;
    private LocalDateTime modified;

    public TicketModel(){
    }


    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getAgentName() {
        return agentName;
    }

    public Set<String> getTags() {
        if (tags == null) {
            tags = new HashSet<String>();
        }
        return Collections.unmodifiableSet(tags);
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
        this.modified = LocalDateTime.now();
    }

    public void setTags(Set<String> tags) {
        this.tags.clear();
        this.tags.addAll(tags);
        this.modified = LocalDateTime.now();
    }

    private TicketModel(Builder builder) {
        this.id = builder.id;
        this.subject = builder.subject;
        this.agentName = builder.agentName;
        this.tags = builder.tags;
        this.modified = this.created = LocalDateTime.now();
    }

    public static class Builder {
        private int id;
        private String subject;
        private String agentName;
        private Set<String> tags;

        public Builder(int id, String subject, String agentName) {
            this.id = id;
            this.subject = subject;
            this.agentName = agentName;
        }

        public Builder withTags(Set tags) {
            this.tags = new HashSet<String>(tags);
            return this;
        }

        public TicketModel build() {
            return new TicketModel(this);
        }

    }
}
