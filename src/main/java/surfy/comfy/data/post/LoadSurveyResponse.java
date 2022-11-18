package surfy.comfy.data.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import surfy.comfy.entity.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoadSurveyResponse {
    private String intro0;
    private String intro1;
    private Long surveyId;
    private String ques_contents;
    private Long quesId;
    private Long optionId;
    private String option_contents;
    private Long gridId;
    private String grid_contents;
    private Long essayId;
    private String essay_contents;

    public LoadSurveyResponse(Survey survey){
        this.intro0=survey.getTitle();
        this.intro1=survey.getContents();
        this.surveyId=survey.getId();
//        this.type="post";
    }

    public LoadSurveyResponse(Question question){
        this.quesId=question.getId();
        this.ques_contents=question.getContents();
    }

    public LoadSurveyResponse(Option option){
        this.optionId=option.getId();
        this.option_contents=option.getContents();
    }

    public LoadSurveyResponse(Grid grid){
        this.gridId=grid.getId();
        this.grid_contents=grid.getContents();
    }
    public LoadSurveyResponse(Essay essay){
        this.essayId=essay.getId();
        this.essay_contents=essay.getContents();
    }

}
