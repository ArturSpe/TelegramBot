package DataBase.Methods.Hibernate.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "phrases")
@Getter
@Setter
public class Phrase {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "phrases_id_seq")
    private long id;

    @Column(name = "id_package")
    private long idPackage;

    @Column(name = "phrase")
    private String phrase;

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

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public long getIdPackage() {
        return idPackage;
    }

    public void setIdPackage(long idPackage) {
        this.idPackage = idPackage;
    }

    @Override
    public String toString() {
        return "Phrase{" +
                "id=" + id +
                ", packages=" + packages +
                ", phrase='" + phrase + '\'' +
                '}';
    }
}

