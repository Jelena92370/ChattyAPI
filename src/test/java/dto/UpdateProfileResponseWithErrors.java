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

public class UpdateProfileResponseWithErrors {

    private String message;
    private String httpStatus;

    private ArrayList< Object > name = new ArrayList < Object > ();
    private ArrayList< Object > surname = new ArrayList < Object > ();
    private ArrayList< Object > role = new ArrayList < Object > ();
}
