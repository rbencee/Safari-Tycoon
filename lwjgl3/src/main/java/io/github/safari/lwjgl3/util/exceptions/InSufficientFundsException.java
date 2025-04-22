package io.github.safari.lwjgl3.util.exceptions;

public class InSufficientFundsException extends RuntimeException {

    public InSufficientFundsException() {
        super("Insufficient funds to complete the transaction.");
    }


}
