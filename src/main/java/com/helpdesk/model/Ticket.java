package com.helpdesk.model;

/**
 * Created by root on 8/2/16.
 * Modal Class for Ticket,
 */

import com.sun.istack.internal.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class Ticket implements Serializable{
    private int id;
    private String subject;
    private String agent;
    private HashSet<String> tags;
    private LocalDateTime created;
    private LocalDateTime updated;

    public static class Builder{
        private int id;
        private String subject;
        private String agent;
        private HashSet<String> tags;

        public Builder withId(int id){
            this.id = id;
            return this;
        }

        public Builder withSubject(String subject){
            this.subject = subject;
            return this;
        }

        public Builder withAgent(String agent){
            this.agent = agent;
            return this;
        }

        public Builder withTags(Set<String> tags) {
            this.tags = new HashSet<>(tags);
            return this;
        }

        public Ticket build(){
            return new Ticket(this);
        }
    }

    public Ticket(Builder builder) {
        this.id = builder.id;
        this.subject = builder.subject;
        this.agent = builder.agent;
        this.tags = builder.tags;
        this.created = this.updated = LocalDateTime.now();
    }

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

    public Set<String> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void updateAgent(@NotNull String agent){
        this.agent = agent;
        this.updated = LocalDateTime.now();
    }

    public void addTags(@NotNull Set<String> tags){
        this.tags.addAll(tags);
        this.updated = LocalDateTime.now();
    }

    public void removeTags(@NotNull Set<String> tags){
        this.tags.removeAll(tags);
        this.updated = LocalDateTime.now();
    }

    public String toString(){
        return "{Id: " + this.id + " | Subject: " + this.subject + " | Agent: " + this.agent +
                " | Tags: " + this.tags.toString() + " | Created: " + this.created +
                " | Updated: " + this.updated + "}";
    }
}