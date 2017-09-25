package com.brim.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.brim.Font.AxiformaBook;
import com.brim.Font.AxiformaRegular;
import com.brim.R;

/**
 * Created by apple on 22/08/17.
 */

public class AppProgerssDialog {

    Context mContext;
    AlertDialog Dailog;
    AxiformaBook TXTitle;
    AxiformaRegular Msg;

      String Title="";
      String Message="";

    public void SetTitle(String Title)
    {
        this.Title=Title;
        TXTitle.setText(Title);
        TXTitle.setVisibility(View.VISIBLE);

    }

    public void SetMessage(String Message){
        this.Message=Message;
        Msg.setText(Message);
        Msg.setVisibility(View.VISIBLE);

    }

    public AppProgerssDialog(Context mContext) {
        this.mContext = mContext;
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
        View view=inflater.inflate(R.layout.loader_view,null);
         TXTitle=(AxiformaBook)view.findViewById(R.id.Title);
        TXTitle.setVisibility(View.GONE);
         Msg=(AxiformaRegular)view.findViewById(R.id.Msg);
        Msg.setVisibility(View.INVISIBLE);

        builder.setView(view);
        Dailog=builder.create();
        Dailog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Dailog.setCanceledOnTouchOutside(false);
    }


    public void Show(){
        Dailog.show();
    }
    public void Dismiss(){
        Dailog.dismiss();
    }

}
