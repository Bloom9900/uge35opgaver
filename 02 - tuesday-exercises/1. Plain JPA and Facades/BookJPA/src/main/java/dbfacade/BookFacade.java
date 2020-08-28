package dbfacade;

import Entity.Book;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class BookFacade {

    private static EntityManagerFactory emf;
    private static BookFacade instance;

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        BookFacade facade = BookFacade.getBookFacade(emf);
        Book b1 = facade.addBook("Author 1");
        Book b2 = facade.addBook("Author 2");
        //Find book by ID
        System.out.println("Book1: " + facade.findBook(b1.getId()).getAuthor());
        System.out.println("Book2: " + facade.findBook(b2.getId()).getAuthor());
        //Find all books
        System.out.println("Number of books: " + facade.getAllBooks().size());

    }

    private BookFacade() {
    }

    public static BookFacade getBookFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BookFacade();
        }
        return instance;
    }

    /*
    @purpose Is to add a book to our database
    @param Author of the book you want to add
    @return Gives back the book that you just added
     */
    public Book addBook(String author) {
        Book book = new Book(author);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(book);
            em.getTransaction().commit();
            return book;
        } finally {
            em.close();
        }
    }

    /*
    @purpose Searching for a book in our database
    @param id of the book in database
    @return the book you were searching for
     */
    public Book findBook(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Book book = em.find(Book.class, id);
            return book;
        } finally {
            em.close();
        }
    }

    /*
    @purpose Listing all books stored in the database
    @return A list of all the books.
     */
    public List<Book> getAllBooks() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Book> query
                    = em.createQuery("Select book from Book book", Book.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

}
