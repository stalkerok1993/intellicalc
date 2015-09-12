package ua.org.s4code.intellicalc.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

        if (activity instanceof IExprEditor) {
            exprEditor = (IExprEditor) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View exprHistory = inflater.inflate(R.layout.expr_history, container, false);
        ListView exprView = (ListView) exprHistory.findViewById(R.id.listHistory);
        historyExpressions = new ArrayList<>();
        exprAdapter = new ExprHistoryListAdapter(getActivity().getApplicationContext(),
                exprView, historyExpressions);
        exprAdapter.setEditor(exprEditor);
        exprView.setAdapter(exprAdapter);

        return exprHistory;
    }

    public void toHistory(ExprContainer expression) {
        historyExpressions.add(0, expression);
        exprAdapter.notifyDataSetChanged();
    }

}
