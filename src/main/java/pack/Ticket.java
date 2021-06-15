package pack;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label = "pack.Ticket")
public class Ticket {

    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "Reg")
    private String reg;

    @Property(name = "Value")
    private String value;

    @Property(name = "Date")
    private String date;

    public Ticket(String reg, String value, String date) {
        this.reg = reg;
        this.value = value;
        this.date = date;
    }

    public Ticket(){}

    public Long getId() {
        return id;
    }

    public String getReg() {
        return reg;
    }

    public String getValue() {
        return value;
    }
    public void setValue(String val) { this.value=val;}

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Ticket{id: "+id+"}-[registration = " + reg + ", value = " + value + ", date = " + date+"]";
    }
}
