package com.junnanhao.samantha.ui.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.junnanhao.samantha.R;
import com.junnanhao.samantha.entity.InfoBean;
import com.junnanhao.samantha.ui.adapter.RecyclerViewAdapter;
import com.junnanhao.samantha.ui.utils.PaddingItemDecoration;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A placeholder fragment containing a simple view.
 */
public class CardsFragment extends Fragment {
    @BindView(R.id.recycler_view_cards) RecyclerView recyclerView;

    private Unbinder unbinder;
    private RecyclerViewAdapter mAdapter;
    private List<InfoBean> mDataSet;

    public CardsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cards, container, false);
        unbinder = ButterKnife.bind(this, view);

        mDataSet = new ArrayList<>();
        SparseArray<String> data = new SparseArray<>();
        data.put(1, "hi");
        mDataSet.add(new InfoBean().type(0).data(data));
        mAdapter = new RecyclerViewAdapter( mDataSet);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new PaddingItemDecoration(getActivity(), PaddingItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
