package in.mngo.gatesecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

public class ScanResult extends AppCompatActivity {
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);

        String scannedResult= getIntent().getStringExtra("scannedResult");

        Toast.makeText(ScanResult.this, scannedResult, Toast.LENGTH_SHORT).show();

    //checking QR Code
        String test[] = scannedResult.split("\n");
//        if(test.length == 3)
//        {
//            String key_name = scannedResult.split("\n")[0];
//            String key_roll = scannedResult.split("\n")[1];
//            String key_secret = scannedResult.split("\n")[2];
//
//        //checking if phone if connected to net or not
//            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
//            if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
//                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
//                try
//                {
//                    //checking if scanned key is a authorized key
//                    type = "check_if_key_is_authorized";
//                    String check_if_key_is_authorizedResult = new DatabaseActions().execute(type, key_name, key_secret).get();
//
//                    if(check_if_key_is_authorizedResult.equals("-1"))
//                    {
//                        scan_key_qr_feed.setText("Database issue found");
//                    }
//                    else if (check_if_key_is_authorizedResult.equals("Something went wrong"))
//                    {
//                        scan_key_qr_feed.setText(check_if_key_is_authorizedResult);
//                    }
//                    else if(check_if_key_is_authorizedResult.equals("0")) //invalid key //not authorized key
//                    {
//                        scan_key_qr_feed.setText("Invalid key!! \nThis key is not authorized.");
//                    }
//                    else if(check_if_key_is_authorizedResult.equals("1")) //everything is fine // key is authorized
//                    {
//                        //checking if key is already issued
//                        type = "check_key_issued";
//                        String check_key_issuedResult = new DatabaseActions().execute(type, key_name, key_secret).get();
//
//                        if(check_key_issuedResult.equals("-1"))
//                        {
//                            scan_key_qr_feed.setText("Database issue found");
//                        }
//                        else if (check_key_issuedResult.equals("Something went wrong"))
//                        {
//                            scan_key_qr_feed.setText(check_key_issuedResult);
//                        }
//                        else if(check_key_issuedResult.equals("1")) //key is already issued
//                        {
//                            scan_key_qr_feed.setText("This key has already been issued");
//                        }
//                        else if(check_key_issuedResult.equals("0")) //everything is fine // it can be issued
//                        {
//                            editor.putString("key_name", key_name);
//                            editor.putString("key_secret", key_secret);
//                            editor.apply();
//
//                            //redirecting the scan person qr page
//                            Intent IssueScanPersonIntent = new Intent(IssueScanKey.this, IssueScanPerson.class);
//                            startActivity(IssueScanPersonIntent);
//                            finish(); //used to delete the last activity history which we want to delete
//                        }
//                        else
//                        {
//                            //scan_key_qr_feed.setText(check_key_issuedResult);
//                            scan_key_qr_feed.setText("unKnown Error");
//                        }
//                    }
//                    else
//                    {
//                        //scan_key_qr_feed.setText(check_key_issuedResult);
//                        scan_key_qr_feed.setText("unKnown Error");
//                    }
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            else
//            {
//                scan_key_qr_feed.setText("Internet Connection is not available");
//            }
//        }
//        else
//        {
//            scan_key_qr_feed.setText("Wrong QR Code");
//        }

    }

    public void onStatusRadioButtonClicked(View view) {
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
}