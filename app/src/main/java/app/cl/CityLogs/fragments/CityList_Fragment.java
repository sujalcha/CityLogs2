package app.cl.CityLogs.fragments;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import app.cl.CityLogs.MainActivity;
import app.cl.CityLogs.R;
import app.cl.CityLogs.helper.Utils;
import app.cl.CityLogs.helper.CityLogs;


/**
 * A simple {@link Fragment} subclass.
 */
public class CityList_Fragment extends android.support.v4.app.ListFragment {

    public static final String TAG = "ShowLoggerEntries_Fragment";
    public ArrayList<String> CustomListViewValuesArr = new ArrayList<String>();
    String logType;
    int position;

    public CityList_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_citylist_layout, container, false);
        logType = this.getArguments().getString(MainActivity.LOG_TYPE);
        position = this.getArguments().getInt(MainActivity.CURRENTPAGE, 1);
        Button backButton = (Button) view.findViewById(R.id.returncity);
        backButton.setText(getString(R.string.return_to) + " " + logType);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragmentActivity(logType);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        /******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
        setListData(view);
        Resources res = getResources();
        ListView list = (ListView) view.findViewById(R.id.dataList);  // List defined in XML ( See Below )

        /**************** Create Custom Adapter *********/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,CustomListViewValuesArr);
        list.setAdapter(adapter);

    }

    // Function to set data in ArrayList

    public void setListData(View view) {
        for (CityLogs cityLog : Utils.cityLogsArrayList) {
            Log.e("city",cityLog.getLogType());
            Log.e("city",logType);
            if (cityLog.getLogType().equals(logType))
                CustomListViewValuesArr.add(cityLog.toString());
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

    private void startFragmentActivity(String description) {
        City_Fragment frag = new City_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.PAGETITLE, description);
        bundle.putString(MainActivity.STATUS, MainActivity.CREATE);
        bundle.putInt(MainActivity.CURRENTPAGE, position);
        frag.setArguments(bundle);
        this.getFragmentManager().beginTransaction().remove(this)
                .replace(R.id.container, frag, frag.TAG)
                .commit();
    }
}
