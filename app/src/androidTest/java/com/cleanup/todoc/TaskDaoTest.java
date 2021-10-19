package com.cleanup.todoc;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.graphics.Color;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.ToDocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Abdoul on 11/10/2021.
 * refer to https://openclassrooms.com/en/courses/4568746-gerez-vos-donnees-localement-pour-avoir-une-application-100-hors-ligne
 */

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    // FOR DATA
    private ToDocDatabase database;

    // DATA SET FOR TEST
    private static long PROJECT_ID = 1;
    public static Project PROJECT_DEMO = new Project (PROJECT_ID, "Projet KAK", Color.RED);
    private static Task NEW_TASK_NETTOYER_LES_VITRES = new Task (1L, PROJECT_ID, "Vitres", System.currentTimeMillis());
    private static Task NEW_TASK_VIDER_LE_LAVE_VAISSELLE = new Task(2L, PROJECT_ID, "Vaisselle", System.currentTimeMillis());
    private static Task NEW_TASK_PASSER_L_ASPIRATEUR = new Task(3L, PROJECT_ID, "Aspirateur", System.currentTimeMillis());

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                ToDocDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }

    @Test
    public void insertAndGetProject() throws InterruptedException {
        // BEFORE : Adding a new user
        this.database.projectDao().createProject(PROJECT_DEMO);
        // TEST
        Project project = LiveDataTestUtil.getValue(this.database.projectDao().getProject(PROJECT_ID));
        assertTrue(project.getName().equals(PROJECT_DEMO.getName()) && project.getId() == PROJECT_ID);
    }

    @Test
    public void getTasksWhenNoTaskInserted() throws InterruptedException {
        // TEST
        List<Task> items = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        assertTrue(items.isEmpty());
    }

    @Test
    public void insertAndGetTasks() throws InterruptedException {
        // BEFORE : Adding demo project & demo tasks
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().insertTask(NEW_TASK_NETTOYER_LES_VITRES);
        this.database.taskDao().insertTask(NEW_TASK_VIDER_LE_LAVE_VAISSELLE);
        this.database.taskDao().insertTask(NEW_TASK_PASSER_L_ASPIRATEUR);
        // TEST
        List<Task> items = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        assertTrue(items.size() == 3);
    }

    @Test
    public void insertAndUpdateTask() throws InterruptedException {
        // BEFORE : Adding demo project & demo tasks. Next, update task added & re-save it
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().insertTask(NEW_TASK_NETTOYER_LES_VITRES);
        Task taskAdded = LiveDataTestUtil.getValue(this.database.taskDao().getTasks()).get(0);
        taskAdded.setSelected(true);
        this.database.taskDao().updateTask(taskAdded);
        //TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        assertTrue(tasks.size() == 1 && tasks.get(0).getSelected());
    }

    @Test
    public void insertAndDeleteTask() throws InterruptedException {
        // BEFORE : Adding demo project & demo task. Next, get the task added & delete it.
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().insertTask(NEW_TASK_VIDER_LE_LAVE_VAISSELLE);
        Task taskAdded = LiveDataTestUtil.getValue(this.database.taskDao().getTasks()).get(0);
        this.database.taskDao().deleteTask(taskAdded.getId());
        //TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        assertTrue(tasks.isEmpty());
    }
}