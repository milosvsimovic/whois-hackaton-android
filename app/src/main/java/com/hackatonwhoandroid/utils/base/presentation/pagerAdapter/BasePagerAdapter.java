package com.hackatonwhoandroid.utils.base.presentation.pagerAdapter;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BasePagerAdapter<FragmentT extends Fragment> extends FragmentPagerAdapter {

    private List<FragmentT> items;

    @SuppressLint("WrongConstant")
    public BasePagerAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.items = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    private boolean isPositionValid(int position) {
        return position >= 0 && position < getCount();
    }

    public void setItems(List<FragmentT> items) {
        this.items = items == null ? new ArrayList<>() : items;
        notifyDataSetChanged();
    }

    public List<FragmentT> getAll() {
        return items;
    }

    @NotNull
    @Override
    public FragmentT getItem(int position) {
        return items.get(position);
    }

    public void add(FragmentT item) {
        if (item != null) {
            items.add(item);
            notifyDataSetChanged();
        }
    }

    public void addAll(Collection<FragmentT> items) {
        if (items != null && items.size() > 0) {
            this.items.addAll(items);
            notifyDataSetChanged();
        }
    }

    @SuppressWarnings("unused")
    public void insert(int position, FragmentT item) {
        if (position >= 0 && position <= getCount() && item != null) {
            items.add(position, item);
            notifyDataSetChanged();
        }
    }

    public void update(int position, FragmentT item) {
        if (isPositionValid(position) && item != null) {
            items.set(position, item);
            notifyDataSetChanged();
        }
    }

    public FragmentT remove(int position) {
        FragmentT removed = null;
        if (isPositionValid(position)) {
            removed = items.remove(position);
            notifyDataSetChanged();
        }
        return removed;
    }

    public void clear() {
        this.items.clear();
        notifyDataSetChanged();
    }

    @BindingAdapter("fragments")
    public static <FragmentT extends Fragment> void bindFragments(@NonNull ViewPager viewPager, List<FragmentT> list) {
        if (viewPager.getAdapter() instanceof BasePagerAdapter) {
            //noinspection unchecked
            ((BasePagerAdapter<FragmentT>) viewPager.getAdapter()).setItems(list);
        }
    }

    @BindingAdapter("pageChangeListener")
    public static void bindPageChangeListener(@NonNull ViewPager viewPager, OnPageChangeListener listener) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (listener != null) {
                    listener.onPageChanged(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public interface OnPageChangeListener {
        void onPageChanged(int position);
    }

}
