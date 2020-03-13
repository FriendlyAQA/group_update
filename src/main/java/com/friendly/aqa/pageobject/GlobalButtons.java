package com.friendly.aqa.pageobject;

public enum GlobalButtons {

    ACTIVATE("btnActivate_btn"),
    ADVANCED_VIEW("btnAdvancedView_btn"),
    CANCEL("btnCancel_btn"),
    DEACTIVATE("btnDeactivate_btn"),
    DELETE("btnDelete_btn"),
    DELETE_GROUP("btnDeleteView_btn"),
    DUPLICATE("btnDuplicate_btn"),
    EDIT("btnEdit_btn"),
    FINISH("btnFinish_btn"),
    NEXT("btnNext_btn"),
    PAUSE("btnPause_btn"),
    REFRESH("btnRefresh_btn"),
    PREVIOUS("btnPrev_btn"),
    SAVE("btnSave_btn"),
    SAVE_AND_ACTIVATE("btnSaveActivate_btn"),
    SIMPLE_VIEW("btnView_btn"),
    STOP("btnStop_btn"),
    STOP_WITH_RESET("btnStopWithReset_btn");

    GlobalButtons(String id) {
        this.id = id;
    }

    private String id;

    public String getId() {
        return id;
    }
}
