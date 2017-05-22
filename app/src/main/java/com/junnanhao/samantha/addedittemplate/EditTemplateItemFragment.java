package com.junnanhao.samantha.addedittemplate;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.junnanhao.samantha.R;

import butterknife.ButterKnife;



public class EditTemplateItemFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_template_add_edit_detail, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    public static EditTemplateItemFragment newInstance(int sectionNumber) {
        EditTemplateItemFragment fragment = new EditTemplateItemFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
