package org.texttechnologylab.exception;

public class InputException extends Exception {

    public InputException() {
    }

    public InputException(String pMessage) {
        super(pMessage);
    }

    public InputException(String pMessage, Throwable pCause) {
        super(pMessage, pCause);
    }


}
