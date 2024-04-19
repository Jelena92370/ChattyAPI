package dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TokenRefreshResponse {
    private String accessToken;
    private String refreshToken;
    private Integer expiration;
    private String message;
    private String httpStatus;
}
