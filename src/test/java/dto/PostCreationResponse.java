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

public class PostCreationResponse {

    private String id;
    private String title;
    private String description;
    private String body;
    private String imageUrl;
    private String publishDate;
    private String updatedAt;
    private boolean draft;
    private String userId;

    private UserResponse user;

    private String httpStatus;
    private String message;


}
