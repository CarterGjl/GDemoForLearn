package com.tution.wanandroidkotlin.ui.main
import com.afollestad.materialdialogs.MaterialDialog
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.tution.wanandroidkotlin.R
import com.tution.wanandroidkotlin.base.BaseActivity
import com.tution.wanandroidkotlin.ui.home.HomeFragment
import com.tution.wanandroidkotlin.util.Preference
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity:BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.nav_blog -> startKtxActivity<ProjectActivity>(value = ProjectActivity.BLOG_TAG to true)
//            R.id.nav_project_type -> startKtxActivity<ProjectActivity>(value = ProjectActivity.BLOG_TAG to false)
//            R.id.nav_tool -> switchToTool()
//            R.id.nav_collect -> switchCollect()
//            R.id.nav_about -> switchAbout()
//            R.id.nav_exit -> exit()
//        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    private fun exit() {
        MaterialDialog(this).show {
            title = "退出"
            message(text = "是否确认退出登录？")
            positiveButton(text = "确认") {
                launch(Dispatchers.Default) {
//                    WanRetrofitClient.service.logOut()
                }
                isLogin = false
                userJson = ""
                refreshView()
            }
            negativeButton(text = "取消")
        }
    }


    private fun refreshView(){
        navigationView.menu.findItem(R.id.nav_exit).isVisible = isLogin
        homeFragment.refresh()
//        lastedProjectFragment.refresh()
    }
    private var isLogin by Preference(Preference.IS_LOGIN, false)
    private var userJson by Preference(Preference.USER_GSON, "")

    private val titleList = arrayOf("首页"/*, "广场","最新项目", "体系", "导航"*/)
    private val fragmentList = arrayListOf<Fragment>()
    private val homeFragment by lazy { HomeFragment() } // 首页
//    private val squareFragment by lazy { SquareFragment() } // 广场
//    private val lastedProjectFragment by lazy { ProjectTypeFragment.newInstance(0, true) } // 最新项目
//    private val systemFragment by lazy { SystemFragment() } // 体系
//    private val navigationFragment by lazy { NavigationFragment() } // 导航


    init {
        fragmentList.add(homeFragment)
//        fragmentList.add(squareFragment)
//        fragmentList.add(lastedProjectFragment)
//        fragmentList.add(systemFragment)
//        fragmentList.add(navigationFragment)
    }
    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        initViewPager()
        mainToolBar.setNavigationOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.menu.findItem(R.id.nav_exit).isVisible = isLogin
    }

    private fun initViewPager() {
        viewPager.offscreenPageLimit = titleList.size
        viewPager.adapter = object : androidx.fragment.app.FragmentPagerAdapter(supportFragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            override fun getItem(position: Int) = fragmentList[position]

            override fun getCount() = titleList.size

            override fun getPageTitle(position: Int) = titleList[position]

        }
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun iniData() {
        mainSearch.setOnClickListener {
            Toast.makeText(this, "to do search", Toast.LENGTH_SHORT).show();
        }
    }
}