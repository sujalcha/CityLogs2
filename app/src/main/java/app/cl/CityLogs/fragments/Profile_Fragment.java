package app.cl.CityLogs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import app.cl.CityLogs.R;


public class Profile_Fragment extends Fragment {

    public static final String TAG = "Profile_Fragment";
    String errorMsg = "";
    EditText password, confirm_password, username;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_layout, container, false);
        password = (EditText) view.findViewById(R.id.editPassword);
        confirm_password = (EditText) view.findViewById(R.id.confirm_password);
        username = (EditText) view.findViewById(R.id.editUsername);

        ((Button) view.findViewById(R.id.save_profile)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String confirm_pass = confirm_password.getText().toString().trim();
                if (checkPasswordValidation(uname, pass, confirm_pass)) {
                    Toast.makeText(getActivity(), "Username and Password stored Successfully.", Toast.LENGTH_SHORT).show();

                    startFragmentActivity();
                } else {
                    Toast.makeText(getActivity(), "Fill all required fields correctly.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ((Button) view.findViewById(R.id.cancel_profile)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragmentActivity();
            }
        });

        return view;
    }

    private boolean checkPasswordValidation(String username, String password, String confirm_password) {
        if (username.isEmpty()) {
            this.username.setError("Username cannot be blank");
            return false;
        }
        if(password.isEmpty() || password.equals("")){
            this.password.setError("Fill All Fields");

            return false;
        }
        if(confirm_password.isEmpty() || password.equals("")||!password.equals(confirm_password)){
            this.confirm_password.setError("password does not match");

            return false;
        }

        return true;
    }



    private void startFragmentActivity() {
        Home_Fragment nextFrag = new Home_Fragment();
        this.getFragmentManager().beginTransaction().remove(this)
                .replace(R.id.container, nextFrag, nextFrag.TAG)
                .commit();
    }
}
