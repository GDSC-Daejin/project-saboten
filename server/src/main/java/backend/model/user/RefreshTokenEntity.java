package backend.model.user;

import backend.model.user.UserEntity;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "TB_Refresh_Token")
public class RefreshTokenEntity {

    @Id
    @Column(name="user_id", nullable = false)
    private Long userId;

    @MapsId
    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="user_id", nullable = false)
    public UserEntity user;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;
}