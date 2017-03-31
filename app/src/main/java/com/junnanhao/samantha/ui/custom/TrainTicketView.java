package com.junnanhao.samantha.ui.custom;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.junnanhao.samantha.R;

/**
 * Created by Jonas on 2017/3/31.
 */

public class TrainTicketView extends LinearLayout {
    public TrainTicketView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.content_card_train_ticket, this);
        setGravity(Gravity.CENTER);
    }
}
