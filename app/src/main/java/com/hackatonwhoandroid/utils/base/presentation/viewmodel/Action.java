package com.hackatonwhoandroid.utils.base.presentation.viewmodel;

import lombok.Getter;

@SuppressWarnings("WeakerAccess")
@Getter
public class Action<ActionCodeT> {

    private final ActionCodeT code;
    private Object data;

    public Action(ActionCodeT code) {
        this.code = code;
    }

    public Action(ActionCodeT code, Object data) {
        this.code = code;
        this.data = data;
    }
}
