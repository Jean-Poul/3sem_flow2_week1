package Tester;

import Entity.Customer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author jplm
 */
public class Tester {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        try {
            Customer c1 = new Customer("J-P", "L-M");
            c1.addHobby("Coding");
            c1.addHobby("Trading");
            Customer c2 = new Customer("Customer", "lastName");
            c2.addHobby("Buying");
            c2.addHobby("Everything");
            em.getTransaction().begin();
            em.persist(c1);
            em.persist(c2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
