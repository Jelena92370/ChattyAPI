package dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PostCreationResponseWithErrors {

    private ArrayList< Object > title = new ArrayList < Object > ();
    private ArrayList< Object > description = new ArrayList < Object > ();
    private ArrayList< Object > body = new ArrayList < Object > ();
}
