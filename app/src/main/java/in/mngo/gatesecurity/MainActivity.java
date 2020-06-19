package in.mngo.gatesecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    EditText serverUrlInput;
    Button continueBtn;
    TextView feed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serverUrlInput = findViewById(R.id.serverUrlInput);
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

    //on clicking on continue btn
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String serverUrl = serverUrlInput.getText().toString().trim();
                if( !serverUrl.equals("") ) {
                    editor.putString( "server_url", serverUrl );
                    editor.apply();

                    Intent HomeIntent = new Intent(MainActivity.this, Home.class);
                    startActivity(HomeIntent);
                } else {
                    feed.setText("Please enter a valid server url");
                }
            }
        });
    }
}