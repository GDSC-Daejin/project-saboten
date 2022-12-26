package backend.model.post;

import backend.controller.dto.PostScrapDto;
import backend.model.compositekey.PostScrapPK;
import backend.model.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity @IdClass(PostScrapPK.class)
@Table(name = "TB_PostScrap")
public class PostScrapEntity {

    @Id
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="post_id", nullable = false)
    public PostEntity post;

    @Id
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="user_id", nullable = false)
    public UserEntity user;

    public PostScrapDto toDto() {
        return PostScrapDto.builder()
                .post(post.toDto())
                .user(user.toDto())
                .build();
    }
}
