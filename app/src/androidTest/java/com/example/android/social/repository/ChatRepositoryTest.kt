/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.social.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class ChatRepositoryTest {

    @Test
    fun getChats() = runTest {
        val repository = createTestRepository()
        repository.getChats().test {
            assertThat(awaitItem()).isEmpty()
            repository.initialize()
            assertThat(awaitItem()).isNotEmpty()
        }
    }

    @Test
    fun findChat() = runTest {
        val repository = createTestRepository()
        repository.findChat(1L).test {
            assertThat(awaitItem()).isNull()
            repository.initialize()
            assertThat(awaitItem()!!.firstContact.name).isEqualTo("Cat")
        }
    }

    @Test
    fun findMessages() = runTest {
        val repository = createTestRepository()
        repository.findMessages(1L).test {
            assertThat(awaitItem()).isEmpty()
            repository.initialize()
            assertThat(awaitItem()).hasSize(2)
        }
    }
}
