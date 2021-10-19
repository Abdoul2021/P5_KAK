package com.cleanup.todoc.di;

import android.content.Context;

import com.cleanup.todoc.database.ToDocDatabase;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Abdoul on 11/10/2021.
 * refer to https://openclassrooms.com/en/courses/4568746-gerez-vos-donnees-localement-pour-avoir-une-application-100-hors-ligne
 */

public class DI {

    public static TaskDataRepository provideTaskDataSource(Context context) {
        ToDocDatabase database = ToDocDatabase.getInstance(context);
        return new TaskDataRepository(database.taskDao());
    }

    public static ProjectDataRepository provideProjectDataSource(Context context) {
        ToDocDatabase database = ToDocDatabase.getInstance(context);
        return new ProjectDataRepository(database.projectDao());
    }

    public static Executor provideExecutor(){ return Executors.newSingleThreadExecutor(); }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        TaskDataRepository dataSourceItem = provideTaskDataSource(context);
        ProjectDataRepository dataSourceUser = provideProjectDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceItem, dataSourceUser, executor);
    }
}
