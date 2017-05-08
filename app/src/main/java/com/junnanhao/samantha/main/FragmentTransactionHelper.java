package com.junnanhao.samantha.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.google.common.collect.ImmutableMap;
import com.junnanhao.samantha.R;
import com.junnanhao.samantha.info.InfoFragment;

/**
 * Created by Jonas on 2017/4/27.
 * manage fragment transactions.
 * to keep fragment alive
 */
public class FragmentTransactionHelper {

    private FragmentManager manager;
    private int containerId;
    private Fragment current;

    private ImmutableMap<Integer, Integer> resToTypeId = ImmutableMap.<Integer, Integer>builder()
            .put(R.id.nav_today, 0)
            .put(R.id.nav_trip, 1)
            .put(R.id.nav_other, 2)
            .put(R.id.nav_meeting, 3)
            .put(R.id.nav_bill, 4)
            .put(R.id.nav_archive, -1)
            .build();


    public FragmentTransactionHelper(FragmentManager manager, int containerId) {
        this.manager = manager;
        this.containerId = containerId;
    }

    public void setCurrentItem(int itemId) {
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
        fragmentTransaction.addToBackStack(name).commit();
    }

    private Fragment getItem(int itemId) {
        Integer typeId = resToTypeId.get(itemId);
        if (typeId != null) {
            return InfoFragment.newInstance(typeId);
        }
        return InfoBaseFragment.newInstance(itemId);
    }

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }
}
