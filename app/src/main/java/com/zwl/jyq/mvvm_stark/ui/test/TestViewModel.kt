package com.zwl.jyq.mvvm_stark.ui.test

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.zwl.jyq.mvvm_stark.base.AutoViewModel

@SuppressWarnings("checkResult")
class TestViewModel(private val repo: TestDataSourceRepository) : AutoViewModel() {

    companion object {

        fun instance(activity: FragmentActivity, repo: TestDataSourceRepository) =
            ViewModelProviders.of(activity, TestViewModelFactory(repo))
                .get(TestViewModel::class.java)

    }
}

@Suppress("UNCHECKED_CAST")
class TestViewModelFactory(private val repo: TestDataSourceRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = TestViewModel(repo) as T

}