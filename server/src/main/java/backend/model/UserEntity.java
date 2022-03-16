package backend.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name="TB_User")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "user_nickname", nullable = false, unique = true, length = 50)
    private String nickname;

    @Column(name = "age")
    private Integer age;

    @Column(name = "user_mypage_introduction")
    private String myPageIntroduction;

    @Builder.Default
    @Column(name = "user_regist_date", nullable = false)
    private LocalDateTime registDate = LocalDateTime.now();

    @Builder.Default
    @Column(name = "user_modify_date")
    private LocalDateTime modifyDate = LocalDateTime.now();

    @Column(name = "user_gender")
    private Integer gender;



    @OneToMany(mappedBy = "user")
    private List<PostEntity> posts = new ArrayList<>();
}