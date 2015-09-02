package ua.org.s4code.intellicalc.ui.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ua.org.s4code.intellicalc.R;
import ua.org.s4code.intellicalc.analyser.ExprContainer;
import ua.org.s4code.intellicalc.analyser.exception.ExprException;
import ua.org.s4code.intellicalc.analyser.function.power.Log;
import ua.org.s4code.intellicalc.analyser.value.Literal;

/**
 * Created by Serhii on 9/1/2015.
 */
public class ExprListAdapter extends BaseAdapter {

    private Context context;
    private ViewGroup parentContainer;
    private List<ExprContainer> exprList;
    private LayoutInflater inflater;

    /**
     * @param context application context
     * @param parent view to be parent for new view elements
     * @param elements list, containing view elements
     */
    public ExprListAdapter(Context context, ViewGroup parent, List<ExprContainer> elements) {
        super();
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        parentContainer = parent;
        exprList = elements;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View contentView = null;
        ExprContainer expr = exprList.get(position);

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            try {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                contentView = inflater.inflate(R.layout.history_record, null);
            } catch (Exception ex) {
                android.util.Log.e("adapter", ex.getMessage());
            }
        } else {
            contentView = convertView;
        }

        TextView textView = (TextView) contentView.findViewById(R.id.exprView);
        try {
            textView.setText(String.format("%s = %f", expr.toString(),
                    ((Literal) expr.getResult()).getValue()));
        } catch (ExprException ex) {
            // TODO: log the exception
            textView.setText(String.format("Can't equate '%s'. Exception: %s",
                    expr.toString(), ex.getMessage()));
            //textView.setSel
        }

        return textView;
    }

}
