package app.cl.CityLogs;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import app.cl.CityLogs.db.DBHandler;
import app.cl.CityLogs.fragments.Home_Fragment;
import app.cl.CityLogs.fragments.Profile_Fragment;
import app.cl.CityLogs.helper.CityLogs;
import app.cl.CityLogs.helper.Utils;

public class MainActivity extends AppCompatActivity {

    public static String PAGETITLE = "Page Title";
    public static String LOG_TYPE = "log_type";
    public static String STATUS = "Status";
    public static String CURRENTPAGE = "Current Page";
    public static String CREATE = "Create";
    public static String PREVIOUS = "previous";
    public static String NEXT = "next";
    public static String HOME = "home";

    DBHandler myDBHandler = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportFragmentManager().beginTransaction().add(R.id.container, new Home_Fragment(), Home_Fragment.TAG).commit();
        Utils.cityLogsArrayList = myDBHandler.getAllLogEntries("*");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.profile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new Profile_Fragment(), Profile_Fragment.TAG).commit();
            return true;
        } else if (id == R.id.save) {
            new DBHandler(MainActivity.this).insertdata();
            Toast.makeText(MainActivity.this,
                    "Logs Saved", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.send) {
            new myEmailSend().execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //back button pressed leading to this function
    private void opend() {
        AlertDialog.Builder d = new AlertDialog.Builder(
                MainActivity.this);
        d.setTitle("Save entries to Db first?");
        d.setIcon(R.mipmap.ic_launcher);
        d.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getSupportFragmentManager().popBackStack();
                finish();
            }
        });
        d.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new DBHandler(MainActivity.this).insertdata();
                Utils.tempcityLogsArrayList.clear();
                Toast.makeText(MainActivity.this,
                        "Logs Saved", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        AlertDialog alertDialog = d.create();
        alertDialog.show();
    }


    /**
     * For Send All Entries
     */
    private void openSendAllDialog() {
        AlertDialog.Builder d = new AlertDialog.Builder(MainActivity.this);
        d.setTitle("Are you ready to delete all entries?");
        d.setIcon(R.mipmap.ic_launcher);
        d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getSupportFragmentManager().popBackStack();
            }
        });
        d.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new DBHandler(MainActivity.this).dropAllData();
                Utils.tempcityLogsArrayList.clear();
                Utils.cityLogsArrayList.clear();
                Toast.makeText(MainActivity.this,
                        "Logs Cleared", Toast.LENGTH_LONG).show();


                Home_Fragment homeFrag = new Home_Fragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, homeFrag, homeFrag.TAG)
                        .commit();

            }
        });
        d.show();
    }


    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0)
            opend();
        else
            super.onBackPressed();
    }

    // assync task class to send all entries
    public class myEmailSend extends AsyncTask<Void, Void, String> {

        // progress dialog to show sending status of email while email send process is in background
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        StringBuilder log = new StringBuilder();
        @Override
        protected String doInBackground(Void... params) {

            int i = 0;
            for (CityLogs logs : Utils.cityLogsArrayList) {
                log.append(i +" "+logs.getCityname()+" "+logs.getTime()+ " "+logs.getContact()+ " "+
                        logs.getInvoice()+" "+logs.getDestination()+ "\n");
                i++;
            }
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Sending data...");
            progressDialog.setMessage("Please wait");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.hide();


            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto: zujalxtha@gmail.com"));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "New logger data");
            emailIntent.putExtra(Intent.EXTRA_CC, Uri.parse("mailto:zujalxtha@gmail.com "));
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Sujal" + "\n" + log.toString());
            startActivity(Intent.createChooser(emailIntent, "Send via:"));
            Utils.tempcityLogsArrayList.clear();
            Utils.cityLogsArrayList.clear();
            myDBHandler.dropAllData();
        }
    }







}

