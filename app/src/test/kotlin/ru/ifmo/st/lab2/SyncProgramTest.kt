package ru.ifmo.st.lab2

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.Test
import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.domain.*
import ru.ifmo.st.lab2.domains.SyncServerUseCase
import ru.ifmo.st.lab2.framework.mock
import ru.ifmo.st.lab2.framework.runUITest
import ru.ifmo.st.lab2.program.*
import ru.ifmo.st.lab2.sample.makeFixedTask
import ru.ifmo.st.lab2.sample.sampleTask
import ru.ifmo.st.lab2.sl.create

class SyncProgramTest {

    @Test
    fun `test syncs with upload type`() = runUITest {
        program { it.create<SyncProgram>() }
        container {
            mock<SyncServerUseCase>()
            single<FetchUserInfoUseCase> {
                mock {
                    on { invoke() } doReturn "qwerty"
                }
            }
        }
        args(SyncProgram.UPLOAD)
        constructOutput {
            single(SyncProgram.OK)
        }
    }

    @Test
    fun `test syncs with download type`() = runUITest {
        program { it.create<SyncProgram>() }
        container {
            mock<SyncServerUseCase>()
            single<FetchUserInfoUseCase> {
                mock {
                    on { invoke() } doReturn "qwerty"
                }
            }
        }
        args(SyncProgram.DOWNLOAD)
        constructOutput {
            single(SyncProgram.OK)
        }
    }

    @Test
    fun `test syncs with unknown type`() = runUITest {
        program { it.create<SyncProgram>() }
        container {
            mock<SyncServerUseCase>()
            single<FetchUserInfoUseCase> {
                mock {
                    on { invoke() } doReturn "qwerty"
                }
            }
        }
        args("lol")
        constructOutput {
            single(SyncProgram.SYNC_TYPE_ERR)
        }
    }

    @Test
    fun `test syncs with null user`() = runUITest {
        program { it.create<SyncProgram>() }
        container {
            mock<SyncServerUseCase>()
            single<FetchUserInfoUseCase> {
                mock {
                    on { invoke() } doReturn null
                }
            }
        }
        args(SyncProgram.DOWNLOAD)
        constructOutput {
            single(SyncProgram.AUTH_ERR)
        }
    }

}
