package backend.model;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name="TB_Category")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "category_name", nullable = false, length = 50)
    private String categoryName;
}