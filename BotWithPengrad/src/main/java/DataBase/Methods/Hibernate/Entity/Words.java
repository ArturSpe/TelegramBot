package DataBase.Methods.Hibernate.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "words")
@Getter
@Setter
public class Words {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "words_id_seq")
    private long id;

    @Column(name = "id_package")
    private long idPackage;
    @Column(name = "word")
    private String word;
    @ManyToOne
    @JoinColumn(name = "id_package", insertable = false, updatable = false)
    private Packages packages;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Packages getPackages() {
        return packages;
    }

    public void setPackages(Packages packages) {
        this.packages = packages;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public long getIdPackage() {
        return idPackage;
    }

    public void setIdPackage(long idPackage) {
        this.idPackage = idPackage;
    }

    @Override
    public String toString() {
        return "Words{" +
                "id=" + id +
                ", packages=" + packages +
                ", word='" + word + '\'' +
                '}';
    }
}
