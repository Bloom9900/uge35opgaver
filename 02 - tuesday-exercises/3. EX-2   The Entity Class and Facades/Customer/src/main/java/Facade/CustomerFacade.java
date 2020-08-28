package Facade;

import Entity.Customer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class CustomerFacade {
    private static EntityManagerFactory emf;
    private static CustomerFacade instance;
    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        CustomerFacade facade = getCustomerFacade(emf);
        Customer cust1 = facade.addCustomer("Niels", "Olsen");
        Customer cust2 = facade.addCustomer("Per", "Jensen");
        //Find Customer by ID
        System.out.println("Customer with ID 1: "+facade.findByID(cust1.getId()));
        System.out.println("Customer with ID 2: "+facade.findByID(cust2.getId()));
        //Find Customer by first name
        System.out.println("Customer with ID 1 is named: "+facade.findByLastName("Olsen").getFirstName());
        System.out.println("Customer with ID 2 is named: "+facade.findByLastName("Jensen").getFirstName());
        //Amount of customers in database
        System.out.println("There is "+facade.getNumberOfCustomers()+" cutomers in our database");
        //All customer entries in database
        List<Customer> listen = facade.allCustomers();
        for (Customer customer : listen) {
            System.out.println(customer);
        }
        //Add new customer
        String fName = "Troels";
        String lName = "Haugaard";
        facade.addCustomer(fName, lName);
        System.out.println("Customer added with name: "+facade.findByLastName(lName)); 
    }
    
    private CustomerFacade() {
    }
    
    
    public static CustomerFacade getCustomerFacade(EntityManagerFactory _emf) {
        if(instance == null) {
            emf = _emf;
            instance = new CustomerFacade();
        }
        return instance;
    }
    
    public Customer findByID(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Customer cust = em.find(Customer.class, id);
            return cust;
        } finally {
            em.close();
        }
    }
    
    public Customer findByLastName(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c WHERE c.lastName = :name", Customer.class);
            query.setParameter("name", name);
            Customer cust = query.getSingleResult();
            return cust;
        } finally {
            em.close();
        }
    }
    
    public long getNumberOfCustomers() {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT COUNT(c) FROM Customer c");
            return (long) query.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    public List<Customer> allCustomers() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c", Customer.class);
            List<Customer> list = query.getResultList();
            return list;
        } finally {
            em.close();
        }
    }
    
    public Customer addCustomer(String fName, String lName) {
        EntityManager em = emf.createEntityManager();
        Customer cust = new Customer(fName, lName);
        try {
            em.getTransaction().begin();
            em.persist(cust);
            em.getTransaction().commit();
            return cust;
        } finally {
            em.close();
        }
    }
}
