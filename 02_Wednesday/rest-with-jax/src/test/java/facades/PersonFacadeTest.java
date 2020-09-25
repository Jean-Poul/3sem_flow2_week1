package facades;

import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Person;
import exceptions.MissingInputException;
import exceptions.PersonNotFoundException;
import java.util.List;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    
    private Person p1;
    private Person p2;

    public PersonFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PersonFacade.getPersonFacde(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        p1 = new Person("Kurt", "Wonnegut", "1234");
        p2 = new Person("Jønke", "bbb", "6666");
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.persist(p1);
            em.persist(p2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    @Test
    public void testPersonCount() {
        assertEquals(2, facade.getPersonFacadeCount(), "Expects two rows in the database");
    }

    @Test
    public void testGetAllPersons() {
        PersonsDTO personsDTO = facade.getAllPersons();
        
        assertEquals(facade.getAllPersons().equals(personsDTO), personsDTO.equals(personsDTO));
    }
    
    @Test
    public void testGetPersonById() throws PersonNotFoundException {
        
        PersonDTO personDTO = facade.getPerson(p1.getId());
        assertEquals("Kurt", personDTO.getFirstName());
        
    }
    
    @Test
    public void testAddPerson() throws MissingInputException {
        PersonDTO person = facade.addPerson("J-P", "L-M", "1234");
        PersonsDTO list = facade.getAllPersons();
        List<PersonsDTO> k = (List<PersonsDTO>) list;
        assertEquals(3, k.size());
        
    }

}
