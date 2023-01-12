package com.Data;

public abstract class AbstractFactoryBoard {
    /**
     * Constructor
     */
    public AbstractFactoryBoard(){}

    /**
     * Creates board
     * @param size
     * @return
     */
    public abstract Board createBoard(int size);
}
