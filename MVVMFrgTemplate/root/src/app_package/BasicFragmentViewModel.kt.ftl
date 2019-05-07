package ${kotlinEscapedPackageName}.${objectKind?lower_case}

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

@SuppressWarnings("checkResult")
class ${viewModelClass}(private val repo: ${dataSourceRepositoryName}) : AutoViewModel() {

    companion object {

        fun instance(fragment: Fragment,repo: ${dataSourceRepositoryName}) =
            ViewModelProviders.of(fragment, ${viewModelClass}Factory(repo))
                .get(${viewModelClass}::class.java)
                
    }
}

@Suppress("UNCHECKED_CAST")
class ${viewModelClass}Factory(
    private val repo: ${dataSourceRepositoryName}
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        ${viewModelClass}(repo) as T
}