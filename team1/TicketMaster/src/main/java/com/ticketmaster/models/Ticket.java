package com.ticketmaster.models;

import com.ticketmaster.utils.SerializerUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

/**
 * Ticket class
 * This class is used to represent a ticket object.
 * Created by Virendra on 8/2/16.
 */
public class Ticket implements Serializable{

    //setter methods
    private void setId(Integer id){
        this.id = id;
    }
    public void setAgent(String agent){
        this.agent = agent;
    }
    public void setTags(Set<String> tags){
        if (!(this.tags instanceof Set))
            this.tags = new HashSet<>();
        this.tags.addAll(tags);
    }

    //getter methods
    public Integer getId(){
        return this.id;
    }
    public String getSubject(){
        return this.subject;
    }
    public String getAgent(){
        return this.agent;
    }
    public long getCreated(){
        return this.created;
    }
    public long getModified(){
        return this.modified;
    }
    public Set<String> getTags(){
        if (!(this.tags instanceof Set))
            this.tags = new HashSet<>();
        return this.tags;
    }

    private boolean beforeSave(){
        long time =  LocalDateTime.now(ZoneId.of("UTC")).toInstant(ZoneOffset.UTC).toEpochMilli();
        if (this.created == 0) this.created = time;
        this.modified = time;
        return true;
    }

    public boolean save(SerializerUtil util)
            throws IOException {
        this.beforeSave();
        //fetch id from properties file
        String tmpId = util.readProperty("id");
        masterId = Integer.parseInt(tmpId);
        this.setId(masterId++);
        return true;
    }

    private void writeObject(ObjectOutputStream out)
            throws IOException{
        out.writeUTF(getSubject());
        out.writeObject(this.tags);
        out.writeLong(getCreated());
        out.writeLong(getModified());
        out.writeInt(getId());
        out.writeUTF(getAgent());
    }

    private void readObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        this.subject = in.readUTF();
        this.tags = (HashSet) (in.readObject());
        this.created = in.readLong();
        this.modified = in.readLong();

        setId(in.readInt());
        setAgent(in.readUTF());
    }

    public Ticket(TicketBuilder object){
        this.subject=   object.getSubject();
        this.agent  =   object.getAgent();
        this.setTags(object.getTags());
    }

    /**
     * Inner Builder class (director) to setup fields of class
     */
    public static class TicketBuilder{

        public TicketBuilder(String subject, String agent){
            this.subject = subject;
            this.agent = agent;
        }

        public TicketBuilder withTags(Set<String> tags){
            this.tags = tags;
            return this;
        }

        public Ticket build(){
            return new Ticket(this);
        }

        //getter methods
        public String getAgent(){
            return this.agent;
        }
        public String getSubject(){
            return this.subject;
        }
        public Set<String> getTags(){
            if (!(this.tags instanceof Set))
                this.tags = new HashSet<>();
            return this.tags;
        }

        private String subject = null;
        private String agent = null;
        private Set tags = new HashSet<>();

    }

    /**
     * toString method. returns the string representation of ticket object
     * @return String
     */
    @Override
    public String toString(){
        return "Ticket: id"+this.getId()+"|subject:"+this.getSubject()+"|agent:"+this.getAgent();
    }

    /**
     * overridden method
     * @return hash of instance <p>int</p>
     */
    @Override
    public int hashCode(){
        return this.getId()+this.getSubject().hashCode()+this.getAgent().hashCode();
    }

    //attributes
    private int id;
    private long created;
    private long modified;
    private String subject;
    private String agent;
    private Set<String> tags;
    public static Integer masterId = 0;
    public static final long serialVersionUID = 881811645564116084L;
}
