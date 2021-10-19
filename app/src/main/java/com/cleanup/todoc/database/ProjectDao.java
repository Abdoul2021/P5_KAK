package com.cleanup.todoc.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.cleanup.todoc.model.Project;

import java.util.List;

/**
 * Created by Abdoul on 11/10/2021.
 * refer to https://openclassrooms.com/en/courses/4568746-gerez-vos-donnees-localement-pour-avoir-une-application-100-hors-ligne
 */

@Dao
public interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createProject(Project project);

    @Query("SELECT * FROM Project WHERE id = :projectId")
    LiveData<Project> getProject(long projectId);

    @Query("SELECT * FROM Project")
    LiveData<List<Project>> getProjects();
}
