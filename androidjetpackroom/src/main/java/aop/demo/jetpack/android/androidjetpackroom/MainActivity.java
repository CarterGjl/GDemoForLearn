package aop.demo.jetpack.android.androidjetpackroom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import aop.demo.jetpack.android.androidjetpackroom.entity.Book;
import aop.demo.jetpack.android.androidjetpackroom.entity.User;
import aop.demo.jetpack.android.androidjetpackroom.entity.data.Result;
import aop.demo.jetpack.android.androidjetpackroom.entity.service.GetArticleI;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private SearchView mSearch;
    private Toolbar mToobar;
    private AutoCompleteTextView mAt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: ");

        Disposable subscribe1 = NetManager.create(GetArticleI.class)
                .getWx()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Result<Object>>() {
                    @Override
                    public void accept(Result<Object> objectResult) throws Exception {
                        Log.d(TAG, "accept: "+objectResult);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "accept: ",throwable );
                    }
                });
        Disposable subscribe = NetManager.create(GetArticleI.class)
                .getArticle(405, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Result<Object>>() {
                    @Override
                    public void accept(Result<Object> stringResult) throws Exception {
                        Log.d(TAG, "accept: " + stringResult);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        Log.e(TAG, "accept: ", throwable);
                    }
                });


        mAt = findViewById(R.id.at);


        mAt.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mAt.setOnEditorActionListener((v, actionId, event) -> {
            switch (actionId){
                case EditorInfo.IME_ACTION_DONE:
                    Toast.makeText(MainActivity.this, v.getText(), Toast.LENGTH_SHORT).show();
                    InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputMethodManager.isActive()) {
                        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
                    }
                    break;
            }
            return true;
        });
        mToobar = findViewById(R.id.toobar);

        setSupportActionBar(mToobar);

        String[] stringArray = getResources().getStringArray(R.array.test_array);

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stringArray);
        mAt.setAdapter(stringArrayAdapter);
        mAt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem item = menu.findItem(R.id.search_view);
        SearchView actionView = (SearchView) item.getActionView();
//        SearchView.SearchAutoComplete ser =
//                (SearchView.SearchAutoComplete) actionView.findViewById(R.id.search_src_text);
        actionView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    private static final String TAG = "MainActivity";
    public void insert(View view) {
         new Thread(() -> {
             User user = new User();
             user.setId(1);
             user.setName("carter");
             AppDataBase carter = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "carter").build();
             carter.userDao().insertAll(user);
//             List<User> all = carter.userDao().getAll();
//             for (int i = 0; i < all.size(); i++) {
//                 Log.d(TAG, "run: "+all.get(i));
//             }

         }).start();
    }

    public void update(View view) {
        new Thread(()->{

            AppDataBase carter2 = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "carter")
                    .addMigrations(new Migration(1, 2) {
                        @Override
                        public void migrate(@NonNull SupportSQLiteDatabase database) {
                            database.execSQL("create table if not exists `book`(`book_id` INTEGER" +
                                    " PRIMARY KEY AUTOINCREMENT NOT NULL, `book_name` TEXT)");
                        }
                    }).build();
//            Book book = new Book();
//            book.setBookId(233);
//            book.setBookName("盗墓笔记");
//
////            carter2.bookDao().insertAll(book);
//            List<User> all = carter2.userDao().getAll();
//            for (int i = 0; i < all.size(); i++) {
//                Log.d(TAG, "update: "+all.get(i));
//            }
//
//            for (Book book1 : carter2.bookDao().getAll()) {
//
//                Log.d(TAG, "update: "+book1);
//            }
        }).start();
    }

    public void query(View view) {
        AppDataBase carter2 = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "carter")
                .build();
        new Thread(()->{



            for (Book book1 : carter2.bookDao().getAll()) {

                Log.d(TAG, "update: "+book1);
            }
        }).start();

        carter2.userDao().getAll().subscribeOn(Schedulers.io())
                .subscribe(users -> {

                    for (User user : users) {
                        Log.d(TAG, "accept: "+user);
                    }
                });

    }

    public void insertBoo(View view) {

        new Thread(()->{
            AppDataBase carter2 = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "carter")
                    .build();

            Book book = new Book();
            book.setBookId(233);
            book.setBookName("盗墓笔记");

            carter2.bookDao().insertAll(book);
        }).start();

    }

    public void updateCo(View view) {
        new Thread(() -> {

            AppDataBase carter = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "carter")
                    .addMigrations(new Migration(3, 4) {
                        @Override
                        public void migrate(@NonNull SupportSQLiteDatabase database) {
                            database.execSQL("ALTER TABLE users"
                                    + " ADD COLUMN grade INTEGER NOT NULL DEFAULT 10");
                        }
                    }).build();

            User user = new User();
            user.setId(6);
            user.setName("daf");
            user.setAge(4353334);
            user.setGrade(4);
            carter.userDao().insertAll(user);
        }).start();
    }

    private boolean the = false;
    public void changeTheme(View view) {

//        if (the){
//            setTheme(R.style.AppTheme);
//            the = false;
//        }else {
//            setTheme(R.style.AppTheme1);
//            the = true;
//        }
//        setTheme(R.style.AppTheme1);
        recreate();
    }

    public void click(View view) {
        Intent intent = new Intent(this, BActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
