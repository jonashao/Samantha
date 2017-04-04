package com.junnanhao.samanthaviews;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

/**
 * Created by Jonas on 2017/3/31.
 * train ticket
 */

public class CardSurfaceContainer extends LinearLayout {
    public CardSurfaceContainer(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.train_ticket_card, this);
        setGravity(Gravity.CENTER);

    }
}
