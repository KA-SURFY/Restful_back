package surfy.comfy.data.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import surfy.comfy.entity.Post;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPost {
    private Long id;
    private String title;
    private String contents;
    private Long memberId;
    private Long surveyId;
    public RequestPost(Post post){
        this.title=post.getTitle();
        this.contents=post.getContents();
        this.memberId=post.getMember().getId();
        this.surveyId=post.getSurvey().getId();
    }
}