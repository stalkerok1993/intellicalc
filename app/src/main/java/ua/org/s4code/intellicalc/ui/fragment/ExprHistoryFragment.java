package ua.org.s4code.intellicalc.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ua.org.s4code.intellicalc.IExprEditor;
import ua.org.s4code.intellicalc.IExprHistorySaver;
import ua.org.s4code.intellicalc.R;
import ua.org.s4code.intellicalc.analyser.ExprContainer;
import ua.org.s4code.intellicalc.ui.list.ExprHistoryListAdapter;

/**
 * Created by Serhii on 9/7/2015.
 */
public class ExprHistoryFragment extends Fragment {

    private ArrayList<ExprContainer> historyExpressions;
    private ExprHistoryListAdapter exprAdapter;
    private IExprEditor exprEditor;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            if (activity instanceof IExprEditor) {
                exprEditor = (IExprEditor) activity;
            }
        }
        catch (Exception ex) {
            Toast.makeText(getActivity().getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View exprHistory = null;

        try {
            exprHistory = inflater.inflate(R.layout.expr_history, container, false);
            ListView exprView = (ListView) exprHistory.findViewById(R.id.listHistory);

            historyExpressions = new ArrayList<>();
            exprAdapter = new ExprHistoryListAdapter(getActivity().getApplicationContext(),
                    exprView, historyExpressions);
            exprAdapter.setEditor(exprEditor);
            exprView.setAdapter(exprAdapter);
        }
        catch (Exception ex) {
            Toast.makeText(getActivity().getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return exprHistory;
    }

    public void toHistory(ExprContainer expression) {
        try {
            historyExpressions.add(0, expression);
            exprAdapter.notifyDataSetChanged();
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(), ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

}
