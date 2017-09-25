package com.brim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brim.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionDetails extends AppCompatActivity {

    ImageView back_button,transaction_icon;
    TextView txt_description,txt_amount,txt_trans_date,txt_category,txt_rewards,txt_posted_date,txt_foreign_exchange,txt_forex;
    RelativeLayout set_up_block;
    LinearLayout foreign_exchange_block;
    RelativeLayout foreign_block;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);

        back_button= (ImageView) findViewById(R.id.back_button);
        transaction_icon= (ImageView) findViewById(R.id.transaction_icon);

        txt_description= (TextView) findViewById(R.id.txt_description);
        txt_amount= (TextView) findViewById(R.id.txt_amount);
        txt_trans_date= (TextView) findViewById(R.id.txt_trans_date);
        txt_category= (TextView) findViewById(R.id.txt_category);
        txt_rewards= (TextView) findViewById(R.id.txt_rewards);
        txt_posted_date= (TextView) findViewById(R.id.txt_posted_date);
        txt_foreign_exchange= (TextView) findViewById(R.id.txt_foreign_exchange);
        txt_forex= (TextView) findViewById(R.id.txt_forex);

        foreign_exchange_block= (LinearLayout) findViewById(R.id.foreign_exchange_block);
        foreign_block= (RelativeLayout) findViewById(R.id.foreign_block);
        //foreign_block.setVisibility(View.GONE);

        set_up_block= (RelativeLayout) findViewById(R.id.set_up_block);
        //set_up_block.setVisibility(View.GONE);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final Intent intent=getIntent();

        Picasso.with(this)
                .load(intent.getExtras().getString("category_image"))
                .into(transaction_icon);

        //Log.d("raw date",":::"+intent.getExtras().getString("transaction_date"));
        txt_posted_date.setText(dateFormatChange(intent.getExtras().getString("posted_date")));
        txt_trans_date.setText(dateFormatChange(intent.getExtras().getString("transaction_date")));

        txt_description.setText(intent.getExtras().getString("description"));
        txt_amount.setText("$"+intent.getExtras().getString("amount"));
        txt_category.setText(intent.getExtras().getString("category_name"));
        txt_rewards.setText(intent.getExtras().getString("points")+" pts");

        if (intent.getExtras().getBoolean("eligible_installment_status")==true && intent.getExtras().getBoolean("installment_flag")==false){
            set_up_block.setVisibility(View.VISIBLE);
        }else{
            set_up_block.setVisibility(View.GONE);

        }

        if(intent.getExtras().getLong("savings")>0){

            foreign_exchange_block.setVisibility(View.VISIBLE);
            txt_foreign_exchange.setText("$"+intent.getExtras().getLong("savings"));
        }else{
            foreign_exchange_block.setVisibility(View.GONE);

        }

        if(intent.getExtras().getLong("foreign_amount")==0 && intent.getExtras().getLong("conversion_rate")==0){
            foreign_block.setVisibility(View.GONE);
        }else{
            foreign_block.setVisibility(View.VISIBLE);
            txt_forex.setText(intent.getExtras().getLong("foreign_amount")+" USD at "+intent.getExtras().getLong("conversion_rate"));
        }

        set_up_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent1=new Intent(TransactionDetails.this,SetUpInstallments.class);
                intent1.putExtra("amount",intent.getExtras().getString("amount"));
                intent1.putExtra("des",intent.getExtras().getString("description"));
                intent1.putExtra("transaction_id",intent.getExtras().getString("transaction_id"));

               // intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent1);
            }
        });
    }

    public String dateFormatChange(String date){
        String inputDateStr=date;
        String outputDateStr="";

        try {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat outputFormat = new SimpleDateFormat("MMMM dd, yyyy");
            Date d1 = inputFormat.parse(inputDateStr);
            outputDateStr = outputFormat.format(d1);

            Log.d("date Change",":::"+outputDateStr);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return  outputDateStr;
    }
}
