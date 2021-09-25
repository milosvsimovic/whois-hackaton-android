package com.hackatonwhoandroid.presentation;

import android.graphics.PorterDuff;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.utils.ShapeImageView;

import org.apache.commons.lang3.StringUtils;

public class BindingAdapters {

    @BindingAdapter({"text"})
    public static void bindText(TextView textView, @StringRes int res) {
        if (res != 0) {
            textView.setText(res);
        }
    }

    @BindingAdapter(value = {"visibility", "invisibility"}, requireAll = false)
    public static void bindVisibility(@NonNull View view, @Nullable Boolean visibility, @Nullable Boolean invisibility) {
        if (visibility != null) {
            view.setVisibility(visibility ? View.VISIBLE : invisibility != null && invisibility ? View.INVISIBLE : View.GONE);
        }
    }

    @BindingAdapter({"imageColor"})
    public static void bindImageColor(ImageView imageView, @ColorInt int color) {
        imageView.setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }

    @BindingAdapter("onItemSelected")
    public static void bindSpinnerListener(Spinner spinner, OnItemSelectedListener listener) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null) {
                    listener.onItemSelected(parent.getSelectedItem());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });
    }

    @BindingAdapter("selected")
    public static void bindSelected(Spinner spinner, String value) {
        if (StringUtils.isNotEmpty(value)) {
            int position = 0;
            for (int i = 0; i < spinner.getCount(); i++) {
                if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(value)) {
                    position = i;
                    break;
                }
            }
            spinner.setSelection(position);
        }
    }

    @BindingAdapter("onRefresh")
    public static void bindOnRefreshListener(SwipeRefreshLayout refreshLayout, OnRefreshListener listener) {
        refreshLayout.setOnRefreshListener(() -> {
            if (listener != null) {
                listener.onRefresh();
            }
        });
    }

    @BindingAdapter("is_selected")
    public static void setSelected(View view, boolean selected) {
        view.setSelected(selected);
    }

    public interface OnRefreshListener {
        void onRefresh();
    }

    @BindingAdapter("messageInputListener")
    public static void bindMessageInput(MessageInput messageInput, MessageInputListener listener) {
        messageInput.setInputListener(input -> {
            if (listener == null) {
                return false;
            }
            return listener.onSubmit(String.valueOf(input));
        });
    }

    @BindingAdapter("avatar")
    public static void bindAvatar(ShapeImageView view, @DrawableRes Integer res) {

    }

    public interface OnItemSelectedListener {
        void onItemSelected(Object selectedItem);
    }

    public interface MessageInputListener {
        boolean onSubmit(String input);
    }

}
