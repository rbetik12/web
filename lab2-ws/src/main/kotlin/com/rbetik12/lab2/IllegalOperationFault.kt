package com.rbetik12.lab2

open class IllegalOperationFault {
    protected var message = ""

    companion object {
        @JvmStatic
        open fun defaultInstance(): IllegalOperationFault? {
            val fault = IllegalOperationFault()
            fault.message = ""
            return fault
        }
    }
}