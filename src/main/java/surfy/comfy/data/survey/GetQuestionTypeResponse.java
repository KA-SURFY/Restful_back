package surfy.comfy.data.survey;

import lombok.Data;
import lombok.SneakyThrows;
import surfy.comfy.entity.Question;
import surfy.comfy.type.QuestionType;

import java.util.ArrayList;
import java.util.List;

@Data
public class GetQuestionTypeResponse {
    private Long id;
    private String name;
    private Boolean choice_type;
    private List<GetChoiceAnswerResponse> choice_value;
    private String answer;
    private List<Object> fileList;
    public GetQuestionTypeResponse(){}
    @SneakyThrows
    public GetQuestionTypeResponse(Question question,Boolean loadAnswer,Long submitid){
        if(question.getQuestionType()== QuestionType.객관식_단일 ||
                question.getQuestionType()== QuestionType.객관식_중복){
            this.id=1L;
            this.name="객관식";
            if(question.getQuestionType()==QuestionType.객관식_단일){
                this.choice_type=false;
            }
            else{
                this.choice_type=true;
            }
            this.choice_value=new ArrayList<>();
            if(loadAnswer){
                for(int i=0;i<question.getAnswers().size();i++){
                    if(question.getAnswers().get(i).getSubmit()==submitid){
                        this.choice_value.add(new GetChoiceAnswerResponse(question.getAnswers().get(i)));
                    }
                }
            }

        }
        else if(question.getQuestionType()== QuestionType.객관식_그리드_단일 ||
                question.getQuestionType()== QuestionType.객관식_그리드_중복){
            this.id=2L;
            this.name="객관식 Grid";
            if(question.getQuestionType()==QuestionType.객관식_그리드_단일){
                this.choice_type=false;
            }
            else{
                this.choice_type=true;
            }
            this.choice_value=new ArrayList<>();
            if(loadAnswer){
                for(int i=0;i<question.getAnswers().size();i++){
                    if(question.getAnswers().get(i).getSubmit()==submitid) {
                        this.choice_value.add(new GetChoiceAnswerResponse(question.getAnswers().get(i)));
                    }
                }
            }
        }
        else if(question.getQuestionType()==QuestionType.주관식){
            this.id=3L;
            this.name="주관식";
            try{
                if(loadAnswer){
                    for(int i=0;i<question.getAnswers().size();i++){
                        if(question.getAnswers().get(i).getSubmit()==submitid) {
                            this.answer=question.getAnswers().get(i).getEssay().getContents();
                            break;
                        }
                    }

                }
            }
            catch(Exception e){
                this.answer="";
            }
        }
        else if(question.getQuestionType()==QuestionType.슬라이더){
            this.id=4L;
            this.name="슬라이더";
            try{
                if(loadAnswer){
                    for(int i=0;i<question.getAnswers().size();i++){
                        if(question.getAnswers().get(i).getSubmit()==submitid) {
                            this.answer= String.valueOf(question.getAnswers().get(i).getSlider().getValue());
                            break;
                        }
                    }
                }
            }
            catch(Exception e){
                this.answer="0";
            }
        }
        else if(question.getQuestionType()==QuestionType.파일){
            this.id=5L;
            this.name="파일 업로드";
            //this.fileList
        }

    }
}