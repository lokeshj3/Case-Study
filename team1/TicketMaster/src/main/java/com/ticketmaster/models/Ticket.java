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

        if (this.tags == null)
            this.tags = new HashSet<>();
        if (tags!= null)
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
        return this.tags;
    }

    /**
     * beforeSave method ensures created and modified values
     * @return <p>boolean</p>
     */
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
    /**
     * update method updates the details of ticket
     * @return boolean <code></>true</code>
     * @throws IOException
     * @throws ClassNotFoundException
     */

    public boolean update()  throws IOException, ClassNotFoundException{
        beforeSave();
        return true;
    }

    /**
     * writeObject method is used to serialize ticket object
     * @param out <p>ObjectOutputStream</p>
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream out)
            throws IOException{
        out.writeUTF(this.getSubject());
        out.writeObject(this.getTags());
        out.writeLong(this.getCreated());
        out.writeLong(this.getModified());
        out.writeInt(this.getId());
        out.writeUTF(this.getAgent());
    }

    /**
     * readObject method is used to deserialize ticket object
     * @param in <p>ObjectInputStream</p>
     * @throws IOException
     */
    private void readObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        this.subject = in.readUTF();
        this.tags = (Set<String>) (in.readObject());
        this.created = in.readLong();
        this.modified = in.readLong();

        this.setId(in.readInt());
        this.setAgent(in.readUTF());
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
        public Set<String> getTags(){ return this.tags; }

        private String subject;
        private String agent;
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
