package ua.org.s4code.intellicalc.ui.fragment;

import android.os.Parcel;
import android.os.Parcelable;

import ua.org.s4code.intellicalc.analyser.ExprContainer;

/**
 * Created by Serhii on 9/7/2015.
 */
public class ExprFragmentData implements Parcelable {

    public ExprContainer expression;

    public ExprFragmentData(ExprContainer expression) {
        this.expression = expression;
    }

    protected ExprFragmentData(Parcel in) {
        expression = (ExprContainer) in.readValue(ExprContainer.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(expression);
    }

    public static final Creator<ExprFragmentData> CREATOR = new Creator<ExprFragmentData>() {
        @Override
        public ExprFragmentData createFromParcel(Parcel in) {
            return new ExprFragmentData(in);
        }

        @Override
        public ExprFragmentData[] newArray(int size) {
            return new ExprFragmentData[size];
        }
    };

}
