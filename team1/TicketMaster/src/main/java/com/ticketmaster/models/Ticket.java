package com.ticketmaster.models;

import com.ticketmaster.utils.SerializerUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Ticket class
 * This class is used to represent a ticket object.
 * Created by Virendra on 8/2/16.
 */
public class Ticket {

    public Ticket(){

    }
    public Ticket(TicketBuilder object){
        this.subject=   object.getSubject();
        this.agent  =   object.getAgent();
        this.setTags(object.getTags());

    }
    //setter methods
    private void setId(int id){
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
    public int getId(){
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

    public boolean save()
            throws IOException {

        this.beforeSave();

        //fetch id from properties file
        SerializerUtil util = new SerializerUtil();
        String masterId = util.readProperty("id");

        k = Integer.parseInt(masterId);

        this.setId(k++);
        return true;

        /*Map<Integer, Ticket> tempMap = new HashMap<>();
        tempMap.put(getId(), this);

        //add ticket in file
        util.writeToFile(tempMap);
        //read new entries
        repository.updatePool();
        repository.addAgent(this.getAgent());
        repository.addTags(this.getTags());
        //update id in file
        util.writeProperty("id",new Integer(Ticket.k).toString());
        return repository.getTicket(this.getId()) != null;*/


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
    private static int k = 0;
    private int id;
    private long created;
    private long modified;
    public static final long serialVersionUID = 881811645564116084L;
    private String subject;
    private String agent;
    private Set<String> tags;

    /**
     * Inner Builder class (director) to setup fields of class
     */
    public static class TicketBuilder{

        public TicketBuilder(){

        }

        public TicketBuilder withSubject(String subject){
            this.subject = subject;
            return this;
        }

        public TicketBuilder withAgent(String agent){
            this.agent = agent;
            return this;
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

}
