package com.junnanhao.samantha.ui.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.junnanhao.samantha.R;
import com.junnanhao.samantha.model.entity.InfoBean;
import com.junnanhao.samantha.ui.adapter.RecyclerViewAdapter;
import com.junnanhao.samantha.ui.utils.PaddingItemDecoration;




import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;

/**
 * A placeholder fragment containing a simple view.
 */
public class CardsFragment extends Fragment {
    @BindView(R.id.recycler_view_cards) RecyclerView recyclerView;

    private Unbinder unbinder;
    private RecyclerViewAdapter mAdapter;

    public CardsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm realm = Realm.getDefaultInstance();
        mAdapter = new RecyclerViewAdapter(realm.where(InfoBean.class).findAllAsync());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cards, container, false);
        unbinder = ButterKnife.bind(this, view);
        setupRecyclerView();
        return view;
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.addItemDecoration(new PaddingItemDecoration(getActivity(), PaddingItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
