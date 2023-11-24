package org.tp;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Set;
import java.util.List;
import org.tp.service.*;
import org.tp.data.*;

public class Main {
    public static void main(String[] args) {
        // Initialisation de l'EntityManagerFactory (utilisez H2 pour les tests et Oracle pour le déploiement)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");

        // Création d'une catégorie
        CategoryService categoryService = new CategoryServiceImpl(emf);
        Category fictionCategory = new Category();
        fictionCategory.setName("Fiction");
        categoryService.createCategory(fictionCategory);

        // ToDo: Création de deux auteurs de nom Author1 et Author2
        AuthorService authorService = new AuthorServiceImpl(emf);
        Author author1 = new Author();
        author1.setName("Author 1");
        authorService.createAuthor(author1);

        Author author2 = new Author();
        author2.setName("Author 2");
        authorService.createAuthor(author2);

        BookService bookService = new BookServiceImpl(emf);
        // ToDo: Création d'un PaperBook de titre PaerperBook1 et de 200 pages
        // Ajouter les auteurs Author1 et Author2 comme auteurs de ce livre
        PaperBook paperBook1 = new PaperBook();
        paperBook1.setTitle("PaperBook1");
        paperBook1.setPageCount(200);
        paperBook1.setAuthors(Set.of(author1, author2)); // Utilisation de Set.of() pour créer un ensemble
        paperBook1.setCategory(fictionCategory);
        // ToDo: Faire persister ce livre à l'aide du service bookService (c'est une création)
        bookService.createBook(paperBook1);

        // ToDo: refaire la même chose avec un Ebook de titre EBook1 et de format PDF.
        Ebook ebook1 = new Ebook();
        ebook1.setTitle("EBook1");
        ebook1.setFormat("PDF");
        ebook1.setAuthors(Set.of(author1, author2));
        ebook1.setCategory(fictionCategory);
        bookService.createBook(ebook1);

        // ToDo: Recherche d'un livre par son Id (par exemple avec l'ID du paperbook précédent)
        // ET on afficha sa catégorie
        Book foundBook = bookService.findBookById(paperBook1.getId());// On utilise le service bookService;
        Category foundCategory = foundBook.getCategory();// On extrait la catégorie à l'aide d'un getter;
        System.out.println("Category of PaperBook 1: " + foundCategory.getName());

        // Recherche d'auteurs d'un livre
        Set<Author> foundAuthors = foundBook.getAuthors();
        System.out.println("Authors of PaperBook 1:");
        for (Author author : foundAuthors) {
            System.out.println("Author Name: " + author.getName());
        }

        // ToDo: Recherche de livres par catégorie (Fiction par exemple), on affiche juste les titres
        // On utilise la méthode de bookService pour une recherche
        // On parcours la liste résultat pour afficher les titres de livres
        List<Book> booksByCategory = bookService.findBooksByCategoryName("Fiction");
        System.out.println("Books in Fiction category:");
        for (Book book : booksByCategory) {
            System.out.println("Book Title: " + book.getTitle());
        }

        // ToDo: Recherche de livres par auteur (par exemple ar l'Author 1 créé avant), on affiche juste les titres
        // idem recherche par catégorie dans le principe
        List<Book> booksByAuthor = bookService.findBooksByAuthorId(author1.getId());
        System.out.println("Books by Author 1:");
        for (Book book : booksByAuthor) {
            System.out.println("Book Title: " + book.getTitle());
        }
        // Fermeture de l'EntityManagerFactory
        emf.close();
    }
}
