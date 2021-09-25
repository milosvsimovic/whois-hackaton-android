package com.hackatonwhoandroid.presentation.main;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.hackatonwhoandroid.R;
import com.hackatonwhoandroid.databinding.ActivityMainBinding;
import com.hackatonwhoandroid.presentation.chat.ChatFragment;
import com.hackatonwhoandroid.utils.base.presentation.BaseActivity;
import com.hackatonwhoandroid.utils.base.presentation.viewmodel.Action;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewModel().callExampleMethod();
        addFragment(R.id.fragment_container, ChatFragment.newInstance(this::handleFragmentActions));
    }

    private void handleFragmentActions(Action<ChatFragment.ActionCode> action) {
        switch (action.getCode()) {
            case ON_LINE_CLICK:
//                addFragment(R.id.fragment_container, DeparturesFragment.newInstance((String) action.getData()));
                break;
        }
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }
}