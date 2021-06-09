package com.ys.challange.rest.api.model;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class AccreditationResponse {

    private Boolean success;
    private Boolean accredited;

}
