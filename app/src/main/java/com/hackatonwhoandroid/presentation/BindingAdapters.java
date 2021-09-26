package com.hackatonwhoandroid.presentation;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hackatonwhoandroid.R;
import com.hackatonwhoandroid.domain.model.Message;
import com.hackatonwhoandroid.utils.SimpleTextWatcher;
import com.stfalcon.chatkit.messages.MessageInput;

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

    @BindingAdapter(value = {"messageType"}, requireAll = false)
    public static void changeColor(@NonNull View view, @Nullable Message.Type messageType) {
        if (messageType == null) {
            return;
        }
        Resources resources = view.getContext().getResources();
        switch (messageType) {
            case INFO:
            case TEXT:
                break;
            case DOMAIN_LOADING:
                view.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.light_blue)));
                break;
            case DOMAIN_ACTIVE:
                view.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.red)));
                break;
            case DOMAIN_INACTIVE:
                view.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.cornflower_blue_two)));
                break;
            case REMINDER:
                view.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.white)));
            case DOMAIN_OTHER:
            default:
                view.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.light_gray)));
                break;
        }
    }

    @BindingAdapter(value = {"messageTypeImageColor"}, requireAll = false)
    public static void changeImageColor(@NonNull ImageView view, @Nullable Message.Type messageType) {
        if (messageType == null) {
            return;
        }
        Resources resources = view.getContext().getResources();
        switch (messageType) {
            case INFO:
            case TEXT:
                break;
            case DOMAIN_LOADING:
                view.setImageTintList(ColorStateList.valueOf(resources.getColor(R.color.light_blue)));
                break;
            case DOMAIN_ACTIVE:
                view.setImageTintList(ColorStateList.valueOf(resources.getColor(R.color.red)));
                break;
            case DOMAIN_INACTIVE:
                view.setImageTintList(ColorStateList.valueOf(resources.getColor(R.color.cornflower_blue_two)));
                break;
            case DOMAIN_OTHER:
            default:
                view.setImageTintList(ColorStateList.valueOf(resources.getColor(R.color.light_gray)));
                break;
        }
    }

    @BindingAdapter(value = {"messageTypeTextColor"}, requireAll = false)
    public static void changeColor(@NonNull TextView view, @Nullable Message.Type messageType) {
        if (messageType == null) {
            return;
        }
        Resources resources = view.getContext().getResources();
        switch (messageType) {
            case DOMAIN_OTHER:
                view.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.cornflower_blue_two)));
                break;
            case REMINDER:
                view.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.gray)));
            default:
                view.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.white)));
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

    @BindingAdapter("onSubmitInput")
    public static void bindOnFocusChangeListener(MessageInput messageInput, OnSubmitListener listener) {
        messageInput.setInputListener(listener::onSubmitListener);
    }

    @BindingAdapter({"onTextChangeListener"})
    public static void bindTextChangeListener(EditText editText, OnTextChangeListener listener) {
        if (listener != null) {
            editText.addTextChangedListener(new SimpleTextWatcher() {
                @Override
                public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                    listener.onTextChange(sequence.toString());
                }
            });
        }
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

    public interface OnItemSelectedListener {
        void onItemSelected(Object selectedItem);
    }

    public interface MessageInputListener {
        boolean onSubmit(String input);
    }

    public interface OnSubmitListener {
        boolean onSubmitListener(CharSequence isSubmitted);
    }

    public interface OnTextChangeListener {
        void onTextChange(String text);
    }

}
