package org.example.spring_data_jpa_homework.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class APIResponse<T>{
    private String message;
    private HttpStatus status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T payload;
    private Integer code;
    private LocalDateTime time;

    public APIResponse(String newCustomerAddedSuccessfully, HttpStatus httpStatus, T customerResponse, int i, LocalDateTime now) {
    }
}
