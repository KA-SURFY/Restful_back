package surfy.comfy.data.survey;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import surfy.comfy.entity.Question;
@Data
public class GetQuestionResponse {
    private Long id;
    private String ques;
    private JSONObject type;
    @SneakyThrows
    public GetQuestionResponse(Question question,Boolean loadAnswer,Long submitid){
        ObjectMapper mapper=new ObjectMapper();
        JSONParser parser=new JSONParser();

        this.id=question.getId();
        this.ques=question.getContents();
        String str_answer=mapper.writeValueAsString(new GetQuestionTypeResponse(question,loadAnswer,submitid));
        this.type=(JSONObject) parser.parse(str_answer);
    }
}