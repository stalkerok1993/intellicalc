package ua.org.s4code.intellicalc.ui.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;
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
        if (convertView == null) {
            try {
                contentView = inflater.inflate(R.layout.expr_history_list_item, null);
            } catch (Exception ex) {
                // TODO: log an exception
            }
        } else {
            contentView = convertView;
        }

        final ExprContainer expr = exprList.get(position);

        TextView textView = (TextView) contentView.findViewById(R.id.exprView);
        textView.setOnClickListener(new View.OnClickListener() {
            private final long TRASHOLD = 300;
            private long lastClickTime = -1;

            @Override
            public void onClick(View v) {
                long thisClickTime = (new Date()).getTime();

                if (thisClickTime - lastClickTime < TRASHOLD) { // is double click?
                    toEditor(expr);
                }

                lastClickTime = thisClickTime;
            }
        });
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                toEditor(expr);
                return true;
            }
        });
        try {
            textView.setText(String.format("%s =\n%f", expr.toString(),
                    ((Literal) expr.getResult()).getValue()));
        } catch (ExprException ex) {
            // TODO: select exception part in textView!
            textView.setText(String.format("Can't equate '%s'. Exception: %s",
                    expr.toString(), ex.getMessage()));
        }

        return contentView;
    }

    private void toEditor(ExprContainer expr) {
        if (exprEditor != null) {
            exprEditor.toEditor(expr);
        }
    }

}
