package server.questions;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Category {
    @Id
    private String name;
    private String color;

    public Category(String name, String color) {
        this.name = name;
        this.color = color;
    }
}
