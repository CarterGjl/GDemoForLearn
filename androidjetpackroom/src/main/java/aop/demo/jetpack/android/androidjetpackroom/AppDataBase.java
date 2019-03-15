package aop.demo.jetpack.android.androidjetpackroom;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import aop.demo.jetpack.android.androidjetpackroom.entity.Book;
import aop.demo.jetpack.android.androidjetpackroom.entity.User;

@Database(entities = {User.class, Book.class},version = 4,exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract BookDao bookDao();
}
