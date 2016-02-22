
package com.Jasagredo.Youtube_Search_Related;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Id {

    @SerializedName("videoId")
    @Expose
    private String videoId;

    /**
     * 
     * @return
     *     The videoId
     */
    public String getVideoId() {
        return videoId;
    }

    /**
     * 
     * @param videoId
     *     The videoId
     */
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
