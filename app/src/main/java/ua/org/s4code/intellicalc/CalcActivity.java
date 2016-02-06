package ua.org.s4code.intellicalc;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import ua.org.s4code.intellicalc.analyser.ExprContainer;
import ua.org.s4code.intellicalc.analyser.Expression;
import ua.org.s4code.intellicalc.analyser.exception.ExprException;
import ua.org.s4code.intellicalc.ui.fragment.ExprEditFragment;
import ua.org.s4code.intellicalc.ui.fragment.ExprFragmentData;
import ua.org.s4code.intellicalc.ui.fragment.ExprHistoryFragment;
import ua.org.s4code.intellicalc.ui.list.ExprHistoryListAdapter;


public class CalcActivity extends Activity implements IExprEditor, IExprHistorySaver {

    Button btnCalc;
    EditText expressionText;
    TextView resultView;
    ExprHistoryListAdapter exprAdapter;
    ListView exprView;

    ExprEditFragment exprEditFragment;
    ExprHistoryFragment exprHistoryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        FragmentManager fManager = getFragmentManager();
        exprEditFragment = (ExprEditFragment) fManager
                .findFragmentById(R.id.fragmentExprEdit);
        exprHistoryFragment = (ExprHistoryFragment) fManager
                .findFragmentById(R.id.fragmentExprHistory);

        /*FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        exprEditFragment = new ExprEditFragment();
        Bundle bundle = new Bundle();
        try {
            ExprFragmentData data = new ExprFragmentData(Expression.parse("2+2"));
            bundle.putParcelable("key", data);
        } catch (ExprException e) {
            // TODO: log an exception
            e.printStackTrace();
        }
        exprEditFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.fragmentEdit, exprEditFragment);
        exprHistoryFragment = new ExprHistoryFragment();
        fragmentTransaction.add(R.id.fragmentHistory, exprHistoryFragment);
        fragmentTransaction.commit();*/
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
    public void toEditor(ExprContainer expression) {
        exprEditFragment.toEditor(expression);
    }

    @Override
    public void toHistory(ExprContainer expression) {
        exprHistoryFragment.toHistory(expression);
    }
}
