
package dk.jplm.jpademo.entities;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author jplm
 */
public class Tester {
    public static void main(String[] args) {
       
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        
        Person p1 = new Person("J-P", 1984);
        Person p2 = new Person("Batman", 1984);
        
        Address a1 = new Address("Skovkrogen 7", 2920, "CPH");
        Address a2 = new Address("Gotham street", 9999, "Gotham");
        
        p1.setAddress(a1);
        p2.setAddress(a2);
        
        Fee f1 = new Fee(100);
        Fee f2 = new Fee(200);
        Fee f3 = new Fee(300);
        
        p1.addFee(f1);
        p1.addFee(f3);
        p2.addFee(f2);
        
        SwimStyle s1 = new SwimStyle("Crawl");
        SwimStyle s2 = new SwimStyle("ButterFly");
        SwimStyle s3 = new SwimStyle("Breast stroke");
        
        p1.addSwimStyle(s1);
        p1.addSwimStyle(s3);
        p2.addSwimStyle(s2);
        
        try {
            //adding to database
            em.getTransaction().begin();
              //Udkommenteres pga der er lavet relation i Person klassen med @OneToOne
//            em.persist(a1);
//            em.persist(a2);
            em.persist(p1);
            em.persist(p2);
            em.getTransaction().commit();
            
            //removing from database and SwimStyle List
            em.getTransaction().begin();
            em.remove(s3);
            em.getTransaction().commit();
            
            //Everything above has been manage since we havnent closed the transaction yet
            
            System.out.println("p1: " + p1.getP_id() + ", " + p1.getName());
            System.out.println("p2: " + p2.getP_id() + ", " + p2.getName());
            
            System.out.println("J-Ps street: " + p1.getAddress().getStreet());
            System.out.println("Batmans street: " + p2.getAddress().getStreet());
            
            System.out.println("Lad os se om to-vejs virker: " + a1.getPerson().getName());
            
            System.out.println("Hvem har betalt f2? Det har: " + f2.getPerson().getName());
            
            System.out.println("Hvad er der blevet betalt i alt?");
            
            TypedQuery<Fee> q1 = em.createQuery("SELECT f from Fee f", Fee.class);
            List<Fee> fees = q1.getResultList();
            
            for (Fee f: fees) {
                System.out.println(f.getPerson().getName() + ": " + f.getAmount() + "kr. Den: " + f.getPayDate() + "Adresse: " + f.getPerson().getAddress().getCity());
            }
            
        } finally {
            
            em.close();
            
        }
    }
}
