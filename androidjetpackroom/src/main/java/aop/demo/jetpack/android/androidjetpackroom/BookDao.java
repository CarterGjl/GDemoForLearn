package aop.demo.jetpack.android.androidjetpackroom;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import aop.demo.jetpack.android.androidjetpackroom.entity.Book;

@Dao
public interface BookDao {

    @Query("select * from books")
    List<Book> getAll();

    @Insert
//被标注的方法只能返回 void，long，Long，long[]，Long[]或者List<Long>
    void insertAll(Book... books);
}
