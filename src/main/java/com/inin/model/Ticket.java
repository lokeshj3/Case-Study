package com.inin.model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by root on 8/2/16.
 */
public class Ticket implements Serializable{
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
        this.tags = new HashSet<>(tags);
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
        this.modified = LocalDateTime.now();
    }

    public void setTags(Set<String> tags) {
        this.tags.clear();
        this.tags.addAll(tags);
        this.modified = LocalDateTime.now();
    }


    /**
     * Custom serialization of ticket object
     * @param oos
     * @throws Exception
     */
    private void writeObject(ObjectOutputStream oos) throws Exception{
        oos.writeInt(this.id);
        oos.writeUTF(this.subject);
        oos.writeUTF(this.agent);
        oos.writeObject(this.tags);
        oos.writeObject(this.created);
        oos.writeObject(this.modified);
    }

    /**
     * Custom deserialization of ticket object
     * @param ois
     * @throws Exception
     */
    private void readObject(ObjectInputStream ois) throws Exception{
        this.id = ois.readInt();
        this.subject = ois.readUTF();
        this.agent = ois.readUTF();
        this.tags = (Set<String >)ois.readObject();
        this.created = (LocalDateTime)ois.readObject();
        this.modified = (LocalDateTime)ois.readObject();
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
