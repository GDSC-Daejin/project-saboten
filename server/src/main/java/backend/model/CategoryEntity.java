package backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name="TB_Cartegory")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Long cartegoryId;

    @Column(name = "category_name", nullable = false, length = 50)
    private String cartegoryName;

    @OneToOne(mappedBy = "category")
    private PostEntity post;
}