package com.pinisielektra.apps;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendPosition;

public class PieChartActivity extends Activity {

	private PieChart mChart;
	
	private String[] mMonths = new String[] {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"
    };

    private String[] mParties = new String[] {
            "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pie_chart);
		
		mChart = (PieChart) findViewById(R.id.chart1);
		// change the color of the center-hole
        mChart.setHoleColor(Color.rgb(235, 235, 235));
        mChart.setHoleRadius(60f);
        mChart.setDescription("");
        mChart.setDrawYValues(true);
        mChart.setDrawCenterText(true);
        mChart.setDrawHoleEnabled(true);
        mChart.setRotationAngle(0);
        // draws the corresponding description value into the slice
        mChart.setDrawXValues(true);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        // display percentage values
        mChart.setUsePercentValues(true);
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);
        // add a selection listener
        //mChart.setOnChartValueSelectedListener(this);
        // mChart.setTouchEnabled(false);

        setData(5, 100);

        mChart.animateXY(1500, 1500);
        mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        l.setPosition(LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pie_chart, menu);
		return true;
	}

	private void setData(int count, float range) {

        float mult = range;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        
        yVals1.add(0, new Entry(25, 0));
        yVals1.add(1, new Entry(30, 0));
        yVals1.add(2, new Entry(25, 0));
        yVals1.add(3, new Entry(20, 0));
        
        
        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < count + 1; i++)
            xVals.add(mParties[i % mParties.length]);

        PieDataSet set1 = new PieDataSet(yVals1, "Election Results");
        set1.setSliceSpace(3f);
        
        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        
        colors.add(ColorTemplate.getHoloBlue());

        set1.setColors(colors);

        PieData data = new PieData(xVals, set1);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }
}
