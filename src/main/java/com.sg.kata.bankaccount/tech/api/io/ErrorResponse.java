package org.account.manager.tech.api.io;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "the error body thrown by the different services")
public class ErrorResponse {
    @ApiModelProperty(notes = "the Error code", name = "code")
    private String code;
    @ApiModelProperty(notes = "the Error message", name = "message")
    private String message;
    @ApiModelProperty(notes = "the Error time", name = "timeStamp")
    private String timeStamp;
}