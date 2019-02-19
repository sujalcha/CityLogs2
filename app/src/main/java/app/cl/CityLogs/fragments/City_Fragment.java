package app.cl.CityLogs.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import app.cl.CityLogs.MainActivity;
import app.cl.CityLogs.R;
import app.cl.CityLogs.db.DBHandler;
import app.cl.CityLogs.helper.CityLogs;
import app.cl.CityLogs.helper.TrackGPS;
import app.cl.CityLogs.helper.Utils;

import static app.cl.CityLogs.R.id.spinner;


public class City_Fragment extends Fragment {

    public static final String TAG = "SaveEntries_Fragment";
    DBHandler dbHandler;

    String logType;
    Integer currentPage;
    EditText timetxt, contacttxt, invoicetxt;
    Button timebtn;
    String selected_item;
    TextView titleTextView;
    Spinner s1;

    public City_Fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_city_layout, container, false);

        titleTextView = (TextView) view.findViewById(R.id.data_entry_title);

        contacttxt = (EditText) view.findViewById(R.id.contact_edittext);
        invoicetxt = (EditText) view.findViewById(R.id.invoice_edittext);
        timetxt = (EditText) view.findViewById(R.id.time_edittext);


        timebtn = (Button) view.findViewById(R.id.time_button);



        logType = this.getArguments().getString(MainActivity.PAGETITLE);
        currentPage = this.getArguments().getInt(MainActivity.CURRENTPAGE, 1);
        titleTextView.setText(logType);
        String status = this.getArguments().getString(MainActivity.STATUS);

        timebtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                timetxt.setVisibility(View.VISIBLE);

                timebtn.setVisibility(View.INVISIBLE);

                TrackGPS gps = new TrackGPS(getActivity());


                if (gps.canGetLocation()) {


                    double longitude = gps.getLongitude();
                    double latitude = gps.getLatitude();

                    long date = System.currentTimeMillis();
                    SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy hh:mm");
                    String dateString = sdf.format(date) + " Longitude: " +longitude+ " Latitude: " + latitude;
                    timetxt.setText(dateString);;


                }


            }

        });



        ((Button) view.findViewById(R.id.save_log_entry)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLogEntriesFragmentActivity(view);

                            }
        });


        ((Button) view.findViewById(R.id.show_log_entries)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogDetails();
            }
        });

        ((Button) view.findViewById(R.id.data_entry_previous)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragmentActivity(MainActivity.PREVIOUS);
                timebtn.setVisibility(View.VISIBLE);

                timetxt.setVisibility(View.INVISIBLE);

            }
        });

        ((Button) view.findViewById(R.id.data_entry_next)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragmentActivity(MainActivity.NEXT);
                timebtn.setVisibility(View.VISIBLE);

                timetxt.setVisibility(View.INVISIBLE);

            }
        });

        ((Button) view.findViewById(R.id.data_entry_home)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragmentActivity(MainActivity.HOME);
            }
        });
        return view;
    }

    private void saveLogEntriesFragmentActivity(View view) {
        dbHandler = new DBHandler(view.getContext());

        /**
         * Get Input Value
         */
        String cityname = titleTextView.getText().toString().trim();
        String contact = contacttxt.getText().toString().trim();
        String invoice = invoicetxt.getText().toString().trim();
        String time = timetxt.getText().toString().trim();
        String destination = selected_item;

        /**
         * Validate input Log Entry
         */
       if (Utils.validateInputData(contact, invoice, time, destination))

        {

            CityLogs citylog = new CityLogs();

            citylog.setCityname(cityname);
            citylog.setContact(contact);
            citylog.setInvoice(invoice);
            citylog.setTime(time);
            citylog.setDestination(destination);

            citylog.setLogType(logType);
            citylog.setCreatedDate(getCurrentDateTime());
            citylog.setUpdatedDate("");
            Utils.tempcityLogsArrayList.add(citylog);
            Utils.cityLogsArrayList.add(citylog);

            setEditText(false);


            Toast.makeText(getActivity(), "Log Entry Succeded", Toast.LENGTH_SHORT).show();
        }
        else {
            setEditText(true);
            Toast.makeText(getActivity(), "Entry not saved as not all data entered. Complete all the entries and try again", Toast.LENGTH_SHORT).show();
        }
    }

    private void setEditText(boolean error) {
        if (error) {
            contacttxt.setError(Utils.errorMap.get("Contact"));
            invoicetxt.setError(Utils.errorMap.get("Invoice"));
            timebtn.setError(Utils.errorMap.get("Time"));


        } else {
            contacttxt.setText("");
            invoicetxt.setText("");
            timetxt.setText("");

            s1.setSelection(0);

            timebtn.setVisibility(View.VISIBLE);

            timetxt.setVisibility(View.INVISIBLE);

        }
        Utils.errorMap.clear();
    }


    /**
     * get current date time in dd/MM/yyyy:hh:mm:ss format
     *
     * @return String
     */
    private String getCurrentDateTime() {
        DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy:hh:mm:ss ");
        dateFormatter.setLenient(false);
        Date today = new Date();
        return dateFormatter.format(today);
    }


    /**
     * Starting Log Entries Fragment Activity
     */
    private void showLogDetails() {

        CityList_Fragment showLoggerEntries = new CityList_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.LOG_TYPE, logType);
        bundle.putInt(MainActivity.CURRENTPAGE, currentPage);
        showLoggerEntries.setArguments(bundle);
        this.getFragmentManager().beginTransaction()
                .replace(R.id.container, showLoggerEntries, showLoggerEntries.TAG)
                .commit();
    }


    /**
     * Starting Home Page Fragment Activity
     */
    private void startFragmentActivity(String button) {

        if (button.equals(MainActivity.NEXT))
            currentPage = Utils.getNextPosition(currentPage);
        else if (button.equals(MainActivity.PREVIOUS))
            currentPage = Utils.getPreviousPosition(currentPage);

        logType = Utils.menuList.get(currentPage);
        titleTextView.setText(logType);
        setEditText(false);
        if (button.equals(MainActivity.HOME)) {
            Home_Fragment homeFrag = new Home_Fragment();
            this.getFragmentManager().beginTransaction().replace(R.id.container, homeFrag, homeFrag.TAG)
                    .commit();
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();

        final String[] cityhost;
        cityhost = getResources().getStringArray(R.array.cities);

        s1 = (Spinner) getActivity().findViewById(spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, cityhost);
        s1.setAdapter(adapter);
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,int position, long id) {
                // On selecting a spinner item
                selected_item = adapter.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }


}
