package com.brim.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.brim.R;

/**
 * Created by apple on 18/08/17.
 */

public  class AppAlert {
    Context mContext;

    public AppAlert(Context mContext) {
        this.mContext = mContext;
    }


    public void Error(String message)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.show_error_message_dialog, null);
        TextView title_dia = (TextView) view.findViewById(R.id.title);
        TextView message_dia = (TextView) view.findViewById(R.id.message);
        TextView ok = (TextView) view.findViewById(R.id.ok);
        title_dia.setText("Error");
        message_dia.setText(message);
        ok.setText("Ok");
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.show();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }


}
