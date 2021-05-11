package com.reactive.livebus.Interfaces;

import com.reactive.livebus.model.StopClass;

public interface StopListener {
    void onStopAddClick(StopClass model);
    void onStopRemoveClick(StopClass model);
}
