package app.cl.CityLogs.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import app.cl.CityLogs.MainActivity;
import app.cl.CityLogs.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class Home_Fragment extends Fragment {

    public static final String TAG = "Home_Fragment";
    int currentPosition = 0;

    public Home_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_layout, container, false);



        ((Button) view.findViewById(R.id.city1))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startFragmentActivity(getActivity().getString(R.string.city101), 0);
                    }
                });

        ((Button) view.findViewById(R.id.city2))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startFragmentActivity(getActivity().getString(R.string.city102), 1);
                    }
                });

        ((Button) view.findViewById(R.id.city3))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startFragmentActivity(getActivity().getString(R.string.city103), 2);
                    }
                });

        ((Button) view.findViewById(R.id.city4))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startFragmentActivity(getActivity().getString(R.string.city104), 3);
                    }
                });

        ((Button) view.findViewById(R.id.city5))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startFragmentActivity(getActivity().getString(R.string.city105), 4);
                    }
                });
        return view;
    }

    private void startFragmentActivity(String description, int position) {
        City_Fragment nextFrag = new City_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.PAGETITLE, description);
        bundle.putString(MainActivity.STATUS, MainActivity.CREATE);
        bundle.putInt(MainActivity.CURRENTPAGE, position);
        nextFrag.setArguments(bundle);
        this.getFragmentManager().beginTransaction().remove(this)
                .replace(R.id.container, nextFrag, nextFrag.TAG)
                .commit();
    }
}
