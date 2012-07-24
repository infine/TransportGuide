package com.map.app;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class AndroidXMLParsingActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);

		// Adding menuItems to ListView
		ArrayAdapter<BusStation> arrayAdapter = new ArrayAdapter<BusStation>(
				this, android.R.layout.simple_list_item_1,
				AndroidXMLParsing.getValues());
		setListAdapter(arrayAdapter);
	}

}
