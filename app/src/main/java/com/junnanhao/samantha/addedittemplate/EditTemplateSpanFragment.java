package com.junnanhao.samantha.addedittemplate;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.junnanhao.samantha.R;
import com.junnanhao.samantha.templates.TemplatesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class EditTemplateSpanFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    @BindView(R.id.section_label) TextView textView;

    public EditTemplateSpanFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static EditTemplateSpanFragment newInstance(int sectionNumber) {
        EditTemplateSpanFragment fragment = new EditTemplateSpanFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        textView.setText("label " + getArguments().getInt(ARG_SECTION_NUMBER));

        return rootView;
    }

}
