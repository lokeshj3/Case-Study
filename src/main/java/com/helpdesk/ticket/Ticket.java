package com.helpdesk.ticket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by root on 8/2/16.
 */
public class Ticket implements Serializable{

    private static final long serialVersionUID = 42L;
    private int id;
    private String subject;
    private String agentName;
    private Set<String> tags;
    private LocalDateTime created;
    private LocalDateTime modified;

    public Ticket(Ticket ticket) {
        this.id = ticket.id;
        this.subject = ticket.subject;
        this.agentName = ticket.agentName;
        this.tags = ticket.tags;
        this.modified = ticket.modified;
        this.created = ticket.created;
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

    private Ticket(Builder builder) {
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

        public Ticket build() {
            return new Ticket(this);
        }

    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeInt(this.id);
        out.writeUTF(this.subject);
        out.writeUTF(this.agentName);
        out.writeObject(this.tags);
        out.writeObject(this.created);
        out.writeObject(this.modified);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
        this.id         = in.readInt();
        this.subject    = in.readUTF();
        this.agentName  = in.readUTF();
        this.tags       = (HashSet) in.readObject();
        this.created    = (LocalDateTime) in.readObject();
        this.modified   = (LocalDateTime) in.readObject();
    }

    @Override
    public String toString() {
        return "id = " + this.id
                + ", subject = " + this.subject
                + "";
    }
}
