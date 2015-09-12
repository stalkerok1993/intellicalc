package ua.org.s4code.intellicalc.ui.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ua.org.s4code.intellicalc.IExprEditor;
import ua.org.s4code.intellicalc.R;
import ua.org.s4code.intellicalc.analyser.ExprContainer;
import ua.org.s4code.intellicalc.analyser.exception.ExprException;
import ua.org.s4code.intellicalc.analyser.value.Literal;

/**
 * Created by Serhii on 9/1/2015.
 */
public class ExprHistoryListAdapter extends BaseAdapter {

    private Context context;
    private ViewGroup parentContainer;
    private List<ExprContainer> exprList;
    private LayoutInflater inflater;
    private IExprEditor exprEditor;

    public void setEditor(IExprEditor editor) {
        exprEditor = editor;
    }

    /**
     * @param context application context
     * @param parent view to be parent for new view elements
     */
    public ExprHistoryListAdapter(Context context, ViewGroup parent, List<ExprContainer> exprList) {
        this.context = context;
        this.exprList = exprList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        parentContainer = parent;
    }

    @Override
    public int getCount() {
        return exprList.size();
    }

    @Override
    public Object getItem(int position) {
        return exprList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        View contentView = null;
        final ExprContainer expr = exprList.get(position);

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            try {
                contentView = inflater.inflate(R.layout.expr_history_list_item, null);

                if (contentView != null) {
                    final View buttonLoad = contentView.findViewById(R.id.buttonLoad);
                    final View exprView = contentView.findViewById(R.id.exprView);
                    exprView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (int i = 0; i < parentContainer.getChildCount(); i++) {
                                View child = parentContainer.getChildAt(i).findViewById(R.id.buttonLoad);
                                if (child instanceof Button) {
                                    child.setVisibility(View.INVISIBLE);
                                }
                            }

                            buttonLoad.setVisibility(View.VISIBLE);
                        }
                    });
                }
            } catch (Exception ex) {
                // TODO: log an exception
            }
        } else {
            contentView = convertView;
        }

        TextView textView = (TextView) contentView.findViewById(R.id.exprView);
        try {
            textView.setText(String.format("%s =\n%f", expr.toString(),
                    ((Literal) expr.getResult()).getValue()));
        } catch (ExprException ex) {
            // TODO: select exception part in textView!
            textView.setText(String.format("Can't equate '%s'. Exception: %s",
                    expr.toString(), ex.getMessage()));
        }

        View buttonLoad = contentView.findViewById(R.id.buttonLoad);
        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exprEditor != null) {
                    exprEditor.toEditor(expr);
                }
            }
        });

        return contentView;
    }

}
