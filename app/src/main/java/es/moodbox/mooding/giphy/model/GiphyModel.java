package es.moodbox.mooding.giphy.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by machete on 1/11/15.
 */
public class GiphyModel {

    @SerializedName("data")
    public List<GiphyData> mGiphyData;

    @SerializedName("pagination")
    public GiphyPagination mGiphyPagination;


    @Override
    public String toString() {
        return mGiphyData.toString();
    }

    public class GiphyData{
        @SerializedName("images")
        public GiphyImages images;

        @Override
        public String toString() {
            return images.toString();
        }
    }
    public class GiphyImages{
        @SerializedName("downsized")
        public GiphyImage image;

        @Override
        public String toString() {
            return image.toString();
        }

    }
    public class GiphyImage {

        @SerializedName("url")
        public String mUrl;

        @Override
        public String toString() {
            return mUrl;
        }
    }

    public class GiphyPagination{
        @SerializedName("total_count")
        public Integer totalCount;

        @Override
        public String toString() {
            return totalCount.toString();
        }
    }
}

