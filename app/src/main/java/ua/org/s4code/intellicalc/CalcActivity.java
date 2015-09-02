package ua.org.s4code.intellicalc;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ua.org.s4code.intellicalc.analyser.ExprContainer;
import ua.org.s4code.intellicalc.analyser.Expression;
import ua.org.s4code.intellicalc.analyser.exception.ExprException;
import ua.org.s4code.intellicalc.ui.list.ExprListAdapter;


public class CalcActivity extends Activity implements View.OnClickListener{

    Button btnCalc;
    EditText expressionText;
    TextView resultView;
    ArrayList<ExprContainer> expressions;
    ExprListAdapter exprAdapter;
    ListView exprView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        expressions = new ArrayList<>();

        expressionText = (EditText) findViewById(R.id.exprInput);

        btnCalc = (Button) findViewById(R.id.calculateButton);
        btnCalc.setOnClickListener(this);

        resultView = (TextView) findViewById(R.id.expressionView);

        exprView = (ListView) findViewById(R.id.exprListView);
        exprAdapter = new ExprListAdapter(this, exprView, expressions);
        exprView.setAdapter(exprAdapter);
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
        if (v.getId() == R.id.calculateButton) {
            try {
                ExprContainer expr = Expression.parse(expressionText.getText().toString());
                // Expression result = expr.getResult();

                // TODO: Made clickable buttons for elements (on click expression is loaded to EditText again
                // resultView.setText(String.format("%s =\n%s", expr.toString(), result.toString()));

                expressions.add(0, expr);
                exprAdapter.notifyDataSetChanged();
            } catch (ExprException exception) {
                exception.selectText(expressionText);

                resultView.setText(String.format("Exception:\n%s", exception.getMessage()));
            }
            catch (Exception exception) {
                resultView.setText(String.format("General exception:\n%s", exception.getMessage()));
            }
        }
    }

}
