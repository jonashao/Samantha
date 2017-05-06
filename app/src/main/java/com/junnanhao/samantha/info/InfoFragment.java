package com.junnanhao.samantha.info;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.junnanhao.samantha.R;
import com.junnanhao.samantha.info.utils.SwipeDismissListViewTouchListener;
import com.junnanhao.samantha.model.entity.InfoBean;
import com.junnanhao.samantha.info.adapter.InfoBeanAdapter;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmQuery;
import timber.log.Timber;

/**
 * A placeholder fragment containing a simple view.
 */
public class InfoFragment extends Fragment {
    private static final String ARG_TYPE_ID = "type_id";
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private Unbinder unbinder;
    private InfoBeanAdapter mAdapter;

    public InfoFragment() {
    }


    public static Fragment newInstance(int typeId) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE_ID, typeId);
        Timber.d("new cards instance " + fragment.getId());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int typeId = getArguments().getInt(ARG_TYPE_ID);
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<InfoBean> query = realm.where(InfoBean.class);
        if (typeId != 0) {
            query.equalTo("type.id", typeId);
        }
        mAdapter = new InfoBeanAdapter(query.findAllSorted("id"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_recycler_view, container, false);
        unbinder = ButterKnife.bind(this, view);
        setupRecyclerView();
        return view;
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(recyclerView,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(RecyclerView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    mAdapter.remove(position);
                                }
                            }
                        });
        recyclerView.setOnTouchListener(touchListener);
        recyclerView.addOnScrollListener(touchListener.makeScrollListener());
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
