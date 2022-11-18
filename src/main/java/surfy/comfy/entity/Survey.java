package surfy.comfy.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import surfy.comfy.type.SurveyType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter
@Table(name="survey")
public class Survey {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="survey_id")
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="member_id")
    private Member member; // 설문 제작자

    @JsonManagedReference
    @OneToMany(mappedBy="survey",cascade = CascadeType.ALL)
    private List<Question> questions;

    @JsonManagedReference
    @OneToMany(mappedBy = "survey",cascade = CascadeType.ALL)
    private List<Satisfaction> satisfactions;

    @JsonManagedReference
    @OneToMany(mappedBy = "survey",cascade = CascadeType.ALL)
    private List<Essay> essays;

    private LocalDate start;
    private LocalDate end;

    private String title;
    private String contents;

    @Enumerated(EnumType.STRING)
    private SurveyType status;

    private Long satisfaction;
    private String bgColor;
    private Long thumbnail;
}