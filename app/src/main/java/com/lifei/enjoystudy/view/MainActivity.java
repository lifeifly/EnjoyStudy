package com.lifei.enjoystudy.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.media.tv.TvContract;
import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lifei.annotation.MyClass;
import com.lifei.base.autoservice.MyServiceLoader;
import com.lifei.common.autoservice.IWebViewService;
import com.lifei.customview.FlowLayout;
import com.lifei.customview.吸顶recyclerview.Star;
import com.lifei.customview.吸顶recyclerview.StarAdapter;
import com.lifei.customview.吸顶recyclerview.StarDecoration;
import com.lifei.customview.吸顶recyclerview.StarItemDecoration;
import com.lifei.customview.懒加载viewpager.LazyFragment;
import com.lifei.customview.懒加载viewpager.LazyFragment2;
import com.lifei.customview.缩放滑动view.MutilpePointerView;
import com.lifei.customview.缩放滑动view.PhotoView;
import com.lifei.customview.自定义LayoutManager.CardConfig;
import com.lifei.customview.自定义LayoutManager.SlideCallback;
import com.lifei.customview.自定义LayoutManager.SlideCardBean;
import com.lifei.customview.自定义LayoutManager.SlideCardLayoutManager;
import com.lifei.customview.自定义LayoutManager.SlideLayoutManager;
import com.lifei.customview.自定义LayoutManager.adapter.UniversalAdapter;
import com.lifei.customview.自定义LayoutManager.adapter.ViewHolder;
import com.lifei.enjoystudy.R;
import com.lifei.enjoystudy.proxy.FButtonKnife;
import com.lifei.enjoystudy.proxy.OnClick;
import com.lifei.enjoystudy.proxy.OnLongClick;
import com.lifei.enjoystudy.utils.Utils;
import com.lifei.skinmanage.skin.SkinManager;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

@MyClass
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FlowLayout flowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutilpe_pointer);
        Log.i(TAG, "onCreate");
        Button mv = findViewById(R.id.mv);

        mv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IWebViewService webViewService= MyServiceLoader.load(IWebViewService.class);
                if (webViewService!=null){
//                    webViewService.startWebViewActivity(MainActivity.this,"https://www.baidu.com","百度",true);
                    webViewService.startDemoHtml(MainActivity.this);
                }
            }
        });
//        RecyclerView rv=findViewById(R.id.recycler_view);
//        rv.setLayoutManager(new LinearLayoutManager(this));
//        List<String> data=new ArrayList<>();

//        for (int i = 0; i < 100; i++) {
//            data.add("数据"+i);
//        }

//        rv.setAdapter(new UniversalAdapter(this,data,R.layout.first_item) {
//            @Override
//            public void convert(ViewHolder var1, Object var2) {
//                var1.setText(R.id.tv,(String)var2);
//            }
//        });

//        ViewPager viewPager = findViewById(R.id.vp);
//        BottomNavigationView bnv = findViewById(R.id.bnv);
//
//        List<Fragment> fragments = new ArrayList<>();
//        fragments.add(LazyFragment.newInstance("Fragment 1"));
//        fragments.add(LazyFragment2.newInstance("Fragment 2"));
//        fragments.add(LazyFragment.newInstance("Fragment 3"));
//        fragments.add(LazyFragment.newInstance("Fragment 4"));
//        fragments.add(LazyFragment.newInstance("Fragment 5"));
//
//        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
//            @NonNull
//            @NotNull
//            @Override
//            public Fragment getItem(int position) {
//                return fragments.get(position);
//            }
//
//            @Override
//            public int getCount() {
//                return fragments.size();
//            }
//        });
//
//        viewPager.setOffscreenPageLimit(1);
//
//        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.fragment_1:
//                        viewPager.setCurrentItem(0, true);
//                        return true;
//                    case R.id.fragment_2:
//                        viewPager.setCurrentItem(1, true);
//                        return true;
//                    case R.id.fragment_3:
//                        viewPager.setCurrentItem(2, true);
//                        return true;
//                    case R.id.fragment_4:
//                        viewPager.setCurrentItem(3, true);
//                        return true;
//                    case R.id.fragment_5:
//                        viewPager.setCurrentItem(4, true);
//                        return true;
//                }
//                return false;
//            }
//        });
//
//        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                int itemId = R.id.fragment_1;
//                switch (position) {
//                    case 0:
//                        itemId = R.id.fragment_1;
//                        break;
//                    case 1:
//                        itemId = R.id.fragment_2;
//                        break;
//                    case 2:
//                        itemId = R.id.fragment_3;
//                        break;
//                    case 3:
//                        itemId = R.id.fragment_4;
//                        break;
//                    case 4:
//                        itemId = R.id.fragment_5;
//                        break;
//                }
//
//                bnv.setSelectedItemId(itemId);
//            }
//        });
//        RecyclerView rv = findViewById(R.id.rv);

