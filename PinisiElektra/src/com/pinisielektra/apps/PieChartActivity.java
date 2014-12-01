package com.pinisielektra.apps;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendPosition;
import com.pinisielektra.apps.object.InventoryObj;
import com.pinisielektra.apps.object.PembelianObj;

public class PieChartActivity extends ActionBarActivity {

	private PieChart mChart;
	private ArrayList<Object> mIntentItemId;
	private String mIntentTotal;
	private int mIntentMenuId;
    private ArrayList<String> arrItemName;
    private ArrayList<String> arrItemSatuan;
    
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pie_chart);
		
		mIntentItemId = (ArrayList<Object>) getIntent().getExtras().getSerializable("pie_chart_data");
		mIntentTotal = getIntent().getExtras().getString("total_satuan");
		mIntentMenuId = getIntent().getExtras().getInt("id_menu");
		
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

        arrItemName = new ArrayList<String>();
        arrItemSatuan = new ArrayList<String>();
        
        for (int i=0; i<mIntentItemId.size(); i++) {
        	switch (mIntentMenuId) {
			case 1:
//	        	arrItemName.add(((PenjualanObj)mIntentItemId.get(i)).getKodeBarang());
//	        	arrItemSatuan.add(((PenjualanObj)mIntentItemId.get(i)).getSatuan());				
				break;
			case 2:
	        	arrItemName.add(((PembelianObj)mIntentItemId.get(i)).getKodeBarang());
	        	arrItemSatuan.add(((PembelianObj)mIntentItemId.get(i)).getSatuan());
				break;
			case 3:
	        	arrItemName.add(((InventoryObj)mIntentItemId.get(i)).getNamaBarang());
	        	arrItemSatuan.add(((InventoryObj)mIntentItemId.get(i)).getSatuan());
				break;
			case 4:
//	        	arrItemName.add(((PelangganObj)mIntentItemId.get(i)).getKodeBarang());
//	        	arrItemSatuan.add(((PelangganObj)mIntentItemId.get(i)).getSatuan());
				break;
			case 5:
//	        	arrItemName.add(((DistributorObj)mIntentItemId.get(i)).getKodeBarang());
//	        	arrItemSatuan.add(((DistributorObj)mIntentItemId.get(i)).getSatuan());
				break;
			default:
				break;
			}
        }
        
        setData(mIntentItemId.size(), Integer.valueOf(mIntentTotal));

        mChart.animateXY(1500, 1500);
        mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        l.setPosition(LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean itemSelected = false;
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			itemSelected = true;
			break;	
		default:
			break;
		}
		return itemSelected;
	}
	
	private void setData(int count, float range) {

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        for (int j=0; j<arrItemSatuan.size(); j++) {
        	yVals1.add(j, new Entry(Float.parseFloat(arrItemSatuan.get(j)), 0));
        }
        
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count + 1; i++)
            xVals.add(arrItemName.get(i % arrItemName.size()));

        PieDataSet set1 = new PieDataSet(yVals1, "");
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
