package com.junnanhao.samantha.addedittemplate;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.SparseArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListAdapter;

import com.junnanhao.samantha.R;
import com.junnanhao.samantha.model.entity.concept.Concept;
import com.junnanhao.samantha.model.entity.concept.ConceptDesc;
import com.junnanhao.samantha.model.entity.template.ConceptFormat;
import com.junnanhao.samanthaviews.util.ColorUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import timber.log.Timber;

import static android.app.Activity.RESULT_OK;

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
    @BindViews({R.id.autoCompleteTextView, R.id.autoCompleteTextView2}) List<AutoCompleteTextView> autoCompleteTextViews;
    private SparseArray<ConceptFormat> conceptFormatSparseArray = new SparseArray<>(5);
    private SparseArray<ConceptDesc> conceptDescSparseArray = new SparseArray<>(5);
    private SpansPagerAdapter pagerAdapter;
    private int currentKey = 0;
    private ConceptFormat currentConceptFormat = null;
    private ConceptDesc currentConceptDesc = null;

    public static final int REQUEST_SMS_SELECT = 16;

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
        pagerAdapter = new SpansPagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        editPattern.setCustomSelectionActionModeCallback(callback);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            editPattern.setCustomInsertionActionModeCallback(insertCallback);
        }

        ButterKnife.apply(autoCompleteTextViews, new ButterKnife.Setter<AutoCompleteTextView, Object>() {
            @Override
            public void set(@NonNull AutoCompleteTextView view, Object value, int index) {
            }
        },null );
        return rootView;
    }

    ActionMode.Callback insertCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater menuInflater = mode.getMenuInflater();
            menuInflater.inflate(R.menu.menu_edit_template_text_selection_insert, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.fromSms) {
                Intent intent = new Intent(getContext(), SmsSelectActivity.class);
                startActivityForResult(intent, REQUEST_SMS_SELECT);
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SMS_SELECT) {
                String body = data.getExtras().getString(SmsSelectActivity.EXTRA_SMS_BODY);
                if (body != null && body.length() > 0) {
                    editPattern.setText(body);
                }
            }
        }
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
            int start = editPattern.getSelectionStart();
            int end = editPattern.getSelectionEnd();
            currentKey = (start << 16) | end;
            currentConceptFormat = conceptFormatSparseArray.get(currentKey);
            currentConceptDesc = conceptDescSparseArray.get(currentKey);

            SpannableStringBuilder ssb = new SpannableStringBuilder(editPattern.getText());

            switch (item.getItemId()) {
                case R.id.tag:
                    int backgroundColor = getResources().getColor(R.color.green_dark);
                    final int foregroundColor = ColorUtils.isColorDark(backgroundColor) ? Color.WHITE : Color.BLACK;

                    ssb.setSpan(new BackgroundColorSpan(backgroundColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ssb.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(View widget) {
                            setCurrentSpan(currentConceptFormat, currentConceptDesc);
                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            super.updateDrawState(ds);
                            ds.setUnderlineText(false);
                            ds.setColor(foregroundColor);
                        }
                    }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    ssb.setSpan(new ForegroundColorSpan(foregroundColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    Timber.d("is span color dark:", ColorUtils.isColorDark(backgroundColor));

                    editPattern.setMovementMethod(LinkMovementMethod.getInstance());
                    editPattern.setText(ssb);


                    if (currentConceptFormat == null) {
                        currentConceptFormat = new ConceptFormat();
                        conceptFormatSparseArray.put(currentKey, currentConceptFormat);
                    }
                    if (currentConceptDesc == null) {
                        currentConceptDesc = new ConceptDesc();
                        conceptDescSparseArray.put(currentKey, currentConceptDesc);
                    }

                    setCurrentSpan(currentConceptFormat, currentConceptDesc);

//                    mode.finish();//收起操作菜单
                    break;
            }
            return false;//返回true则系统的"复制"、"搜索"之类的item将无效，只有自定义item有响应
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
        }
    };


    private void setCurrentSpan(ConceptFormat format, ConceptDesc desc) {
        if (format != null && format.concept() != null && format.concept().meaning() != null) {
            Timber.d("set span | currentConceptFormat:%s", currentConceptFormat);
            autoCompleteTextViews.get(0).setText(format.concept().meaning());
        } else {
            autoCompleteTextViews.get(0).setText("");
        }

        if (desc != null && desc.property() != null) {
            autoCompleteTextViews.get(1).setText(desc.property());
        } else {
            autoCompleteTextViews.get(1).setText(getText(R.string.hint_template_selection_meta));
        }
    }

    @OnTextChanged(R.id.autoCompleteTextView)
    void updateMeaning(CharSequence charSequence) {
        Timber.d("text changed:%s", charSequence.toString());
        Timber.d("currentConceptFormat:%s", currentConceptFormat);
        if (currentConceptFormat != null && charSequence.length() > 0) {
            currentConceptFormat.concept(new Concept().meaning(charSequence.toString()));
        }
    }

    private static class SpansPagerAdapter extends PagerAdapter {


        SpansPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return container.getChildAt(position);
        }

        public void initAutoEditText(AutoCompleteTextView autoCompleteTextView) {
            autoCompleteTextView.requestFocus();
            autoCompleteTextView.setCursorVisible(true);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}
