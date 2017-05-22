package com.junnanhao.samantha.addedittemplate;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.junnanhao.samantha.R;
import com.junnanhao.samantha.app.base.FragmentTransactionHelper;

public class AddEditTemplateActivity extends AppCompatActivity {


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private FrameLayout mContainer;
    private boolean isFirstPage = true;
    private TransactionHelper transactionHelper;

    private static class TransactionHelper extends FragmentTransactionHelper {
        TransactionHelper(FragmentManager manager, int containerId) {
            super(manager, containerId);
        }

        @Override
        protected Fragment getItem(int itemId) {
            if (itemId == 1) {
                return EditTemplatePatternFragment.newInstance(1);
            } else if (itemId == 2) {
                return EditTemplateItemFragment.newInstance(1);
            }
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_template);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        transactionHelper = new TransactionHelper(getSupportFragmentManager(), R.id.container);
        transactionHelper.setCurrentItem(1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_edit_template, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_next_step) {
            transactionHelper.setCurrentItem(isFirstPage ? 2 : 1);
            item.setTitle(isFirstPage ? getString(R.string.action_last_step) : getString(R.string.action_next_step));
            isFirstPage = !isFirstPage;
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
