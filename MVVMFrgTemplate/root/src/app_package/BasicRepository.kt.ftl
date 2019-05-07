package ${kotlinEscapedPackageName}.${objectKind?lower_case}

import com.j1ang.mvvm.base.repository.BaseRepositoryBoth
import com.j1ang.mvvm.base.repository.ILocalDataSource
import com.j1ang.mvvm.base.repository.IRemoteDataSource

class ${dataSourceRepositoryName}(
    remoteDataSource: ${remoteDataSourceName},
    localDataSource: ${localDataSourceName}
) : BaseRepositoryBoth<${remoteDataSourceName}, ${localDataSourceName}>(remoteDataSource, localDataSource)

class ${remoteDataSourceName} : IRemoteDataSource

class ${localDataSourceName} : ILocalDataSource