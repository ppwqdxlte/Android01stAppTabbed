package com.laowang.android01stapptabbed.ui.main;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
/**
 * 这是activity_main.xml对应的页面视图模型
 * 用来处理 数据 的吗？
 * 要我看初始代码只给 TabLayout组件 服务了。。。
 */
public class PageViewModel extends ViewModel {

    /*private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            return "Hello world from section: " + input;
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }*/

    /**
     * 可变实时数据
     */
    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    /**
     * 实时数据(不可变)：Transformations.map(原数据，变形钩子)方法把 MutableLiveData对象 转化成 String泛型的 LiveData对象
     * PageViewModel对象初始化时给mText对象绑定了map钩子函数，往后每逢调用 getText() 都转化一次
     */
    private LiveData<String> mText;
    /**
     * 匿名函数成员，避免每次 getter()都创建新函数对象
     */
    private Function<Integer,String> innerHook = new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            return "你好世界 from section: " + input;
        }
    };
    /**
     * 为啥匿名函数对象要在构造方法里引用并给其它成员初始化呢？由于编译器特性，哪个成员初始化不一定按顺序的，
     * 所以可能给mText初始化赋值时可能还没初始化 mIndex 咧，怎么能保准顺序呢？
     * 已知对象初始化过程：先给所有成员属性或成员方法初始化，完后再 有构造器 创建出对象，所以把mText的赋值放入构造器中
     */
    public PageViewModel() {
        mText = Transformations.map(mIndex,innerHook);
    }
    /**
     * 只能作用在可变对象上，LiveData对象不可变
     * @param index tab索引
     */
    public void setIndex(int index) {
        mIndex.setValue(index);
    }
    /**
     * 每次调用 getter 都执行一下Transformations.map()，难道不是为了所谓的【优雅】脱裤子放屁吗？
     * 完全可以 mIndex 一个成员变量就够了啊？！可能就是追求所谓的优雅吧。。
     * 每次引用 mText 时，都得 转换一下，注意，人家map()是钩子，new Function并不是钩子，只是钩子的里面的转化方式
     * @return 返回新的LiveData<String>对象，虽说还是mText变量，但它引用的已经变了
     */
    public LiveData<String> getText() {
        return mText;
    }
}