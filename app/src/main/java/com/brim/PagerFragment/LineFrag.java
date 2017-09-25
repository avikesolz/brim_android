package com.brim.PagerFragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brim.ApiHelper.HTTP_Get;
import com.brim.AppContant.ApiConstant;
import com.brim.AppContant.AppConstant;
import com.brim.AppContant.BrimApplication;
import com.brim.R;
import com.brim.Utils.AppProgerssDialog;
import com.brim.Utils.NetworkChecking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by su on 20/9/17.
 */

public class LineFrag extends Fragment {

    LineChartView Line_chart;


    private int numberOfLines = 0;
    private int maxNumberOfLines = 4;
    private int numberOfPoints = 31;
    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLines = true;
    private boolean hasPoints = true;
    private ValueShape shape = ValueShape.CIRCLE;
    private boolean isFilled = false;
    private boolean hasLabels = false;
    private boolean isCubic = false;
    private boolean hasLabelForSelected = true;
    private boolean pointsHaveDifferentColor;

    String[] Days=new String[numberOfPoints];


    List<PointValue> point_values = new ArrayList<PointValue>();
    List<PointValue> point_values2 = new ArrayList<PointValue>();

    NetworkChecking networkChecking;
    AppProgerssDialog appProgerssDialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.line_graph_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        networkChecking=new NetworkChecking(getActivity());
        appProgerssDialog=new AppProgerssDialog(getActivity());

        Line_chart = (LineChartView) view.findViewById(R.id.Line_chart);
        Line_chart.setZoomEnabled(false);


