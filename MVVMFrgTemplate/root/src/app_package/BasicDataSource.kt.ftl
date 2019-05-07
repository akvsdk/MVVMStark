package ${kotlinEscapedPackageName}.${objectKind?lower_case}

import com.j1ang.mvvm.base.repository.BaseRepositoryBoth
import com.j1ang.mvvm.base.repository.ILocalDataSource
import com.j1ang.mvvm.base.repository.IRemoteDataSource

interface ${remoteDataSourceInterfaceName} : IRemoteDataSource

interface ${localDataSourceInterfaceName} : ILocalDataSource

class ${dataSourceRepositoryName}(
    remoteDataSource: ${remoteDataSourceInterfaceName},
    localDataSource: ${localDataSourceInterfaceName}
) : BaseRepositoryBoth<${remoteDataSourceInterfaceName}, ${localDataSourceInterfaceName}>(remoteDataSource, localDataSource)

class ${remoteDataSourceName} : ${remoteDataSourceInterfaceName}

class ${localDataSourceName} : ${localDataSourceInterfaceName}