//        List<Star> stars = new ArrayList<>();
//
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 20; j++) {
//                if (i % 2 == 0) {
//                    stars.add(new Star("张三" + j, "快乐家族" + i));
//                } else {
//                    stars.add(new Star("李四" + j, "天天兄弟" + i));
//                }
//            }
//        }
//
//        StarAdapter adapter = new StarAdapter(this, stars);
//        rv.setLayoutManager(new LinearLayoutManager(this));
//        rv.addItemDecoration(new StarItemDecoration());
//        rv.setAdapter(adapter);


//        CardConfig.initConfig(this);
//
//        List<SlideCardBean> datas = new ArrayList<>();
//        int i = 1;
//        datas.add(new SlideCardBean(i++, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595434963213&di=5d07d9de35f42c16238c3076119a6e98&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fmobile%2F2018-12-13%2F5c120783eba2b.jpg", "美女1"));
//        datas.add(new SlideCardBean(i++, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595435110291&di=67d1066bc7fc86a92fbf8b52de05398d&imgtype=0&src=http%3A%2F%2Fimg.tupianzj.com%2Fuploads%2Fallimg%2F150919%2F9-150919205I5.jpg", "美女2"));
//        datas.add(new SlideCardBean(i++, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595435159388&di=c6581dc27ef4c1e956ac24d8b50bf8cf&imgtype=0&src=http%3A%2F%2Fimg.08087.cc%2Fuploads%2F20191223%2F18%2F1577098270-azHRMjKysW.jpg", "美女3"));
//        datas.add(new SlideCardBean(i++, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595435251280&di=7de649efc7dbd900534870e226609c3f&imgtype=0&src=http%3A%2F%2Finews.gtimg.com%2Fnewsapp_match%2F0%2F11956700004%2F0.jpg", "美女4"));
//        datas.add(new SlideCardBean(i++, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595435309398&di=f5a0ba4719386c298d98ad56394c61f4&imgtype=0&src=http%3A%2F%2Fimg.tupianzj.com%2Fuploads%2Fallimg%2F150921%2F9-1509210S604-50.jpg", "美女5"));
//        datas.add(new SlideCardBean(i++, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595435349750&di=2958b535274265ef958d71fda1032ab2&imgtype=0&src=http%3A%2F%2Fimg3.yxlady.com%2Fmr%2FUploadFiles_9207%2F2015093%2F20150903115457296.jpg", "美女6"));
//        datas.add(new SlideCardBean(i++, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560164210849&di=c6ea3fdd3ec938600ddde9022f46033c&imgtype=0&src=http%3A%2F%2Fbbs-fd.zol-img.com.cn%2Ft_s800x5000%2Fg4%2FM09%2F00%2F07%2FCg-4WlJA9zCIPZ8PAAQWAhRW0ssAAMA8wD2hYAABBYa996.jpg", "美女7"));
//        datas.add(new SlideCardBean(i++, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595435458268&di=a44e8862a2b5ecf8d7519188bd06c30b&imgtype=0&src=http%3A%2F%2Fwww.guanxiu.com%2Fuploads%2Fallimg%2Fc140918%2F14110351bS220-14617-lp.jpg","美女8"));
//        UniversalAdapter adapter = new UniversalAdapter<SlideCardBean>(this, datas, R.layout.item_swipe_card) {
//
//            @Override
//            public void convert(ViewHolder viewHolder, SlideCardBean slideCardBean) {
//                viewHolder.setText(R.id.tvName, slideCardBean.getName());
//                viewHolder.setText(R.id.tvPrecent, slideCardBean.getPostition() + "/" + mDatas.size());
//                Glide.with(MainActivity.this)
//                        .load(slideCardBean.getUrl())
//                        .into((ImageView) viewHolder.getView(R.id.iv));
//            }
//        };
//        rv.setAdapter(adapter);
//        rv.setLayoutManager(new SlideCardLayoutManager());
//        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new SlideCallback(rv,adapter,datas));
//        itemTouchHelper.attachToRecyclerView(rv);

    }

}