package com.cleanup.todoc.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
/**
 * Created by Abdoul on 11/10/2021.
 * refer to https://openclassrooms.com/en/courses/4568746-gerez-vos-donnees-localement-pour-avoir-une-application-100-hors-ligne
 */
public class TaskViewModel extends ViewModel {

    /**
     * The sort method to be used to display tasks
     */
    @NonNull
    private SortMethod sortMethod = SortMethod.NONE;

    // REPOSITORIES
    private final TaskDataRepository taskDataSource;
    private final ProjectDataRepository projectDataSource;
    private final Executor executor;

    // DATA
    @Nullable
    private LiveData<Project> currentProject;
    private LiveData<List<Project>> projects;
    private LiveData<List<Task>> tasks;
    private List<Project> projectsList;

    public TaskViewModel(TaskDataRepository taskDataSource, ProjectDataRepository projectDataSource, Executor executor) {
        this.taskDataSource = taskDataSource;
        this.projectDataSource = projectDataSource;
        this.executor = executor;
    }

   /* public void init(long projectId) {
        if (this.currentProject != null) {
            return;
        }
        currentProject = projectDataSource.getProject(projectId);
    }*/

    // -------------
    // FOR PROJECT
    // -------------

    public LiveData<Project> getProject(long projectId) { return this.currentProject;  }
    public LiveData<List<Project>> getProjects() {
        projects = projectDataSource.getProjects();
        return projects ;
    }
    // -------------
    // FOR TASK
    // -------------

    public LiveData<List<Task>> getTasks() {
        tasks =taskDataSource.getTasks();
        return tasks;
    }

    public void createTask(Task task) {
        executor.execute(() -> {
            taskDataSource.createTask(task);
        });
    }

    public void deleteTask(long taskId) {
        executor.execute(() -> {
            taskDataSource.deleteTask(taskId);
        });
    }

    /*public void updateTask(Task task) {
        executor.execute(() -> {
            taskDataSource.updateTask(task);
        });
    }*/

    //Return the list of projects
    public List<Project> getAllProjects(){ return projectsList; }

    //Take the list of projects of activity in the ViewModel
    public void updateProjectsList(List<Project> projectsList) { this.projectsList = projectsList;
    }

    //SortMethod
    @NonNull
    public SortMethod getSortMethod() { return sortMethod; }

    public void setSortMethod(SortMethod sortMethod) {  this.sortMethod = sortMethod; }

    /**
     * List of all possible sort methods for task
     */
    public enum SortMethod {
        /**
         * Sort alphabetical by name
         */
        ALPHABETICAL,
        /**
         * Inverted sort alphabetical by name
         */
        ALPHABETICAL_INVERTED,
        /**
         * Lastly created first
         */
        RECENT_FIRST,
        /**
         * First created first
         */
        OLD_FIRST,
        /**
         * No sort
         */
        NONE
    }
}
