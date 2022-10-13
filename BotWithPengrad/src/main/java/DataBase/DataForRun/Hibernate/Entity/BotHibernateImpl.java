package DataBase.DataForRun.Hibernate.Entity;

import javax.persistence.*;

@Entity
@Table(name = "bots_and_tokens")
public class BotHibernateImpl implements BotHibernate {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "token")
    private String token;

    @Column(name = "url")
    private String url;


    public String getTokenBot() {
        return token;}


    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String userName) {
        this.name = userName;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    @Override
    public String toString() {
        return "Bot{" +
                "id='" + id + '\'' +
                ", userName='" + name + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
