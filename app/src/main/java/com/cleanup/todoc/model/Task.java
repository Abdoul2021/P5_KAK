package com.cleanup.todoc.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Comparator;
import java.util.List;

/**
 * <p>Model for the tasks of the application.</p>
 *
 * @author Gaëtan HERFRAY
 * Modified by Abdoul, refer to https://openclassrooms.com/en/courses/4568746-gerez-vos-donnees-localement-pour-avoir-une-application-100-hors-ligne
 */
@Entity(foreignKeys = @ForeignKey(entity = Project.class,
        parentColumns = "id",
        childColumns = "projectId"))

public class Task {
    /**
     * The unique identifier of the task
     */
    @PrimaryKey(autoGenerate = true)
    private long id ;

    /**
     * The unique identifier of the project associated to the task
     */
    private long projectId;

    /**
     * The name of the task
     */
    // Suppress warning because setName is called in constructor
    @SuppressWarnings("NullableProblems")
    @NonNull
    private String name;

    /**
     * The timestamp when the task has been created
     */
    private long creationTimestamp;

    //Add
    /**
     * The statute of project to be associated to the task
     */
    private Boolean isSelected;

    /**
     * Instantiates a new Task.
     *
     * @param id  the unique identifier of the task to set
     * @param projectId         the unique identifier of the project associated to the task to set
     * @param name              the name of the task to set
     * @param creationTimestamp the timestamp when the task has been created to set
     */
    @Ignore
    public Task(long id, long projectId, @NonNull String name, long creationTimestamp) {
        this.setId(id);
        this.setProjectId(projectId);
        this.setName(name);
        this.setCreationTimestamp(creationTimestamp);
    }

    /**
     * Instantiates a new Task.
     * @param projectId         the unique identifier of the project associated to the task to set
     * @param name              the name of the task to set
     * @param creationTimestamp the timestamp when the task has been created to set
     */
    public Task(long projectId, @NonNull String name, long creationTimestamp) {
        this.setProjectId(projectId);
        this.setName(name);
        this.setCreationTimestamp(creationTimestamp);
    }

    /**
     * @return the unique identifier of the task
     */
    public long getId() { return id; }

    /**
     * Sets the unique identifier of the task.
     * @param id the unique idenifier of the task to set
     */
    public void setId(long id) { this.id = id; }

    //Add
    /**
     * @return the unique identifier of the project associated to the task
     */
    public long getProjectId() { return projectId; }

    /**
     * Sets the unique identifier of the project associated to the task.
     * @param projectId the unique identifier of the project associated to the task to set
     */
    private void setProjectId(long projectId) { this.projectId = projectId; }

    /**
     * @return the project associated to the task
     */
    @Nullable
    public Project getProject() { return Project.getProjectById(projectId); }

    //Add
    /**
     * Sets the project associated to the task
     * @param projects the projects associated to the task to set.
     */
    @Nullable
    public Project getProject(List<Project> projects) { return Project.getProjectById(projectId, projects); }

    /**
     * @return the name of the task
     */
    @NonNull
    public String getName() { return name; }

    /**
     * Sets the name of the task.
     * @param name the name of the task to set
     */
    private void setName(@NonNull String name) { this.name = name; }

    //Add
    /**
     * @return the timestamp when the task has been created.
     */
    public long getCreationTimestamp() { return creationTimestamp; }

    /**
     * Sets the timestamp when the task has been created.
     * @param creationTimestamp the timestamp when the task has been created to set
     */
    private void setCreationTimestamp(long creationTimestamp) { this.creationTimestamp = creationTimestamp; }

    //Add
    /**
     * @return the statute of project to be associated to the task.
     */
    public Boolean getSelected() { return isSelected; }

    //Add
    /**
     * Sets the statute of project to be associated to the task
     * @param selected the statute of project to be associated to the task.
     */
    public void setSelected(Boolean selected) { isSelected = selected; }


    // --- COMPARATOR ---
    /**
     * Comparator to sort task from A to Z
     */
    public static class TaskAZComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) { return left.name.compareTo(right.name); }
    }

    /**
     * Comparator to sort task from Z to A
     */
    public static class TaskZAComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) { return right.name.compareTo(left.name); }
    }

    /**
     * Comparator to sort task from last created to first created
     */
    public static class TaskRecentComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) { return (int) (right.creationTimestamp - left.creationTimestamp); }
    }

    /**
     * Comparator to sort task from first created to last created
     */
    public static class TaskOldComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) { return (int) (left.creationTimestamp - right.creationTimestamp); }
    }

}