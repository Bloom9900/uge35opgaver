package Entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityTester {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        Book book1 = new Book ("J.K. Rowling");
        Book book2 = new Book ("George R. R. Martin");
        try {
            em.getTransaction().begin();
            em.persist(book1);
            em.persist(book2);
            em.getTransaction().commit();
            //Verify that books are managed and has been given database ID
            System.out.println(book1.getId());
            System.out.println(book2.getId());
        } finally {
            em.close();
        }
    }
}
