package com.example.AccountBook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by shijia on 2015/4/11.
 */
public class DetailOutlayFragment extends Fragment {
    private  String dateType;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dateType=getArguments().getString("date_type");
        View view=inflater.inflate(R.layout.fragment_detail_outlay,container,false);
        TextView textView= (TextView) view.findViewById(R.id.textView_fragment_detail_outlay);
        textView.setText(dateType);

        return view;
    }
}
