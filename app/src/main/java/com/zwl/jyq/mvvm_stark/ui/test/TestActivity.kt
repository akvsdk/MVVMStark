package com.zwl.jyq.mvvm_stark.ui.test

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.j1ang.mvvm.base.view.activity.BaseActivity
import com.j1ang.mvvm.image.load
import com.zwl.jyq.mvvm_stark.R
import kotlinx.android.synthetic.main.activity_test.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance


class TestActivity : BaseActivity() {

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(testKodeinModule)
    }

    val viewModel: TestViewModel by instance()

    override val layoutId: Int = R.layout.activity_test

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        test_iv.load("https://ws1.sinaimg.cn/large/54d358dbly1g2brx5w801j20fl0ldmxq.jpg")
    }

    companion object {

        fun start(activity: FragmentActivity) =
            activity.apply {
                startActivity(Intent(this, TestActivity::class.java))
            }
    }
}
