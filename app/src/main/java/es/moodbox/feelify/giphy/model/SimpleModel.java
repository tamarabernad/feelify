package es.moodbox.feelify.giphy.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by victoriza on 17/01/15.
 */
public class SimpleModel {

	@SerializedName("data")
	public SimpleImage mGiphyData;

	public class SimpleImage {
		//private String type;

		@SerializedName("image_original_url")
		public String mUrl;

		@Override
		public String toString() {
			return mUrl;
		}
	}
}
