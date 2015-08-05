package ua.org.s4code.intellicalc;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity implements View.OnClickListener{

    Button btncalc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btncalc = (Button) findViewById(R.id.calculateButton);
        btncalc.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.calculateButton)
            // DO THE MAGIC TRICK
            Toast.makeText(this, "IT'S POINTLESS. CALCULATE IT YOURSELF", Toast.LENGTH_LONG).show();
    }
}
