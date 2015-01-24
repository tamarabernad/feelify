package es.moodbox.mooding.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import es.moodbox.mooding.R;
import es.moodbox.mooding.giphy.model.MoodModel;

/**
 * Created by machete on 1/17/15.
 */
public class MoodsAdapter extends ArrayAdapter<MoodModel> {
    // declaring our ArrayList of items
    private List<MoodModel> objects;
    private Random random;
    private int[] colors;
    public MoodsAdapter(Context context, int layoutResourceId, List<MoodModel> objects) {
        super(context, layoutResourceId, objects);
        this.objects = objects;
        random = new Random();
        colors = new int[]{R.color.palette1, R.color.palette2, R.color.palette3,R.color.palette4,R.color.palette5};
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int colorIndex = random.nextInt(colors.length);
        int color = colors[colorIndex];
        // assign the view we are converting to a local variable
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.moods_list_item, null);
        }
        String txt = objects.get(position).name;

        TextView tv = (TextView) v.findViewById(R.id.mood_list_item);
        tv.setText(txt);
        tv.setBackgroundResource(color);
        return v;
    }
}
