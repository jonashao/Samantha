package com.junnanhao.samantha.addedittemplate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.junnanhao.samantha.R;
import com.junnanhao.samantha.model.entity.Raw;
import com.junnanhao.samantha.info.utils.PaddingItemDecoration;
import com.junnanhao.samantha.workflow.scanner.SmsScanner;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class SmsSelectActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    public static final String EXTRA_SMS_BODY = "body";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recycler_view);
        ButterKnife.bind(this);
        setupRecyclerView();
        Timber.d("on create");
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Raw> data = new SmsScanner(this).scan(true);
        recyclerView.addItemDecoration(new PaddingItemDecoration());
        recyclerView.setAdapter(new Adapter(data, new Adapter.SmsCallback() {
            @Override
            public void onSmsSelected(String body) {
                Intent data = new Intent();
                data.putExtra(EXTRA_SMS_BODY, body);
                setResult(RESULT_OK, data);
                finish();
            }
        }));
    }


    private static class Adapter extends RecyclerView.Adapter<ViewHolder> {
        private List<Raw> data;
        private SmsCallback callback;
        private PrettyTime prettyTime;

        Adapter(List<Raw> data, SmsCallback callback) {
            this.data = data;
            this.callback = callback;
            this.prettyTime = new PrettyTime();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sms, parent, false);
            return new ViewHolder(view, callback);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Raw raw = data.get(position);
            String subject = raw.subject();
            String sender = raw.sender().value();
            holder.sender.setText(subject != null ? subject : sender);
            holder.time.setText(prettyTime.format(raw.datetime()));
            holder.body.setText(raw.body());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        interface SmsCallback {
            void onSmsSelected(String body);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_sms_sender) TextView sender;
        @BindView(R.id.tv_sms_time) TextView time;
        @BindView(R.id.tv_sms_body) TextView body;
        Adapter.SmsCallback callback;

        ViewHolder(View itemView, Adapter.SmsCallback callback) {
            super(itemView);
            this.callback = callback;
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.card)
        void selectSms() {
            if (callback != null) {
                callback.onSmsSelected(body.getText().toString());
            }
        }

    }


}
