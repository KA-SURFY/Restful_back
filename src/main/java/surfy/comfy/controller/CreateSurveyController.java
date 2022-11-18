package surfy.comfy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import surfy.comfy.config.BaseResponse;
import surfy.comfy.data.ThumbnailRequest;
import surfy.comfy.data.survey.*;
import surfy.comfy.entity.*;
import surfy.comfy.repository.*;
import surfy.comfy.service.CreateSurveyService;
import surfy.comfy.type.SurveyType;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CreateSurveyController {

    private final MemberRepository memberRepository;
    private final SurveyRepository surveyRepository;
    private final CreateSurveyService createSurveyService;

    /**
     * minseo
     * @param request
     * @return
     */
//    @PatchMapping("/thumbnail")
//    public BaseResponse<String> postThumbnail(@RequestBody ThumbnailRequest request){
//        System.out.println("email: "+request.getEmail());
//        System.out.println("imgSrc: "+request.getImgSrc());
//        System.out.println("bgColor: "+request.getBgColor());
//        System.out.println("surveyId: "+request.getSurveyId());
//
//        surveyService.patchSurveyThumbnail(request);
//
//        return new BaseResponse<>("ggg");
//    }

    /**
     * minseo
     * @param request
     * @return
     */

    @PatchMapping("/survey")
    public BaseResponse<String> patchThumbnail(@RequestBody ThumbnailRequest request){
        System.out.println("email: "+request.getEmail());
        System.out.println("imgSrc: "+request.getImgSrc());
        System.out.println("bgColor: "+request.getBgColor());
        

        return new BaseResponse<>("ggg");
    }

    @PostMapping("/survey/{memberId}")
    public BaseResponse<Long> CreateSurvey(@RequestBody GetSurveyDataResponse data, @PathVariable(name="memberId")String memberId){

        System.out.println("memberId: "+memberId);
        Optional<Member> loadmember= memberRepository.findById(Long.parseLong(memberId));
        Member member=loadmember.get();

        Survey survey=new Survey();

        survey.setTitle(data.getIntro0());
        survey.setContents(data.getIntro1());
        survey.setMember(member);

        if(data.getEnd().equals("not")){
            survey.setStatus(SurveyType.notFinish);
        }
        else{
            survey.setStatus(SurveyType.surveying);
            LocalDate end=LocalDate.parse(data.getEnd());
            survey.setEnd(end);
            LocalDate start=LocalDate.parse(data.getStart());
            survey.setStart(start);
        }
        createSurveyService.CreateSurveyDB(data,survey,member);
        System.out.println("surveyId"+survey.getId());

        return new BaseResponse<>(survey.getId());
    }

    @PostMapping("/survey/{surveyId}/{memberId}")
    public BaseResponse<Long> EditSurvey(@RequestBody GetSurveyDataResponse data,@PathVariable(name="surveyId")Long surveyId, @PathVariable(name="memberId")String memberId){
        Optional<Member> loadmember= memberRepository.findById(Long.parseLong(memberId));
        Member member=loadmember.get();
        Survey survey = surveyRepository.findSurveysById(surveyId);

        survey.setTitle(data.getIntro0());
        survey.setContents(data.getIntro1());

        if(data.getEnd().equals("not")){
            survey.setStatus(SurveyType.notFinish);
        }
        else{
            survey.setStatus(SurveyType.surveying);
            LocalDate end=LocalDate.parse(data.getEnd());
            survey.setEnd(end);
            LocalDate start=LocalDate.parse(data.getStart());
            survey.setStart(start);
        }

        createSurveyService.ResetSurveyDB(survey);
        createSurveyService.CreateSurveyDB(data,survey,member);
        return new BaseResponse<>(survey.getId());
    }
}