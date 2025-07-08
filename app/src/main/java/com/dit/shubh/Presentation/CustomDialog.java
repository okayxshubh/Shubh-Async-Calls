package com.dit.shubh.Presentation;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.dit.shubh.R;

public class CustomDialog {

    public static void showDialog(final Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_custom);

        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.50);
        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView text = (TextView) dialog.findViewById(R.id.dialog_result);
        text.setMovementMethod(new ScrollingMovementMethod());
        text.setText(msg);


        Button dialog_ok = (Button) dialog.findViewById(R.id.dialog_ok);
        dialog_ok.setOnClickListener(view -> dialog.dismiss());
        dialog.show();

    }
}
