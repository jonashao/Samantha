package com.junnanhao.samantha.templates;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.junnanhao.samantha.R;
import com.junnanhao.samantha.model.entity.template.Template;
import com.junnanhao.samantha.model.entity.template.TemplateItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmResults;
import timber.log.Timber;

/**
 * A placeholder fragment containing a simple view.
 */
public class TemplatesFragment extends Fragment implements TemplatesContract.View {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_INFO_TYPE = "info_type";

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private Unbinder unbinder;
    private TemplatesContract.Presenter mPresenter;
    private TemplatesAdapter mAdapter;
    private int infoType;


    public TemplatesFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TemplatesFragment newInstance(int infoType) {
        TemplatesFragment fragment = new TemplatesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INFO_TYPE, infoType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_recycler_view, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        Bundle arguments = getArguments();
        int infoType = arguments.getInt(ARG_INFO_TYPE);

        Realm realm = Realm.getDefaultInstance();
        Timber.d("type:%s", infoType);
        RealmResults<TemplateItem> results = realm.where(TemplateItem.class)
                .equalTo("template.type.id", infoType)
                .findAllSorted("id");
        mAdapter = new TemplatesAdapter(getContext(), results, false, false);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(TemplatesContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showTemplates(List<Template> templates) {

    }

    @Override
    public void showAddTemplate() {

    }

    @Override
    public void showTemplateDetailsUi(String templateId) {

    }

    @Override
    public void showLoadingTemplatesError() {

    }

    @Override
    public void showNoTemplates() {

    }

    @Override
    public void showSuccessfullySavedMessage() {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void showFilteringPopUpMenu() {

    }
}