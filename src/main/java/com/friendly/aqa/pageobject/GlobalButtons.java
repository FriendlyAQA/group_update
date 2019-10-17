package com.friendly.aqa.pageobject;

public enum GlobalButtons {

    ACTIVATE("btnActivate_btn"),
    CANCEL("btnCancel_btn"),
    DEACTIVATE("btnStop_btn"),
    DELETE("btnDelete_btn"),
    FINISH("btnFinish_btn"),
    NEXT("btnNext_btn"),
    PAUSE("btnPause_btn"),
    REFRESH("btnRefresh_btn"),
    PREVIOUS("btnPrev_btn"),
    SAVE("btnSave_btn"),
    SAVE_AND_ACTIVATE("btnSaveActivate_btn"),
    STOP("btnStop_btn");

    private GlobalButtons(String id) {
        this.id = id;
    }

    private String id;

    public String getId() {
        return id;
    }
}
