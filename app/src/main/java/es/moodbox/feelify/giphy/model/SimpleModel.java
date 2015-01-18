package es.moodbox.feelify.giphy.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by victoriza on 17/01/15.
 */
public class SimpleModel {

	@SerializedName("data")
	public SimpleImage giphyData;

	public class SimpleImage {
		//private String type;

		@SerializedName("image_original_url")
		public String url;

		@SerializedName("image_width")
		public String width;

		@SerializedName("image_height")
		public String height;

		@Override
		public String toString() {
			return url;
		}
	}
}
