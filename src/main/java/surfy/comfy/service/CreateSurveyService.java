package surfy.comfy.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import surfy.comfy.data.result.GetSubmitListResponse;
import surfy.comfy.data.survey.*;
import surfy.comfy.entity.*;
import surfy.comfy.repository.*;
import surfy.comfy.type.QuestionType;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateSurveyService {
    private final EntityManager em;
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
    private final GridRepository gridRepository;
    private final EssayRepository essayRepository;

    @Transactional
    public void CreateSurveyDB(GetSurveyDataResponse data, Survey survey, Member member){
        List<GetQuestionResponse> ques_list=data.getQues_list();
        List<GetOptionResponse> ans_list=data.getAns_list();
        List<GetGridResponse> choice_list=data.getChoice_list();
        for(int i=0;i<ques_list.size();i++){
            Question question=new Question();
            GetQuestionResponse ques_item=ques_list.get(i);

            question.setSurvey(survey);
            question.setContents(ques_item.getQues());

            GetQuestionTypeResponse type=ques_item.getType();
            if(type.getId()==1 || type.getId()==2){
                for(int k=0;k<ans_list.size();k++){ //해당 Question의 ans_list 불러오기
                    GetOptionResponse ans_item=ans_list.get(k);

                    if(ans_item.getRootid()==ques_item.getId()){
                        Option option=new Option();

                        option.setQuestion(question);
                        option.setContents(ans_item.getValue());
                        option.setSurvey(survey);
                        optionRepository.save(option);
                    }
                }
            }
            if(type.getId()==1){ //객관식
                if(!type.getChoice_type()){
                    question.setQuestionType(QuestionType.객관식_단일);
                }
                else{
                    question.setQuestionType(QuestionType.객관식_중복);
                }
            }
            else if(type.getId()==2){ //객관식 Grid
                if(!type.getChoice_type()){
                    question.setQuestionType(QuestionType.객관식_그리드_단일);
                }
                else {
                    question.setQuestionType(QuestionType.객관식_그리드_중복);
                }
                for(int k=0;k<choice_list.size();k++){ //해당 Question의 choice_list 불러오기
                    GetGridResponse choice_item=choice_list.get(k);
                    if(choice_item.getRootid()==ques_item.getId()){
                        Grid grid=new Grid();

                        grid.setQuestion(question);
                        grid.setContents(choice_item.getValue());
                        grid.setSurvey(survey);
                        gridRepository.save(grid);
                    }
                }
            }
            else if(type.getId()==3){
                question.setQuestionType(QuestionType.주관식);
            }
            else if(type.getId()==4){
                question.setQuestionType(QuestionType.슬라이더);
            }

            questionRepository.save(question);
        }
        Question question=new Question();
        question.setSurvey(survey);
        question.setContents("만족도");
        question.setQuestionType(QuestionType.만족도);
        questionRepository.save(question);
        surveyRepository.save(survey);
    }

    @Transactional
    public void ResetSurveyDB(Survey survey){
        List<Question> ques_list=questionRepository.findAllBySurvey_Id(survey.getId());

        em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
        for(int i=0;i<ques_list.size();i++){

            List<Option> opt_list=optionRepository.findAllByQuestion_Id(ques_list.get(i).getId());
            List<Grid> grid_list=gridRepository.findAllByQuestion_Id(ques_list.get(i).getId());
            List<Essay> essay_list=essayRepository.findAllByQuestion_Id(ques_list.get(i).getId());

            optionRepository.deleteAllInBatch(opt_list);
            gridRepository.deleteAllInBatch(grid_list);
            essayRepository.deleteAllInBatch(essay_list);
        }
        questionRepository.deleteAllInBatch(ques_list);
        em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
    }
}