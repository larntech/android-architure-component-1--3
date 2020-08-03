package net.larntech.roomdatabase;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class UsersRepository {

    private Database database;
    private UsersDao usersDao;
    private LiveData<List<Users>> usersList;

    public UsersRepository(Application application) {

        database = Database.getDatabase(application);
        usersDao = database.usersDao();
        usersList = usersDao.getAllUsers();

    }

    public LiveData<List<Users>> getAllUsers(){
        return database.usersDao().getAllUsers();
    }


    public void insertUser(final Users users){
        new AsyncTask<Void, Void, Void>(

        ) {
            @Override
            protected Void doInBackground(Void... voids) {
                database.usersDao().insertUser(users);
                return null;
            }
        }.execute();
    }


    public void updateUsers(final Users users){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                database.usersDao().updateUsers(users);
                return null;
            }
        }.execute();
    }

    public void deleteUsers(final Users users){
        new AsyncTask<Void, Void, Void>(

        ) {
            @Override
            protected Void doInBackground(Void... voids) {

                database.usersDao().deleteUser(users);
                return null;
            }
        }.execute();
    }


}
