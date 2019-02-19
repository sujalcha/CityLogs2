package app.cl.CityLogs.listmodel;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


import app.cl.CityLogs.R;


public class DBAdapter extends BaseAdapter implements View.OnClickListener {

    /***********
     * Declare Used Variables
     *********/
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater = null;
    public Resources res;
    ListModel tempValues = null;
    int i = 0;

    /**
     * DBAdapter Constructor
     */
    public DBAdapter(Activity activity, ArrayList data, Resources resources) {

        /********** Take passed values **********/
        this.activity = activity;
        this.data = data;
        res = resources;

        /**
         * Layout inflator to call external xml layout
         * */
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * What is the size of Passed Arraylist Size
     */
    public int getCount() {
        if (data.size() <= 0)
            return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    /**
     * Create a holder Class to contain inflated xml file elements
     */
    public static class ViewHolder {
        public TextView log_entry_datetime;
        public TextView log_entry_data;
    }

    /**
     * Depends upon data size called for each row , Create each ListView row
     */
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if (convertView == null) {
            /**
             * Inflate tabitem.xml file for each row ( Defined below )
             * */
            vi = inflater.inflate(R.layout.log_entries_layout, null);

            /**
             * View Holder Object to contain tabitem.xml file elements
             */
            holder = new ViewHolder();
            holder.log_entry_datetime = (TextView) vi.findViewById(R.id.log_entry_datetime);
            holder.log_entry_data = (TextView) vi.findViewById(R.id.log_entry_data);

            /**
             * Set holder with LayoutInflater
             */
            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();

        if (data.size() <= 0) {
            holder.log_entry_datetime.setText("No Data");
            holder.log_entry_datetime.setText("");
        } else {
            /**
             * Get each Model object from Arraylist
             */
            tempValues = null;
            tempValues = (ListModel) data.get(position);

            holder.log_entry_datetime.setText(tempValues.getDataTime().split(">")[0]);
            holder.log_entry_data.setText(tempValues.getDataTime().split(">")[1]);
            /**
             * Set Item Click Listner for LayoutInflater for each row
             */
            vi.setOnClickListener(new OnItemClickListener(position));
        }
        return vi;
    }

    @Override
    public void onClick(View v) {
        Log.v("DBAdapter", "=====Row button clicked=====");
    }

    /**
     * Called when Item click in ListView
     */
    private class OnItemClickListener implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {

        }
    }
}
