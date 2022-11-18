package surfy.comfy.data.result;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import surfy.comfy.entity.Survey;
import surfy.comfy.type.SurveyType;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyResultResponse {

    private Long id;
    private String title;
    private String contents;
    private double satisfaction;
    // 문서 타입이 넘어오면 저장해주면 될 듯
    private SurveyType type;

    private LocalDate end;

    public SurveyResultResponse(Survey survey){
        this.id = survey.getId();
        this.title = survey.getTitle();
        this.contents = survey.getContents();
        this.satisfaction=survey.getSatisfaction();
        this.type = survey.getStatus();
        this.end = survey.getEnd();
    }
}