package in.mngo.gatesecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    RadioGroup serverRadio;
    Button continueBtn;
    TextView feed;

    JSONArray jsonArrayFromDatabase = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serverRadio = findViewById(R.id.serverRadio);
        continueBtn = findViewById(R.id.continueBtn);
        feed = findViewById(R.id.feed);

    //checking cookies
        sharedPreferences = this.getSharedPreferences("AppData", Context.MODE_PRIVATE );
        editor = sharedPreferences.edit();

    //checking server url
        String server_url = sharedPreferences.getString( "server_url", "DNE");
        if( !server_url.equals("DNE") ) {
            Intent HomeIntent = new Intent(MainActivity.this, Home.class);
            startActivity(HomeIntent);
        }

    //creating radio buttons
        jsonArrayFromDatabase.put("http://172.16.26.43");
//        jsonArrayFromDatabase.put("http://mngo.in");
        createRadioButton( serverRadio, "count", jsonArrayFromDatabase );

    //on clicking on continue btn
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String serverUrlChoosen = sharedPreferences.getString("server_url_choosen", "DNE");

                if( !serverUrlChoosen.equals("DNE") ) {
                    editor.putString( "server_url", serverUrlChoosen );
                    editor.apply();

                    Intent HomeIntent = new Intent(MainActivity.this, Home.class);
                    startActivity(HomeIntent);
                } else {
                    feed.setText("Please choose a valid server url");
                }
            }
        });
    }

//function to render radio buttons from json
    public void createRadioButton(final RadioGroup radioView, final String type, JSONArray jsonArrayFromDatabase ) {
        try {
            final RadioGroup rg = new RadioGroup(this); //create the RadioGroup

        //setting orientation of the radio group layout
            int orientation = RadioGroup.VERTICAL;
            rg.setOrientation(orientation);//or RadioGroup.VERTICAL

        //creating radio buttons
            for(int i = 0; i < jsonArrayFromDatabase.length(); i++ ) {
                final String address = jsonArrayFromDatabase.getString(i);

                final RadioButton rb = new RadioButton(this);
                rb.setText( address );

                rb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                editor.putString( "server_url_choosen", address );
                                editor.apply();
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
}