package com.rbetik12.lab2

import jakarta.xml.ws.WebFault


@WebFault(faultBean = "com.rbetik12.lab.IllegalOperationFault")
class IllegalOperationException : Exception {
    private val fault: IllegalOperationFault

    constructor(message: String?, fault: IllegalOperationFault) : super(message) {
        this.fault = fault
    }

    constructor(
        message: String?, fault: IllegalOperationFault,
        cause: Throwable?
    ) : super(message, cause) {
        this.fault = fault
    }

    val faultInfo: IllegalOperationFault
        get() = fault

    companion object {
        private const val serialVersionUID = -6647544772732631047L
    }
}