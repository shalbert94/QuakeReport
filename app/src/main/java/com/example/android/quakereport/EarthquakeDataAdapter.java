package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by shalom on 2017-04-12.
 */

public class EarthquakeDataAdapter extends ArrayAdapter<EarthquakeData> {
    public static final String PLACE_TEXT_SEPERATOR = " of ";
    private Context context;

    public EarthquakeDataAdapter(Context context, ArrayList<EarthquakeData> earthquakes) {
        super(context, 0, earthquakes);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final EarthquakeData earthquakeData = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item, parent, false);
        }

        final LinearLayout listItem = (LinearLayout) convertView.findViewById(R.id.list_item_linearlayout);
        TextView magnitude = (TextView) convertView.findViewById(R.id.magnitude_textview);
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitude.getBackground();
        TextView relativeToPlace = (TextView) convertView.findViewById(R.id.place_offset_textview);
        TextView place = (TextView) convertView.findViewById(R.id.place_textview);
        TextView date = (TextView) convertView.findViewById(R.id.date_textview);
        TextView time = (TextView) convertView.findViewById(R.id.time_textview);

        String itemMagnitude = prepareMagnitude(earthquakeData.getMagnitude());
        int magnitudeColor = getMagnitudeColor(earthquakeData.getMagnitude());
        String itemRelativeToPlace = placeOffsetSplit(earthquakeData.getPlace());
        String itemPlace = placeSplit(earthquakeData.getPlace());
        String itemDate = translateUnixTimestampToDate(earthquakeData.getUnixTime());
        String itemTime = translateUnixTimestampToTime(earthquakeData.getUnixTime());

        magnitude.setText(itemMagnitude);
        magnitudeCircle.setColor(magnitudeColor);
        relativeToPlace.setText(itemRelativeToPlace);
        place.setText(itemPlace);
        date.setText(itemDate);
        time.setText(itemTime);

        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWebPage(earthquakeData.getUrl());
            }
        });

        return convertView;
    }

    private String prepareMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    private int getMagnitudeColor(double magnitude) {
        int colorResourceId;
        int magnitudeInt = (int) Math.floor(magnitude);
        switch (magnitudeInt) {
            case 0:
                colorResourceId = R.color.magnitude1;
                break;
            case 1:
                colorResourceId = R.color.magnitude1;
                break;
            case 2:
                colorResourceId = R.color.magnitude2;
                break;
            case 3:
                colorResourceId = R.color.magnitude3;
                break;
            case 4:
                colorResourceId = R.color.magnitude4;
                break;
            case 5:
                colorResourceId = R.color.magnitude5;
                break;
            case 6:
                colorResourceId = R.color.magnitude6;
                break;
            case 7:
                colorResourceId = R.color.magnitude7;
                break;
            case 8:
                colorResourceId = R.color.magnitude8;
                break;
            case 9:
                colorResourceId = R.color.magnitude9;
                break;
            default:
                colorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), colorResourceId);
    }

    private String placeOffsetSplit(String place) {
        if (place.contains(PLACE_TEXT_SEPERATOR)) {
            String[] placeArray = place.split(PLACE_TEXT_SEPERATOR);
            place = placeArray[0] + PLACE_TEXT_SEPERATOR;
        } else {
            place = "Near the";
        }
        return place;
    }

    private String placeSplit(String place) {
        if (place.contains(PLACE_TEXT_SEPERATOR)) {
            String[] placeArray = place.split(PLACE_TEXT_SEPERATOR);
            place = placeArray[1];
        }
        return place;
    }

    private String translateUnixTimestampToDate(long unixTime) {
        Date dateObject = new Date(unixTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM D, YYYY");
        String dateToDisplay = dateFormat.format(dateObject);

        return dateToDisplay;
    }

    private String translateUnixTimestampToTime(long unixTime) {
        Date dateObject = new Date(unixTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
        String timeToDisplay = dateFormat.format(dateObject);

        return timeToDisplay;
    }

    private void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }
}
