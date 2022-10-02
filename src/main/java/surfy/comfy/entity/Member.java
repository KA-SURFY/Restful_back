package surfy.comfy.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name="member")
public class Member {

    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String email;
    //private String password;
    private String name;

    @OneToMany(mappedBy="member",cascade = CascadeType.ALL)
    private List<Post> posts;

    @OneToMany(mappedBy="member",cascade=CascadeType.ALL)
    private List<Survey> surveys;

    @OneToMany(mappedBy="member",cascade = CascadeType.ALL)
    private List<Bookmark> bookmarks;

    private String provider;


    // sign up
    public Member(String email, String name,String provider){
        this.email=email;
        this.name=name;
        this.provider=provider;
    }

}
