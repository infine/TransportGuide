package com.map.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.map.reading.Routing;

import android.app.ListActivity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

public class RouteActivity extends ListActivity {

	private List<HashMap<String, String>> busRoutingList = new ArrayList<HashMap<String, String>>();
	private String KEY_Station = "busStation", KEY_Line = "busLine",
			KEY_Message = "message";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		busRoutingList = Routing.getValues();
		
		// timeTable1.add(timeTable);
		ListAdapter mSchedule = new SimpleAdapter(this, busRoutingList,
				R.layout.list_item, new String[] { KEY_Message, KEY_Station,
						KEY_Line }, new int[] { R.id.message, R.id.name,
						R.id.cost });
		setListAdapter(mSchedule);
	}
}
