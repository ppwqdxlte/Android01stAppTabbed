package com.laowang.android01stapptabbed;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.laowang.android01stapptabbed.entity.Result;
import com.laowang.android01stapptabbed.entity.TokenUser;
import com.laowang.android01stapptabbed.service.Login;
import com.laowang.android01stapptabbed.service.Management;
import com.laowang.android01stapptabbed.ui.main.SectionsPagerAdapter;
import com.laowang.android01stapptabbed.databinding.ActivityMainBinding;

/**
 * 主页面，安卓里 activity相当于页面
 */
public class MainActivity extends AppCompatActivity {
    /**
     * ActivityMainBinding捆绑了res/layout/activity_main.xml,是gradle构建编译生成的class
     * binding对象的功能类似于 古早的 R.layout.activity...的用法
     */
    private ActivityMainBinding binding;
    /**
     * @param savedInstanceState 保存的实例状态【Bundle 捆，怎么理解呢？附赠的一捆吗？】
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 创建页面时，上来就上传状态
        super.onCreate(savedInstanceState);
        // Android4.0以后不能在主线程里面进行网络连接，否则报错
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                binding.title.setText(R.string.tab_text_2);
                binding.title.setText(R.string.tab_text_2);
                Login login = Login.getInstance();
                Result<TokenUser> submit = login.submit("0001", "123");
                System.out.println(submit.getData().toString());
                Result<TokenUser> result = Management.getInstance().changePassword("0001", "000", "123", "123");
                System.out.println(result.getMsg());
                System.out.println(result.getData());
                Log.i("hahah",result.getMsg());
                Log.i("heheh",result.getData().toString());
            }
        });

        // 多形象啊，inflate，充气（成型），初始化 视图的捆绑对象
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        // 页面对象设置 内容视图（捆绑对象的根元素，由于捆绑的是activity_main.xml，而该XML文件的根元素是“交互布局”，
        // 之所以返回 交互布局的对象，因为该binding类是从该xml文件构建编译出来的，如果该xml文件根元素是 LineLayout标签，
        // 那么 binding.getRoot()返回的就会是LineLayout对象）
        setContentView(binding.getRoot());
        // ui/main/为啥main文件夹呢，估计是/res/layout/xxoo_main.xml决定的吧
        // 部件传呼机适配器 对象（Activity对象，FragmentManager对象）
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        // 页面捆绑对象 的 视图寻呼机
        ViewPager viewPager = binding.viewPager;
        // 试图寻呼机 联通 本页面【this (MainActivity对象)】 和 子页面【FragmentManager管理的所有fragments】
        viewPager.setAdapter(sectionsPagerAdapter);
        // 本页面捆绑物 的 页签布局
        TabLayout tabs = binding.tabs;
        // 页签布局 设置 视图传呼机
        tabs.setupWithViewPager(viewPager);
        // 浮动活动按钮（长得像页面边缘小广告）
        FloatingActionButton fab = binding.fab;
        // 给按钮添加点击事件监听器，MD，怎么设置自己的 Action? listener参数null应该改成啥?
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }
}