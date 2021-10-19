package com.cleanup.todoc.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.cleanup.todoc.model.Task;

import java.util.List;

/**
 * Created by Abdoul on 11/10/2021.
 * refer to https://openclassrooms.com/en/courses/4568746-gerez-vos-donnees-localement-pour-avoir-une-application-100-hors-ligne
 */

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getTasks();

    @Insert
    long insertTask(Task task);

    @Update
    int updateTask(Task task);

    @Query("DELETE FROM Task WHERE id = :taskId")
    int deleteTask(long taskId);

}
