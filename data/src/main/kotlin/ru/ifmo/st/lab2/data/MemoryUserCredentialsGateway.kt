package ru.ifmo.st.lab2.data

class MemoryUserCredentialsGatewy : UserCredentialsGatewy {
    private var storedCredentials: Credentials? = null

    override fun store(credentials: Credentials) {
        storedCredentials = credentials
    }

    override fun fetch(): Credentials? {
        return storedCredentials
    }
}
