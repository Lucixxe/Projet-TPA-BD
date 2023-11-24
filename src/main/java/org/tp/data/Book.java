package org.tp.data;

import jakarta.persistence.*;
import java.util.Set;

@Entity
// ToDo: Définir Book comme source d'héritage en mode SINGLE_TABLE
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// ToDo: Définir l'attribut discriminant pour identifier les sous-classes
@DiscriminatorColumn(name = "book_type", discriminatorType = DiscriminatorType.STRING)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    // ToDo: Définir le type de lien avec Category
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    
    // ToDo: Définir le type de lien avec Author (On ne définit pas le lien inverse ici)
    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }
}
