package surfy.comfy.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import surfy.comfy.config.BaseResponse;
//import surfy.comfy.data.result.QuestionAnswerResponse;
//import surfy.comfy.data.result.QuestionResponse;
import surfy.comfy.data.result.QuestionAnswerResponse;
import surfy.comfy.data.result.SurveyResultResponse;
import surfy.comfy.data.result.RespondentsResponse;
import surfy.comfy.entity.Grid;
import surfy.comfy.entity.Option;
import surfy.comfy.service.ResultService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;

    // 설문 정보
    @GetMapping("/result/{survey_id}")
    public BaseResponse<SurveyResultResponse> getSurvey(@PathVariable(name="survey_id") Long surveyId){
        SurveyResultResponse survey = resultService.getSurveyById(surveyId);

        return new BaseResponse<>(survey);
    }
    // 개별 보기 - 응답자 수
    @GetMapping("/result/individual/{survey_id}")
    public BaseResponse<List<RespondentsResponse>> getSurveyIndividual(@PathVariable(name="survey_id") Long surveyId){
        List<RespondentsResponse> responseList = resultService.getRespondents(surveyId);

        return new BaseResponse<>(responseList);
    }

    // 문항별 보기
    @GetMapping("/result/question/{survey_id}")
    public BaseResponse<List<QuestionAnswerResponse>> getSurveyQuestion(@PathVariable(name="survey_id") Long surveyId){
        List<QuestionAnswerResponse> questionAnswerResponseList = resultService.getQuestionAnswerList(surveyId);

        return new BaseResponse<>(questionAnswerResponseList);
    }

    // 객관식 옵션
    @GetMapping("/result/question/option/{survey_id}/{question_id}")
    public BaseResponse<List<Option>> getQuestionOption(@PathVariable(name="survey_id") Long surveyId, @PathVariable(name="question_id") Long questionId){
        List<Option> optionList = resultService.getOptions(surveyId, questionId);

        return new BaseResponse<>(optionList);
    }

    // 그리드 옵션
    @GetMapping("/result/question/grid/{survey_id}/{question_id}")
    public BaseResponse<List<Grid>> getGridOption(@PathVariable(name="survey_id")Long surveyId, @PathVariable(name="question_id")Long questionId){
        List<Grid> gridList = resultService.getGridOptions(surveyId, questionId);

        return new BaseResponse<>(gridList);
    }
}