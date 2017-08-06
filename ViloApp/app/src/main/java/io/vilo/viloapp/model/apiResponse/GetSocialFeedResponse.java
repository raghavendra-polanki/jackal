package io.vilo.viloapp.model.apiResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.vilo.viloapp.model.VideoModel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by raghavendra on 06/08/17.
 */

public class GetSocialFeedResponse extends BaseResponse {

    @Getter
    @Setter
    @SerializedName("data")
    private List<VideoModel> videoList;

}
