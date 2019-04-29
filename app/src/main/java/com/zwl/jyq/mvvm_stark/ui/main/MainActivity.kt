package com.zwl.jyq.mvvm_stark.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.j1ang.mvvm.base.view.activity.BaseActivity
import com.j1ang.mvvm.ext.livedata.map
import com.j1ang.mvvm.ext.livedata.toReactiveStream
import com.j1ang.mvvm.ext.rx.clicksThrottleFirst
import com.uber.autodispose.autoDisposable
import com.zwl.jyq.mvvm_stark.R
import com.zwl.jyq.mvvm_stark.manager.UserManager
import kotlinx.android.synthetic.main.activity_text.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class MainActivity : BaseActivity() {

    override val layoutId = R.layout.activity_text

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(mainModule)
    }

    private val mViewModel: MainViewModel by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binds()
    }

    private fun binds() {
        mViewModel.result.toReactiveStream().autoDisposable(scopeProvider).subscribe {
            test_tv.text = it.invitestring
        }
        test_tv.clicksThrottleFirst().autoDisposable(scopeProvider).subscribe {
            mViewModel.getUpdateInfo(test_tv.text.toString())
        }
        tips_tv.clicksThrottleFirst().autoDisposable(scopeProvider).subscribe {
            tips_tv.text = UserManager.INSTANCE.version
            mViewModel.showSp()
        }
        mViewModel.timeToShow.map { if (it) View.VISIBLE else View.GONE }
            .toReactiveStream()
            .autoDisposable(scopeProvider).subscribe {
                mProgressBar.visibility = it
            }
    }


    companion object {

        fun launch(activity: FragmentActivity) =
            activity.apply {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
    }
}