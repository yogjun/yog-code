package cn.yog.chess.entity.user;

import lombok.Data;
import javax.persistence.*;

@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "name")
    private String name;

    @Column(name = "color")
    private String color;

    @Column(name = "room_id")
    private String roomId;

}
