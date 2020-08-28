package Entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityTested {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        
        Customer cust1 = new Customer("Søren", "Jensen");
        Customer cust2 = new Customer("Jakob", "Olsen");
        
        try {
            em.getTransaction().begin();
            em.persist(cust1);
            em.persist(cust2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
                
    }
}
