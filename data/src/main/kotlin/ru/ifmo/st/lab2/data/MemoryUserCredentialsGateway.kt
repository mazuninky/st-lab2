package ru.ifmo.st.lab2.data

import ru.ifmo.st.lab2.core.Credentials
import ru.ifmo.st.lab2.gateway.UserCredentialsGatewy

class MemoryUserCredentialsGatewy : UserCredentialsGatewy {
    private var storedCredentials: Credentials? = null

    override fun store(credentials: Credentials) {
        storedCredentials = credentials
    }

    override fun fetch(): Credentials? {
        return storedCredentials
    }
}
