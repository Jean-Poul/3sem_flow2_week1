package dk.jplm.jpademo.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author jplm
 */
@Entity
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long p_id;
    private String name;
    private int year;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Address address;

    //Da det er en List er det en til mange
    //I en et til mange skal mappedBy være på et siden
    //Mange siden holder på fremmednøglen og er "the owning side"
    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST)
    private List<Fee> fees;

    @ManyToMany(mappedBy = "persons", cascade = CascadeType.PERSIST)
    private List<SwimStyle> styles;

    public Person() {
    }

    public Person(String name, int year) {
        this.name = name;
        this.year = year;
        this.fees = new ArrayList<>();
        this.styles = new ArrayList<>();
    }

    public Long getP_id() {
        return p_id;
    }

    public void setP_id(Long p_id) {
        this.p_id = p_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
        //Kan ikke sætte en address på noget der er null for så opstår en exception
        //Ved at tage fat i Address klassen, som har en Person, der peger tilbage kan man sætte det til dette object med den tilhørende address
        if (address != null) {
            address.setPerson(this);
        }

    }

    public List<Fee> getFees() {
        return fees;
    }

    public void addFee(Fee fee) {
        this.fees.add(fee);
        if (fee != null) {
            fee.setPerson(this);
        }
    }

    //Dobbelte bogholderi - bliver indsat på denne side og på Swimstyle siden
    public void addSwimStyle(SwimStyle style) {
        if (style != null) {
            this.styles.add(style);
            style.getPersons().add(this);
        }
    }

    public void removeSwimStyle(SwimStyle swimStyle) {
        if(swimStyle != null){
            styles.remove(swimStyle);
            swimStyle.getPersons().remove(this);
        }
    }

    public List<SwimStyle> getStyles() {
        return styles;
    }

}
