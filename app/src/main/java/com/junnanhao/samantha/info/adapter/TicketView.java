package com.junnanhao.samantha.info.adapter;


import android.content.Context;
import android.support.annotation.StringRes;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.junnanhao.samantha.R;
import com.junnanhao.samantha.model.entity.InfoBean;
import com.junnanhao.samantha.model.entity.concept.ConceptDesc;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Setter;
import lombok.experimental.Accessors;

import static com.junnanhao.samanthaviews.R.layout.train_ticket_card;

/**
 * Created by Jonas on 2017/4/11.
 * hold train ticket views
 */

public class TicketView extends ConstraintLayout {
//
//    @BindView(R.id.tv_start_station) TextView startStation;
//    @BindView(R.id.tv_start_date) TextView startDate;
//    @BindView(R.id.tv_start_time) TextView startTime;
//    @BindView(R.id.tv_arrive_station) TextView arriveStation;
//    @BindView(R.id.tv_arrive_date) TextView arriveDate;
//    @BindView(R.id.tv_arrive_time) TextView arriveTime;
//    @BindView(R.id.tv_seat_number) TextView seatNumber;
//    @BindView(R.id.tv_travel_number) TextView travelNumber;
    @BindView(R.id.tv_select_destination) TextView setting;

    public TicketView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), train_ticket_card, this);
        ButterKnife.bind(this);
    }

    public static Builder builder(Context context) {
        return new Builder(context);
    }


    @Accessors(fluent = true)
    @Setter
    public static class Builder {
        private final Context context;
        private InfoBean bean;
//        private String startStation = getString(R.string.start_station);
//        private String arriveStation = getString(R.string.arrive_station);
//        private String startTime = getString(R.string.start_time);
//        private String arriveTime = getString(R.string.arrive_time);
//        private String seatNumber = getString(R.string.seat_number);
//        private String orderNumber = getString(R.string.order_number);
//        private String travelNumber = getString(R.string.travel_number);

        public Builder(Context context) {
            this.context = context;
        }

        protected TicketView build() {
            TicketView ticketView = new TicketView(context);
//            ticketView.startStation.setText(startStation);
//            ticketView.startTime.setText(startTime);
//            ticketView.arriveStation.setText(arriveStation);

            for (ConceptDesc description : bean.type().conceptDescs()) {
                String resName = description.identifier();
                String defType = description.resType();
                if (resName != null) {
                    TextView tv = (TextView) findViewByResName(ticketView, resName, defType);
                    String value = bean.valueOfConcept(description.concept());
                    if (tv != null && value != null) {
                        tv.setText(value);
                    } else {
                        if (tv != null) {
                            tv.setVisibility(View.INVISIBLE);
                        }
                        if (ticketView.setting != null) {
                            ticketView.setting.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            return ticketView;
        }

        String getString(@StringRes int resId) {
            return context.getString(resId);
        }

        protected int findIdByResName(String name, String defType) {
            int id = 0;
            if (name != null) {
                id = context.getResources().getIdentifier(name, defType, context.getPackageName());
            }
            return id;
        }

        View findViewByResName(ViewGroup container, String name, String defType) {
            int id = findIdByResName(name, defType);
            if (id != 0) {
                return ButterKnife.findById(container, id);
            } else return null;
        }
    }


}
