package es.moodbox.feelify.giphy.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by machete on 1/11/15.
 */
public class GiphyModel {

    @SerializedName("data")
    public List<GiphyData> mGiphyData;


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
        @SerializedName("original")
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
}

