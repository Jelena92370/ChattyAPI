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

public class UserResponse {


    private String id;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private String role;
    private String gender;
    private String birthDate;
    private String avatarUrl;
    private String backgroundUrl;
    private boolean blocked;

    private String message;
}
