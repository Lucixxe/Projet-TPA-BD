package org.tp.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.tp.data.Book;

public class BookDAO implements CRUDRepository<Book> {
    private final EntityManagerFactory emf;

    public BookDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    // ToDo: Création d'un livre dans la BD (ajout)
    @Override
    public void create(Book book) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(book);
        em.getTransaction().commit();
        em.close();
    }

    // ToDo: Recherche d'un Livre par son identifiant id
    @Override
    public Book findById(Long id) {
        EntityManager em = emf.createEntityManager();
        Book book = em.find(Book.class, id);
        em.close();
        return book;
    }

    // ToDo: Recherche de tous les livres de la base
    @Override
    public List<Book> findAll() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b", Book.class);
        List<Book> books = query.getResultList();
        em.close();
        return books;
    }

    // ToDo: Mise à jour d'un livre existant dans la base
    @Override
    public void update(Book book) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(book);
        em.getTransaction().commit();
        em.close();
    }

    // ToDo: Supprimer un livre de la base
    @Override
    public void delete(Book book) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.contains(book) ? book : em.merge(book));
        em.getTransaction().commit();
        em.close();
    }

    // ToDo: Rechercher tous les livres d'un auteur (identifié ici par son Id)
    public List<Book> findBooksByAuthorId(Long authorId) {
        EntityManager em = emf.createEntityManager();
        // ToDo (Astuce: On doit réaliser une requête JPQL de jointure entre un livre et sa collection pour travailler avec les éléments de celle-ci)
        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b JOIN b.authors a WHERE a.id = :authorId", Book.class);
        query.setParameter("authorId", authorId);
        List<Book> books = query.getResultList();
        em.close();
        return books;
    }

    // ToDo: Rechercher tous les livres qui correspondent à une catégorie
    public List<Book> findBooksByCategoryName(String categoryName) {
        EntityManager em = emf.createEntityManager();
        // ToDo (Astuce: On doit réaliser une requête JPQL basique sur Book)
        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.category.name = :categoryName", Book.class);
        query.setParameter("categoryName", categoryName);
        List<Book> books = query.getResultList();
        em.close();
        return books;
    }
}
