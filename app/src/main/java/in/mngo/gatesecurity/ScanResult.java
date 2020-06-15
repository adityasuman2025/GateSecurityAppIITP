package in.mngo.gatesecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class ScanResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);

        String scannedResult= getIntent().getStringExtra("scannedResult");

        Toast.makeText(ScanResult.this, scannedResult, Toast.LENGTH_SHORT).show();

    }
}