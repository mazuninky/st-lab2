package ru.ifmo.st.lab2.state

class StateMachine<State : Any>(
        private var value: State,
        private val handlerMap: Map<State, StateHandlerBody<State>.() -> Unit>
) {
    private val context = StateMachineContext<State>()

    companion object {
        fun <State : Any> create(body: StateMachineBuilder<State>.() -> Unit): StateMachine<State> {
            return StateMachineBuilder<State>()
                    .apply(body)
                    .build()
        }
    }

    fun handle(input: String) {
        val handler = handlerMap
                .getValue(value)

        StateHandlerBody(context, input)
                .apply(handler)

        context.processRequest {
            if(it is Request.TransitTo<*>) {
                value = it.to as State
            }
        }
    }
}

class StateMachineBuilder<State : Any> {
    private var initialState: State? = null
    private var handlerMap: MutableMap<State, StateHandlerBody<State>.() -> Unit> = hashMapOf()

    fun setInitialState(state: State) {
        initialState = state
    }

    fun state(state: State, body: StateHandlerBody<State>.() -> Unit) {
        handlerMap[state] = body
    }

    fun build(): StateMachine<State> {
        return StateMachine(requireNotNull(initialState), handlerMap)
    }
}

class StateHandlerBody<State : Any>(private val context: StateMachineContext<State>,
                                    val input: String) {

    fun transitionTo(to: State) {
        context.add(Request.TransitTo(to))
    }
}

sealed class Request {
    data class TransitTo<State>(val to: State) : Request()
}

class StateMachineContext<State : Any> {
    private val requests: MutableList<Request> = mutableListOf()

    fun add(request: Request) {
        requests.add(request)
    }

    fun addAllRequest(all: Collection<Request>) {
        requests.addAll(all)
    }

    internal inline fun processRequest(handler: (Request) -> Unit) {
        requests.forEach(handler)
        requests.clear()
    }
}
