package ua.org.s4code.intellicalc.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ua.org.s4code.intellicalc.IExprEditor;
import ua.org.s4code.intellicalc.IExprHistorySaver;
import ua.org.s4code.intellicalc.R;
import ua.org.s4code.intellicalc.analyser.ExprContainer;
import ua.org.s4code.intellicalc.analyser.Expression;
import ua.org.s4code.intellicalc.analyser.IContextValue;
import ua.org.s4code.intellicalc.analyser.exception.ExprException;
import ua.org.s4code.intellicalc.ui.list.ExprParamListAdapter;

/**
 * Created by Serhii on 9/7/2015.
 */
public class ExprEditFragment extends Fragment implements IExprEditor {

    private ExprParamListAdapter paramListAdapter;
    private ExprContainer exprContainer;
    private View editFragmentView;
    private IExprHistorySaver historySaver;
    private EditText editExpression;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            if (activity instanceof IExprHistorySaver) {
                historySaver = (IExprHistorySaver) activity;
            }
        }
        catch (Exception ex) {
            Toast.makeText(getActivity().getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            editFragmentView = inflater.inflate(R.layout.expr_edit, container, false);
            editExpression = (EditText) editFragmentView.findViewById(R.id.editExpression);

            Bundle args = getArguments();
            if (args != null) {
                ExprFragmentData data = args.getParcelable("key");
                if (data != null) {
                    exprContainer = data.expression;
                    ListView listParameters = (ListView) editFragmentView.findViewById(R.id.listParameters);
                    List<IContextValue> contextValues = new ArrayList<>();
                    exprContainer.getUniqueContextValues(contextValues);
                    paramListAdapter = new ExprParamListAdapter(inflater.getContext(), R.layout.expr_parameter_list_value, listParameters, contextValues);

                    editExpression.setText(exprContainer.toString());
                }
            }

            Button buttonClear = (Button) editFragmentView.findViewById(R.id.buttonClear);
            buttonClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editExpression.setText("");
                }
            });

            Button buttonOptions = (Button) editFragmentView.findViewById(R.id.buttonOptions);
            buttonOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: create application menu
                }
            });

            Button buttonCalculate = (Button) editFragmentView.findViewById(R.id.buttonCalculate);
            buttonCalculate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getId() == R.id.buttonCalculate) {
                        try {
                            ExprContainer expr = Expression.parse(
                                    editExpression.getText().toString());
                            exprContainer = expr;
                            expr.getResult(); // calculate expression or throw an exception
                            historySaver.toHistory(expr);
                        } catch (ExprException exception) {
                            Toast.makeText(inflater.getContext(), exception.getMessage(),
                                    Toast.LENGTH_LONG);
                            exception.selectText(editExpression);
                        } catch (Exception exception) {
                            String message = String.format("General exception:\n%s",
                                    exception.getMessage());
                            Toast.makeText(inflater.getContext(), message, Toast.LENGTH_LONG);
                        }
                    }
                }
            });
        }
        catch (Exception ex) {
            Toast.makeText(getActivity().getApplicationContext(), ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

        return editFragmentView;
    }

    @Override
    public void toEditor(ExprContainer expression) {
        exprContainer = expression;
        editExpression.setText(exprContainer.toString());
        //TODO: set new parameters in parameter list
    }
}
