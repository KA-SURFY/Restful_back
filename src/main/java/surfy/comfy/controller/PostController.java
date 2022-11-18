package surfy.comfy.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import surfy.comfy.config.BaseResponse;
import surfy.comfy.data.manage.SurveyResponse;
import surfy.comfy.data.post.*;
import surfy.comfy.service.BookmarkService;
import surfy.comfy.service.PostService;
import surfy.comfy.service.SurveyService;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final SurveyService surveyService;
    private final BookmarkService bookmarkService;

    private final Logger logger= LoggerFactory.getLogger(PostController.class);

    /**
     * minseo
     * 마이 페이지 조회
     * @param memberId
     * @return MyPageResponse
     */
    @GetMapping("/myPage/{memberId}")
    public BaseResponse<MyPageResponse> myPage(@PathVariable(name="memberId")Long memberId) {
        logger.info("mypage - memberId: {}",memberId);
        List<GetPostResponse> myPostList = postService.getMyposts(memberId);
        List<GetPostResponse> bookmarkList = bookmarkService.getBookmarks(memberId);
        logger.info("bookmarkList: {}",bookmarkList.size());
        MyPageResponse result = new MyPageResponse();
        result.setBookmarks(bookmarkList);
        result.setMyposts(myPostList);

        return new BaseResponse<>(result);
    }


    /**
     * 병근
     * 커뮤니티 조회
     * @return List<PostResponse>
     */
//    @GetMapping("/community")
//    public BaseResponse<List<PostResponse>> community(){
//        logger.info("[Get] - /community");
//        List<PostResponse> posts=postService.getAllPosts();
//
//        return new BaseResponse<>(posts);
//    }

    /**
     * minseo
     * 커뮤니티 조회 v2
     */
    @GetMapping("/community/{memberId}")
    public BaseResponse<List<GetPostResponse>> community(@PathVariable(name="memberId") Long memberId){
        logger.info("[GET] - /community");
        List<GetPostResponse> result=postService.getAllPosts(memberId);

        return new BaseResponse<>(result);
    }

    /**
     * minseo
     * 게시글 조회
     * @param postId
     * @return memberId
     */
    @GetMapping("/post/{postId}/{memberId}")
    public BaseResponse<GetPostResponse> viewPost(@PathVariable(name="postId")Long postId,@PathVariable(name="memberId")Long memberId){
        logger.info("[viewPost] - request: {}",memberId);
        GetPostResponse getPostResponse=postService.getPost(postId, memberId);

        return new BaseResponse<>(getPostResponse);
    }



    /**
     * 게시글 삭제
     * @param postId
     * @param memberId
     * @return
     */
    @DeleteMapping("/post/{postId}/{memberId}")
    public BaseResponse<DeletePostResponse> deletePost(@PathVariable(name="postId")Long postId,@PathVariable(name="memberId")String memberId){
        logger.info("[deletePost]: {}",postId);
        DeletePostResponse response= postService.deletePost(postId,memberId);
        logger.info("[deletePost]: {}",response);
        return new BaseResponse<>(response);
    }

    /**
     * 게시글 제목으로 검색
     * @param title
     * @return
     */
    @GetMapping("/search")
    public BaseResponse<List<PostResponse>> Search(String title){
        List<PostResponse> SearchList = postService.searchPost(title);
        System.out.println("title:"+ title);
        System.out.println("search:"+ SearchList);
        return new BaseResponse<>(SearchList);

    }

    @GetMapping("/selectSurvey/{memberId}")
    public BaseResponse<List<SurveyResponse>> selectSurvey(@PathVariable(name="memberId")Long memberId){
        List<SurveyResponse> SurveyList = postService.getMySurvey(memberId);
        System.out.println("List"+ SurveyList);
        return new BaseResponse<>(SurveyList);

    }

    @PostMapping("/posting")
    public BaseResponse<RequestPost> createPost(@RequestBody RequestPost requestPost) {
        RequestPost returnPost = postService.registerPost(requestPost);
        return new BaseResponse<>(returnPost);

    }


    @GetMapping("/createPost/{surveyId}")
    public BaseResponse<SurveyResponse> getSurvey(@PathVariable(name="surveyId")Long surveyId){
        SurveyResponse response = surveyService.getSurvey(surveyId);
        System.out.println("List"+ response);
        return new BaseResponse<>(response);

    }


}
