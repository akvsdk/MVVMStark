package com.zwl.jyq.mvvm_stark.ui.test

import com.j1ang.mvvm.base.repository.BaseRepositoryBoth
import com.j1ang.mvvm.base.repository.ILocalDataSource
import com.j1ang.mvvm.base.repository.IRemoteDataSource

interface ITestRemoteDataSource : IRemoteDataSource

interface ITestLocalDataSource : ILocalDataSource

class TestDataSourceRepository(
    remoteDataSource: ITestRemoteDataSource,
    localDataSource: ITestLocalDataSource
) : BaseRepositoryBoth<ITestRemoteDataSource, ITestLocalDataSource>(remoteDataSource, localDataSource)

class TestRemoteDataSource : ITestRemoteDataSource

class TestLocalDataSource : ITestLocalDataSource