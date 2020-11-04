package com.aSem.findingPetsMobile.ThreadInterface;

import io.reactivex.Scheduler;

public interface PostExecutionThread {
    Scheduler getScheduler();
}
