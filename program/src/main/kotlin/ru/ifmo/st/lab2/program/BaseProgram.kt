package ru.ifmo.st.lab2.program

abstract class BaseProgram : Program {
    private enum class State {
        PreInit, Created, Working, Stopped, Finished
    }

    private var state = State.PreInit

    final override fun start() {
        if (state == State.Created || state == State.Stopped) {
            state = State.Working
            onStart()
        } else throw IllegalProgramState()
    }

    protected open fun onStart() {

    }

    final override fun stop() {
        if (state != State.Working) {
            throw IllegalProgramState()
        }

        state = State.Stopped

        onStop()
    }


    protected open fun onStop() {

    }

    protected lateinit var context: Context
        private set

    final override fun create(context: Context) {
        if (state != State.PreInit) {
            throw IllegalProgramState()
        }

        this.context = context
        state = State.Created

        onCreate()
    }

    protected open fun onCreate() {

    }

    override val isWorking: Boolean
        get() = state == State.Working

    fun finish() {
        onFinish()
        state = State.Finished
    }

    protected fun onFinish() {

    }
}

