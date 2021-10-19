package com.cleanup.todoc.database;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

/**
 * Created by Abdoul on 11/10/2021.
 * refer to https://openclassrooms.com/en/courses/4568746-gerez-vos-donnees-localement-pour-avoir-une-application-100-hors-ligne
 */

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class ToDocDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile ToDocDatabase INSTANCE;

    // --- DAO ---
    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();

    // --- INSTANCE ---
    public static ToDocDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ToDocDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ToDocDatabase.class, "MyDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback prepopulateDatabase(){
        return new RoomDatabase.Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                Project [] tab = Project.getAllProjects();
                ContentValues contentValues = new ContentValues();
                for (Project project : tab) {
                    contentValues.put("id", project.getId());
                    contentValues.put("name", project.getName());
                    contentValues.put("color", project.getColor());

                    db.insert("Project", OnConflictStrategy.IGNORE, contentValues);
                }

            }
        };
    }
}

