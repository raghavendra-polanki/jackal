package io.vilo.viloapp.api;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

public class RestError
{
    @SerializedName("code") @Getter
    private Integer code;

    @SerializedName("error_message") @Getter
    private String strMessage;

    public RestError(String strMessage)
    {
        this.strMessage = strMessage;
    }
}