        getLine1_Data("2017-09");
        generateXY_Axis_Values();

    }

    private  void generateXY_Axis_Values(){

        for (int i=0;i<31;i++){

            if(i==0)
                Days[i]="1";
            else if(i==30)
                Days[i]="31";
            else
                Days[i]="-";

        }




        //............................................for X Labels............................................


  /*      for (int i = 0; i <numberOfPoints; i=i++) {

            axisXValues.add(new AxisValue(i).setLabel(Days[i]));

        }
        Axis tempoXAxis = new Axis(axisXValues).setHasLines(true);
        //tempoXAxis.setName("Month");
        tempoXAxis.setTypeface(Typeface.DEFAULT_BOLD);
        tempoXAxis.setTextColor(Color.GRAY);
        tempoXAxis.setLineColor(Color.LTGRAY);
        tempoXAxis.setTextSize(13);

        data.setAxisXBottom(tempoXAxis);*/

    }

    private void generateGraph() {


      /*  Line_chart.setViewportCalculationEnabled(false);
        //Log.i("Total point", "" + point_values.size());
        final Viewport v = new Viewport(Line_chart.getMaximumViewport());
        v.bottom = 0;
        v.top = 0;
        v.left = 0;
        v.right = 0;
        Line_chart.setMaximumViewport(v);
        Line_chart.setCurrentViewport(v);*/

        Line line=null,line2=null;

        if (point_values.size()>0) {

            line = new Line(point_values);
            line.setColor(getResources().getColor(R.color.colorPrimaryDark));
            line.setShape(shape);
            line.setCubic(isCubic);
            line.setFilled(isFilled);
            line.setHasLabels(hasLabels);
            // line.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
            line.setPointRadius(5);
            line.setStrokeWidth(2);
            line.setPointColor(getResources().getColor(R.color.colorPrimaryDark));


        }

        if (point_values2.size()>0) {

            line2 = new Line(point_values2);
            line2.setColor(getResources().getColor(R.color.lightBlue));
            line2.setShape(shape);
            line2.setCubic(isCubic);
            line2.setFilled(isFilled);
            line2.setHasLabels(hasLabels);
            // line.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line2.setHasLines(hasLines);
            line2.setHasPoints(hasPoints);
            line2.setPointRadius(5);
            line2.setStrokeWidth(2);
            line2.setPointColor(getResources().getColor(R.color.lightBlue));


        }



        List<Line> lines = new ArrayList<Line>();
        lines.add(line);
        lines.add(line2);

        LineChartData data = new LineChartData();
        data.setLines(lines);


        List<AxisValue> axisXValues = new ArrayList<AxisValue>();
        List<AxisValue> axisYValues = new ArrayList<AxisValue>();

        for (int i = 0; i <numberOfPoints; i++) {

            axisXValues.add(new AxisValue(i).setLabel(""+i));

        }
        Axis tempoXAxis = new Axis(axisXValues).setHasLines(false);
        //tempoXAxis.setName("Month");
 /*       tempoXAxis.setTypeface(Typeface.DEFAULT_BOLD);
        tempoXAxis.setTextColor(Color.GRAY);
        tempoXAxis.setLineColor(Color.LTGRAY);
        tempoXAxis.setTextSize(13);*/

        data.setAxisXBottom(tempoXAxis);




        Line_chart.setLineChartData(data);
  /*      Line_chart.setOnValueTouchListener(new ValueTouchListener());
        Line_chart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popup.dismiss();
                Log.v("setOnTouchListener",String.valueOf(event.getX()) + "," + String.valueOf(event.getY()));
                tap_x=event.getX();
                tap_y=event.getY();

                return false;
            }
        });*/

    }

    public void getLine1_Data(String date) {

       /* appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();*/

        new HTTP_Get(ApiConstant.SPEND_TREND+"?"+ AppConstant.CARDID+"="+ BrimApplication.getInstnace().GetCardId()+"&year_month="+date) {
            @Override
            protected void OnSucess(String response) {

                float x,y;

                try {
                    JSONArray jsonArray=new JSONArray(response);

                    for (int i=0;i<jsonArray.length();i++){

                        JSONObject jsonObject=jsonArray.getJSONObject(i);

                        x=jsonObject.getInt("day");
                        y=jsonObject.getInt("current_total");

                        point_values.add(new PointValue(x,y));

                        Log.i("Line-1 Point------", "(" + x+ "," + y + ")");

                    }

                    getLine2_Data("2017-08");



                } catch (JSONException e) {
                    e.printStackTrace();

                  //  appProgerssDialog.Dismiss();

                }


            }

            @Override
            protected void OnErrorApi(String Error) {

                //appProgerssDialog.Dismiss();


            }

            @Override
            protected void OnHttPError(String HttpError) {

                //appProgerssDialog.Dismiss();


            }
        };

    }

    public void getLine2_Data(String date) {

       /* appProgerssDialog.SetTitle(getString(R.string.app_name));
        appProgerssDialog.SetMessage("Please Wait...");
        appProgerssDialog.Show();*/

        new HTTP_Get(ApiConstant.SPEND_TREND+"?"+ AppConstant.CARDID+"="+ BrimApplication.getInstnace().GetCardId()+"&year_month="+date) {
            @Override
            protected void OnSucess(String response) {

                float x,y;

                try {
                    JSONArray jsonArray=new JSONArray(response);

                    for (int i=0;i<jsonArray.length();i++){

                        JSONObject jsonObject=jsonArray.getJSONObject(i);

                        x=jsonObject.getInt("day");
                        y=jsonObject.getInt("current_total");

                        point_values2.add(new PointValue(x,y));

                        Log.i("Line-2 Point------", "(" + x+ "," + y + ")");


                    }

                    Activity activity = getActivity();

                    if(activity != null){

                        generateGraph();

                    }



                } catch (JSONException e) {
                    e.printStackTrace();

                    //  appProgerssDialog.Dismiss();

                }


            }

            @Override
            protected void OnErrorApi(String Error) {

                //appProgerssDialog.Dismiss();


            }

            @Override
            protected void OnHttPError(String HttpError) {

                //appProgerssDialog.Dismiss();


            }
        };

    }


}
