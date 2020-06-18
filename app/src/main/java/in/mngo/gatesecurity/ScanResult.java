package in.mngo.gatesecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ScanResult extends AppCompatActivity {
    RelativeLayout loadingAnimation;

    ImageView personImageView;
    TextView personNameTextView;
    TextView personIdTextView;
    TextView entryInfoFeed;
    LinearLayout entryInfoLayout;

    RadioGroup statusRadio;
    RadioGroup gateRadio;
    RadioGroup personCountRadio;
    RadioGroup reasonRadio;

    Button submitBtn;


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String type;
    JSONArray jsonArrayFromDatabase;
    String resultFromDatabase;

    Map<String, String> personEntryData = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);

        viewInitializer();

    //checking cookies
        sharedPreferences = this.getSharedPreferences("AppData", Context.MODE_PRIVATE );
        editor = sharedPreferences.edit();

    //checking QR Code
        String scannedResult= getIntent().getStringExtra("scannedResult");

        String test[] = scannedResult.split("\n");
        if(test.length == 3) {
            String key_name = test[0];
            final String key_roll = test[1];
            String key_secret = test[2];

        //setting name and id
            personNameTextView.setText(key_name);
            personIdTextView.setText(key_roll);
            personEntryData.put( "name", key_name );
            personEntryData.put( "roll", key_roll );

        //checking if phone if connected to net or not
            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //fetching data from database in another thread
                new Thread(new Runnable() {
                    public void run() {
                    //fetching person;s image
                        String type = "get_person_photo";
                        try {
                            Bitmap person_imageBitmap = new ServerActions().execute( type, key_roll.toLowerCase() ).get();
                            if( person_imageBitmap != null ) {
                                displayPersonImage( person_imageBitmap );
                            }
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                        //getting status from database
                            type = "get_status";

                            resultFromDatabase = new DatabaseActions().execute( type ).get();
                            if( resultFromDatabase.equals("0") ) {
                                setEntryInfoFeed("Failed to get status from database");
                            } else if( resultFromDatabase.equals("-100") ) {
                                setEntryInfoFeed("Database connection failed");
                            } else if( resultFromDatabase.equals("Something went wrong") ) {
                                setEntryInfoFeed("Something went wrong");
                            } else {
                            //parse JSON data
                                try {
                                    jsonArrayFromDatabase = new JSONArray( resultFromDatabase );
                                    createRadioButton( statusRadio, "status", RadioGroup.HORIZONTAL, jsonArrayFromDatabase );
                                } catch (JSONException ex) {
                                    ex.printStackTrace();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            //getting gates from database
                                type = "get_gates";
                                resultFromDatabase = new DatabaseActions().execute( type ).get();

                                if( resultFromDatabase.equals("0") ) {
                                    setEntryInfoFeed("Failed to get gates from database");
                                } else if( resultFromDatabase.equals("-100") ) {
                                    setEntryInfoFeed("Database connection failed");
                                } else if( resultFromDatabase.equals("Something went wrong") ) {
                                    setEntryInfoFeed("Something went wrong");
                                } else {
                                //parse JSON data
                                    try {
                                        jsonArrayFromDatabase = new JSONArray( resultFromDatabase );
                                        createRadioButton( gateRadio, "gate", RadioGroup.HORIZONTAL, jsonArrayFromDatabase );
                                    } catch (JSONException ex) {
                                        ex.printStackTrace();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                //getting person count from database
                                    type = "get_count_of_persons";
                                    resultFromDatabase = new DatabaseActions().execute( type ).get();

                                    if( resultFromDatabase.equals("0") ) {
                                        setEntryInfoFeed("Failed to get count of person from database");
                                    } else if( resultFromDatabase.equals("-100") ) {
                                        setEntryInfoFeed("Database connection failed");
                                    } else if( resultFromDatabase.equals("Something went wrong") ) {
                                        setEntryInfoFeed("Something went wrong");
                                    } else {
                                    //parse JSON data
                                        try {
                                            jsonArrayFromDatabase = new JSONArray( resultFromDatabase );
                                            createRadioButton( personCountRadio, "count", RadioGroup.VERTICAL, jsonArrayFromDatabase );
                                        } catch (JSONException ex) {
                                            ex.printStackTrace();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    //getting reasons from database
                                        type = "get_reasons";
                                        resultFromDatabase = new DatabaseActions().execute( type ).get();

                                        if( resultFromDatabase.equals("0") ) {
                                            setEntryInfoFeed("Failed to get reasons from database");
                                        } else if( resultFromDatabase.equals("-100") ) {
                                            setEntryInfoFeed("Database connection failed");
                                        } else if( resultFromDatabase.equals("Something went wrong") ) {
                                            setEntryInfoFeed("Something went wrong");
                                        } else {
                                            //parse JSON data
                                            try {
                                                jsonArrayFromDatabase = new JSONArray( resultFromDatabase );
                                                createRadioButton( reasonRadio, "reason", RadioGroup.VERTICAL, jsonArrayFromDatabase );
                                            } catch (JSONException ex) {
                                                ex.printStackTrace();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                        //displaying the entry info layout
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    entryInfoLayout.setVisibility(View.VISIBLE);
                                                    setEntryInfoFeed("");
                                                }
                                            });
                                        }
                                    }
                                }
                            }
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
            else {
                setEntryInfoFeed("Internet Connection is not available");
            }
        } else {
            setEntryInfoFeed("Invalid QR Code");
        }

    //clicking on submit btn
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( personEntryData.containsKey("name") && personEntryData.containsKey("roll") && personEntryData.containsKey("status") && personEntryData.containsKey("gate") && personEntryData.containsKey("count") && personEntryData.containsKey("reason") ) {
                    setEntryInfoFeed("");
                    loadingAnimation.setVisibility(View.VISIBLE);

                //checking if phone if connected to net or not
                    ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                    if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //sending data to database in a another thread
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    String name     = personEntryData.get("name");
                                    String roll     = personEntryData.get("roll");
                                    String status   = personEntryData.get("status");
                                    String gate     = personEntryData.get("gate");
                                    String count    = personEntryData.get("count");
                                    String reason   = personEntryData.get("reason");

                                    type = "insert_person_entry";

                                    resultFromDatabase = new DatabaseActions().execute( type, name, roll, status, gate, count, reason ).get();
                                    if (resultFromDatabase.equals("0")) {
                                        setEntryInfoFeed("Failed to get status from database");
                                    } else if (resultFromDatabase.equals("-100")) {
                                        setEntryInfoFeed("Database connection failed");
                                    } else if (resultFromDatabase.equals("-1")) {
                                        setEntryInfoFeed("Something went wrong");
                                    } else if (resultFromDatabase.equals("-20")) {
                                        setEntryInfoFeed("Invalid status");
                                    } else if (resultFromDatabase.equals("Something went wrong")) {
                                        setEntryInfoFeed("Something went wrong");
                                    } else if (resultFromDatabase.equals("1")) {
                                        setEntryInfoFeed("success");

                                    //redirecting to the success screen
                                        Intent SuccessIntent = new Intent( ScanResult.this, SuccessScreen.class );
                                        startActivity( SuccessIntent );
                                        finish();
                                    } else {
                                        setEntryInfoFeed("unknown error");
                                    }
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    } else {
                        setEntryInfoFeed("Internet Connection is not available");
                    }
                } else {
                    setEntryInfoFeed("Please fill all data");
                }
            }
        });
    }

