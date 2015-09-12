package ua.org.s4code.intellicalc.ui.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Collection;
import java.util.List;

import ua.org.s4code.intellicalc.R;
import ua.org.s4code.intellicalc.analyser.IContextValue;
import ua.org.s4code.intellicalc.analyser.exception.ExprException;
import ua.org.s4code.intellicalc.analyser.value.Literal;

/**
 * Created by Serhii on 9/7/2015.
 */
public class ExprParamListAdapter extends BaseAdapter {

    private List<IContextValue> valueList;
    private Context context;
    private LayoutInflater inflater;
    private ViewGroup parentView;
    private int listItemLayout;

    public ExprParamListAdapter(Context context, int listItemLayout, ViewGroup parent, List<IContextValue> list) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listItemLayout = listItemLayout;
        parentView = parent;
        valueList = list;
    }

    @Override
    public int getCount() {
        return valueList.size();
    }

    @Override
    public Object getItem(int position) {
        return valueList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IContextValue expr = valueList.get(position);
        View contentView = null;

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            /* // not used, but in emulator it's necessary
            if (inflater == null) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }*/

            // contentView = inflater.inflate(R.layout.expr_history_list_item, null);
            View parametersView = inflater.inflate(listItemLayout, parentView, false);
        } else {
            contentView = convertView;
        }

        return contentView;
    }
}
