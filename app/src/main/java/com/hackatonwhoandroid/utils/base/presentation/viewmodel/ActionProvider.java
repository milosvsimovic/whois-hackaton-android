package com.hackatonwhoandroid.utils.base.presentation.viewmodel;

import javax.inject.Inject;

@SuppressWarnings("unused")
public class ActionProvider {

    @Inject
    public ActionProvider() {
    }

    public <ActionCodeT> Action<ActionCodeT> provide(ActionCodeT code) {
        return new Action<>(code);
    }

    public <ActionCodeT> Action<ActionCodeT> provide(ActionCodeT code, Object data) {
        return new Action<>(code, data);
    }

}
