package com.voronkov;

import java.io.Serializable;

public abstract class AbstractMessage implements Serializable {
    public abstract MessageType getMessageType();
}
