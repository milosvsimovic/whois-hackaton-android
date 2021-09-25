package com.hackatonwhoandroid.utils.base.presentation;


import com.hackatonwhoandroid.utils.base.presentation.viewmodel.Action;

public interface IActionListener<ActionCodeT> {

    void onAction(Action<ActionCodeT> action);

}
