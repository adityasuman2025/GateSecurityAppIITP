package in.mngo.gatesecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class ScanResult extends AppCompatActivity {
    TextView personNameTextView;
    TextView personIdTextView;
    TextView entryInfoFeed;

    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);

        viewInitializer();

    //checking QR Code
        String scannedResult= getIntent().getStringExtra("scannedResult");

        String test[] = scannedResult.split("\n");
        if(test.length == 3)
        {
            String key_name = scannedResult.split("\n")[0];
            String key_roll = scannedResult.split("\n")[1];
            String key_secret = scannedResult.split("\n")[2];

        //setting name and id
            personNameTextView.setText(key_name);
            personIdTextView.setText(key_roll);

        //checking if phone if connected to net or not
            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
//                try
//                {
//                    //checking if scanned key is a authorized key
////                    type = "check_if_key_is_authorized";
////                    String check_if_key_is_authorizedResult = new DatabaseActions().execute(type, key_name, key_secret).get();
//
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
            else
            {
                setEntryInfoFeed("Internet Connection is not available");
            }
        }
        else
        {
            setEntryInfoFeed("Invalid QR Code");
        }
    }

    private void setEntryInfoFeed( String text ) {
        entryInfoFeed.setText("Wrong QR Code");
    }

    private void onStatusRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.statusRadioIn:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.statusRadioOut:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }

    private void viewInitializer() {
        personNameTextView = findViewById(R.id.personNameTextView);
        personIdTextView = findViewById(R.id.personIdTextView);
        entryInfoFeed = findViewById(R.id.entryInfoFeed);
    }
}