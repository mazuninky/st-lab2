package ru.ifmo.st.lab2.program

abstract class BaseProgram : Program {
    private enum class State {
        PreInit, Created, Working, Stopped, Finished
    }

    private var state = State.PreInit

    final override fun start() {
        check(state == State.Created || state == State.Stopped || state == State.Finished)

        if (state != State.Finished) {
            state = State.Working
            onStart()
        }
    }

    protected open fun onStart() {

    }

    final override fun stop() {
        check(state == State.Working || state == State.Finished)

        if (state == State.Working) {
            state = State.Stopped

            onStop()
        }
    }


    protected open fun onStop() {

    }

    protected lateinit var context: Context
        private set


    protected lateinit var output: OutputHandler
        private set

    final override fun create(context: Context, output: OutputHandler) {
        check(state == State.PreInit)

        this.output = output

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


    protected fun showMessage(message: String) {
        output.showMessage(message)
    }
}

