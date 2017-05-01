package com.junnanhao.samantha.addedittemplate;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.junnanhao.samantha.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * A placeholder fragment containing a simple view.
 */
public class EditTemplatePatternFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    @BindView(R.id.editText_template_pattern) EditText editPattern;
    @BindView(R.id.viewPager_container) ViewPager viewPager;

    public EditTemplatePatternFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static EditTemplatePatternFragment newInstance(int sectionNumber) {
        EditTemplatePatternFragment fragment = new EditTemplatePatternFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_template_add_edit, container, false);
        ButterKnife.bind(this, rootView);

        editPattern.setCustomSelectionActionModeCallback(callback);
        return rootView;
    }

    ActionMode.Callback callback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater menuInflater = mode.getMenuInflater();
            menuInflater.inflate(R.menu.menu_edit_template_text_selection, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            CharacterStyle style;
            int start = editPattern.getSelectionStart();
            int end = editPattern.getSelectionEnd();
            Timber.d("start:%d end:%d", start, end);
            SpannableStringBuilder ssb = new SpannableStringBuilder(editPattern.getText());

            switch (item.getItemId()) {
                case R.id.tag:
                    style = new BackgroundColorSpan(getResources().getColor(R.color.green_dark));
                    ssb.setSpan(style, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    ssb.setSpan(new ClickableSpan() {
//                        @Override
//                        public void onClick(View widget) {
//                            viewPager.setAdapter(new SectionsPagerAdapter(getChildFragmentManager()));
//                        }
//                    }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    editPattern.setMovementMethod(LinkMovementMethod.getInstance());
                    editPattern.setText(ssb);
                    viewPager.setAdapter(new SectionsPagerAdapter(getChildFragmentManager()));
//                    mode.finish();//收起操作菜单
                    break;
            }
            return false;//返回true则系统的"复制"、"搜索"之类的item将无效，只有自定义item有响应
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
        }
    };


    private static class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a TemplatesFragment (defined as a static inner class below).
            return EditTemplateSpanFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

    }
}
