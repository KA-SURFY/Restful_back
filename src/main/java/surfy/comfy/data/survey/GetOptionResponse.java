

package surfy.comfy.data.survey;

import lombok.Data;
import surfy.comfy.entity.Option;
@Data
public class GetOptionResponse {
    private Long temid;
    private Long rootid;
    private Long id;
    private String value;
    public GetOptionResponse(){}
    public GetOptionResponse(Option option){
        this.temid=0L;
        this.rootid=option.getQuestion().getId();
        this.id=option.getId();
        this.value=option.getContents();
    }
}