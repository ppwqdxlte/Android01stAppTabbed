package com.laowang.android01stapptabbed.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.laowang.android01stapptabbed.R;
import com.laowang.android01stapptabbed.databinding.FragmentMainBinding;
/**
 * A placeholder fragment containing a simple view.
 * Fragment属于子Activity，存在宿主（activity），
 * fragment本身也可成为宿主，由自己的FragmentManager对象管理子Fragments
 */
public class PlaceholderFragment extends Fragment {
    /**
     * 参数部件编号？干啥的
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    /**
     * 页面视图模型【问题来了，此片段是悄悄放进 FragmentManager里了吗？是不是代表tab页签下的那个页面？】
     */
    private PageViewModel pageViewModel;
    /**
     * 主页面片段的捆绑对象
     */
    private FragmentMainBinding binding;
    /**
     * 获取 占位符片段的实例，此处代码可以优化，私以为可以整个【成员集合或数组】保存fragment和bundle对象
     * @param index 按照功能来看，是tab页签的索引
     * @return 占位符片段对象
     */
    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        // fragment.bundle.ARG_SECTION_NUMBER有值了，一个片段就有一个
        // bundle 相当于存储 key-value 键值对的 Map对象
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }
    /**
     * @param savedInstanceState 保存的实例状态
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化 成员PageView对象
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        // onCreate()只有在newInstance()之后执行才能获得 ARG_SECTION_NUMBER，
        // newInstance()在SectionsPagerAdapter中调用，【问题来了，onCreate()啥时候执行的？？？？？】
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentMainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.sectionLabel;
        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}