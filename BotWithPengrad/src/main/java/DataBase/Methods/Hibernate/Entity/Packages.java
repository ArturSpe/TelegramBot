package DataBase.Methods.Hibernate.Entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "packages")
@Getter
@Setter
public class Packages {

    @Id
    @Column(name = "id")
    @GeneratedValue (strategy = GenerationType.IDENTITY, generator = "packages_id_seq")
    private long id;

    @Column(name = "package")
    private String name;

    @OneToMany
    @JoinColumn (name = "id_package")
    private List<Words> word;

    @OneToMany
    @JoinColumn (name = "id_package")
    private List<Phrase> phrase;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Words> getWord() {
        return word;
    }

    public void setWord(List<Words> word) {
        this.word = word;
    }

    public List<Phrase> getPhrase() {
        return phrase;
    }

    public void setPhrase(List<Phrase> phrase) {
        this.phrase = phrase;
    }
}
