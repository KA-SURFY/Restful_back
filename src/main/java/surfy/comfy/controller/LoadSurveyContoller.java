package surfy.comfy.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import surfy.comfy.config.BaseResponse;
import surfy.comfy.service.LoadSurveyService;

@RestController
@RequiredArgsConstructor
public class LoadSurveyContoller {

    private final LoadSurveyService loadSurveyService;
    private final Logger logger= LoggerFactory.getLogger(LoadSurveyContoller.class);
    @SneakyThrows
    @GetMapping("/survey/{surveyId}")
    public BaseResponse<Object> SendEditSurveyData(@PathVariable(name="surveyId")Long surveyId){
        Object result= loadSurveyService.getSurveydata(surveyId,false,null);
        logger.info("editSurvey - surveyId: {}",surveyId);
        return new BaseResponse<>(result);
    }

    @SneakyThrows
    @GetMapping("/respondent/{surveyId}")
    public BaseResponse<Object> SendRespondentSurveyData(@PathVariable(name="surveyId")Long surveyId){
        Object result= loadSurveyService.getSurveydata(surveyId,false,null);
        logger.info("respondentSurvey - surveyId: {}",surveyId);
        return new BaseResponse<>(result);
    }

    @SneakyThrows
    @GetMapping("/respondent/answer/{surveyId}/{submitId}")
    public BaseResponse<Object> SendSurveyAnswerData(@PathVariable(name="surveyId")Long surveyId,@PathVariable(name="submitId")Long submitId){
        Object result= loadSurveyService.getSurveydata(surveyId,true,submitId);
        logger.info("respondentSurveyAnswer - surveyId: {}, submitId: {}",surveyId,submitId);
        return new BaseResponse<>(result);
    }
}