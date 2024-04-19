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

public class RegisterResponse {

    private String accessToken;
    private String refreshToken;
    private Integer expiration;
    private String message;
    private String httpStatus;

    private ArrayList< Object > email = new ArrayList < Object > ();
    private ArrayList< Object > password = new ArrayList < Object > ();
}
