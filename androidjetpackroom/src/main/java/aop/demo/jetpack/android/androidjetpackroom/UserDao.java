package aop.demo.jetpack.android.androidjetpackroom;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import aop.demo.jetpack.android.androidjetpackroom.entity.User;
import io.reactivex.Observable;

@Dao
public interface UserDao {

    @Query("select * from users")
    Observable<List<User>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
//被标注的方法只能返回 void，long，Long，long[]，Long[]或者List<Long>
    void insertAll(User... users);
}
