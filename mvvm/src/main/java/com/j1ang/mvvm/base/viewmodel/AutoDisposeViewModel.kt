/*
 * Copyright (c) 2019. Uber Technologies
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.j1ang.mvvm.base.viewmodel

import androidx.lifecycle.ViewModel
import com.uber.autodispose.lifecycle.CorrespondingEventsFunction
import com.uber.autodispose.lifecycle.LifecycleEndedException
import com.uber.autodispose.lifecycle.LifecycleScopeProvider
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

open class AutoDisposeViewModel : ViewModel(), IViewModel,
    LifecycleScopeProvider<AutoDisposeViewModel.ViewModelEvent> {


    //支持自动处理订阅的Subject
    private val lifecycleEvents = BehaviorSubject.createDefault(ViewModelEvent.CREATED)

    /**
     * [ViewModel]的生命周期事件
     *在创建之后，允许在[viewModel.onCleared]方法被销毁之前清除其中的任何资源。
     */
    enum class ViewModelEvent {
        CREATED, CLEARED
    }

    /**
     * 拥有生命周期的 [ViewModel].
     *
     * @return [Observable] modelling the [ViewModel] lifecycle.
     */
    override fun lifecycle(): Observable<ViewModelEvent> {
        return lifecycleEvents.hide()
    }


    override fun correspondingEvents(): CorrespondingEventsFunction<ViewModelEvent> {
        return CORRESPONDING_EVENTS
    }

    override fun peekLifecycle(): ViewModelEvent? {
        return lifecycleEvents.value
    }

    /**
     * 发出[ViewModelEvent.CLEARED]订阅事件
     * 释放ViewModel中的所有订阅
     */
    override fun onCleared() {
        lifecycleEvents.onNext(ViewModelEvent.CLEARED)
        super.onCleared()
    }

    companion object {
        /**
         *当前事件功能->目标处置事件。
         * [ViewModel]的生命周期非常简单。
         * 被创建，随后被清除
         * 因此，我们只有两个事件，所有订阅将仅在[ViewModelEvent.Cleared]处释放。
         */
        private val CORRESPONDING_EVENTS = CorrespondingEventsFunction<ViewModelEvent> { event ->
            when (event) {
                ViewModelEvent.CREATED -> ViewModelEvent.CLEARED
                else -> throw LifecycleEndedException(
                    "Cannot bind to ViewModel lifecycle after onCleared."
                )
            }
        }
    }
}