//function to render radio buttons from json  coming from database
    private void createRadioButton(final RadioGroup radioView, final String type, int orientation, JSONArray jsonArrayFromDatabase ) {
        try {
            final RadioGroup rg = new RadioGroup(this); //create the RadioGroup
            rg.setOrientation(orientation);//or RadioGroup.VERTICAL

            JSONObject jo = null;
            for(int i = 0; i < jsonArrayFromDatabase.length(); i++ ) {
                jo = jsonArrayFromDatabase.getJSONObject(i);
                String id = jo.getString("id");
                final String value = jo.getString(type);

                RadioButton rb = new RadioButton(this);
                rb.setText( value );
                rb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                personEntryData.put( type, value );
                            }
                        });
                    }
                });
                rg.addView( rb );
            }

        //rendering radio buttons
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    radioView.addView( rg );//you add the whole RadioGroup to the layout
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

// displaying person's image
    private void displayPersonImage(final Bitmap person_imageBitmap ) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                personImageView.setImageBitmap( person_imageBitmap );
            }
        });
    }

//showing feed
    private void setEntryInfoFeed( final String text ) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                entryInfoFeed.setText( text );
                loadingAnimation.setVisibility(View.GONE);
            }
        });
    }

//function to initialize views
    private void viewInitializer() {
        loadingAnimation                = findViewById(R.id.loadingAnimation);

        personImageView                 = findViewById(R.id.personImageView);
        personNameTextView              = findViewById(R.id.personNameTextView);
        personIdTextView                = findViewById(R.id.personIdTextView);
        entryInfoFeed                   = findViewById(R.id.entryInfoFeed);
        entryInfoLayout                 = findViewById(R.id.entryInfoLayout);

        statusRadio                     = findViewById(R.id.statusRadio);
        gateRadio                       = findViewById(R.id.gateRadio);
        personCountRadio                = findViewById(R.id.personCountRadio);
        reasonRadio                     = findViewById(R.id.reasonRadio);

        submitBtn                       = findViewById(R.id.submitBtn);
    }
}