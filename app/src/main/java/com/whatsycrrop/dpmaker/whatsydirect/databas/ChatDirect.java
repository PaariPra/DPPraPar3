package com.whatsycrrop.dpmaker.whatsydirect.databas;

public class ChatDirect {
    int status;
    int count;

    public ChatDirect()
    {

    }
    public ChatDirect(int status, int count) {
        this.status = status;
        this.count = count;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
