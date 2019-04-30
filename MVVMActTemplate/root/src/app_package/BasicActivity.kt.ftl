package ${kotlinEscapedPackageName}.${objectKind?lower_case}

import androidx.fragment.app.FragmentActivity
import android.content.Intent
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import org.kodein.di.android.retainedKodein
<#if applicationPackage??>
import ${applicationPackage}.R
import com.j1ang.mvvm.base.view.activity.BaseActivity

</#if>

class ${className} : BaseActivity() {

    override val kodein: Kodein = Kodein.lazy{
        extend(parentKodein)
        import(${kodeinModuleName})
    }

    val viewModel: ${viewModelClass} by instance()

    override val layoutId: Int = R.layout.${activity_layout}

    companion object {

        fun start(activity: FragmentActivity) =
                activity.apply {
                    startActivity(Intent(this, ${className}::class.java))
                }
    }
}
