package ar.com.wolox.woloxtrainingmolina.utils;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ar.com.wolox.woloxtrainingmolina.Config;
import ar.com.wolox.woloxtrainingmolina.R;

public class UiHelper {

    public static Toolbar setToolbar(ActionBarActivity activity,
                                     int view_toolbar,
                                     int view_title,
                                     String title,
                                     int view_logo,
                                     int logo) {
        Toolbar local_toolbar = (Toolbar) activity.findViewById(view_toolbar);
        ImageView local_logo = (ImageView) activity.findViewById(view_logo);
        TextView local_title = (TextView) activity.findViewById(view_title);

        activity.setSupportActionBar(local_toolbar);
        activity.getSupportActionBar().setElevation(Config.UI_ELEVATION);
        activity.getSupportActionBar().setTitle(null); //El title lo ponemos en view_title sino queda a la izquierda del logo
        local_logo.setImageResource(logo);
        local_title.setText(title);

        return local_toolbar;
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
