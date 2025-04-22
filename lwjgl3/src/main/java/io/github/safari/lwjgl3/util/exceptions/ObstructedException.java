package io.github.safari.lwjgl3.util.exceptions;

public class ObstructedException extends RuntimeException {

    public ObstructedException() {
        super("Target place is obstructed!");
    }
}
