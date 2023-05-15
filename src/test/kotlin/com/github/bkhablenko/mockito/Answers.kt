package com.github.bkhablenko.mockito

import org.mockito.invocation.InvocationOnMock

inline fun <reified T> getArgument(index: Int) =
    { invocation: InvocationOnMock -> invocation.getArgument(index, T::class.java) }
