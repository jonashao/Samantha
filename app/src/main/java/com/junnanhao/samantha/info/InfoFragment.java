package com.junnanhao.samantha.info;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.junnanhao.samantha.R;
import com.junnanhao.samantha.model.entity.InfoBean;
import com.junnanhao.samantha.info.adapter.InfoBeanAdapter;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import timber.log.Timber;

/**
 * A placeholder fragment containing a simple view.
 */
public class InfoFragment extends Fragment {
    private static final String ARG_TYPE_ID = "type_id";
    @BindView(R.id.recycler_view) RealmRecyclerView recyclerView;

    private Unbinder unbinder;
    private InfoBeanAdapter mAdapter;
    private Realm realm;

    public InfoFragment() {
    }

    public static Fragment newInstance(int typeId) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE_ID, typeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        realm = Realm.getDefaultInstance();
        final int typeId = getArguments().getInt(ARG_TYPE_ID);
        RealmQuery<InfoBean> query = realm.where(InfoBean.class);
        if (typeId > 0) {
            query.equalTo("type.id", typeId);
        }
        if (typeId < 0) {
            query.equalTo("archived", true);
        } else {
            query.equalTo("archived", false);
        }
        Timber.d("typeId " + typeId);
        final RealmResults<InfoBean> results = query.findAllSorted("id");
        mAdapter = new InfoBeanAdapter(getContext(), results, true, true);
        mAdapter.setOnItemSwipedListener(new RealmBasedRecyclerViewAdapter.OnItemSwipedListener() {
            @Override
            public void onItemSwiped(int position) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                if (typeId < 0) {
                    results.deleteFromRealm(position);
                } else {
                    results.get(position).archived(true);
                }
                realm.commitTransaction();
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_today_frag, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
