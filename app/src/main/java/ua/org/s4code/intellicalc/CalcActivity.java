package ua.org.s4code.intellicalc;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ua.org.s4code.intellicalc.analyser.ExprContainer;
import ua.org.s4code.intellicalc.analyser.Expression;
import ua.org.s4code.intellicalc.analyser.value.Literal;


public class CalcActivity extends Activity implements View.OnClickListener{

    Button btnCalc;
    EditText expressionText;
    TextView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        expressionText = (EditText) findViewById(R.id.expr_input);

        btnCalc = (Button) findViewById(R.id.calculate_button);
        btnCalc.setOnClickListener(this);

        resultView = (TextView) findViewById(R.id.result_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.calculate_button) {
            // DO THE MAGIC TRICK
            //Toast.makeText(this, "IT'S POINTLESS. CALCULATE IT YOURSELF", Toast.LENGTH_LONG).show();

            try {
                ExprContainer expr = Expression.parse(expressionText.getText().toString());
                Expression result = expr.getResult();

                if (result instanceof Literal) {
                    resultView.setText(String.format("%s =\n%f", expr.toString(),
                            ((Literal) result).getValue()));
                }
            } catch (Exception exception) {
                resultView.setText(String.format("Exception:\n%s", exception.getMessage()));
            }
        }
    }

}
