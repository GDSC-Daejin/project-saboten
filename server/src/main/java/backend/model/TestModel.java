package backend.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class TestModel {
    @Id
    @GeneratedValue
    Long id;

    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
