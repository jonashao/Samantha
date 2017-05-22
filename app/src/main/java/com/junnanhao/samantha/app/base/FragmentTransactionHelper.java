package com.junnanhao.samantha.app.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.google.common.collect.ImmutableMap;
import com.junnanhao.samantha.R;
import com.junnanhao.samantha.info.InfoFragment;
import com.junnanhao.samantha.main.InfoBaseFragment;

/**
 * Created by Jonas on 2017/4/27.
 * manage fragment transactions.
 * to keep fragment alive
 */
public abstract class FragmentTransactionHelper {

    private FragmentManager manager;
    private int containerId;
    private Fragment current;

    public FragmentTransactionHelper(FragmentManager manager, int containerId) {
        this.manager = manager;
        this.containerId = containerId;
    }

    public void setCurrentItem(int itemId) {
        setCurrentItem(itemId, false);
    }

    public void setCurrentItem(int itemId, boolean addToStack) {
        String name = makeFragmentName(containerId, itemId);
        Fragment fragment = manager.findFragmentByTag(name);
        FragmentTransaction fragmentTransaction = manager.beginTransaction();

        if (fragment != null) {
            fragmentTransaction.attach(fragment);
            if (fragment.isHidden()) {
                fragmentTransaction.show(fragment);
            }
        } else {
            fragment = getItem(itemId);
            fragmentTransaction.add(containerId, fragment, name);
        }
        if (fragment != current && current != null) {
            fragmentTransaction.hide(current);
        }
        current = fragment;
        if (addToStack) {
            fragmentTransaction.addToBackStack(name);
        }
        fragmentTransaction.commit();
    }

    protected abstract Fragment getItem(int itemId);

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }
}
