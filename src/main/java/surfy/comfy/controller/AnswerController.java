package surfy.comfy.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import surfy.comfy.data.survey.GetSurveyDataResponse;
import surfy.comfy.entity.Survey;
import surfy.comfy.repository.SurveyRepository;
import surfy.comfy.service.AnswerService;

import javax.transaction.Transactional;

@RestController
@RequiredArgsConstructor
public class AnswerController {
    private final SurveyRepository surveyRepository;
    private final AnswerService answerService;
    @SneakyThrows
    @Transactional
    @PostMapping("/respondent/{surveyId}")
    public void ResponseAnswer(@RequestBody GetSurveyDataResponse data, @PathVariable(name="surveyId")Long surveyId){

        Survey survey=surveyRepository.findSurveysById(surveyId);

        answerService.CreateAnswerDB(data,survey);
    }
}