package com.example.mytask.service.caculate;

import java.util.LinkedList;

public class FixedSizeQueue<T> {
    private LinkedList<T> queue = new LinkedList<>();
    private int maxSize;

    public FixedSizeQueue(int size) {
        this.maxSize = size;
    }

    public void enqueue(T item) {
        if (queue.size() == maxSize) {
            queue.removeFirst();
        }
        queue.addLast(item);
    }

    public T dequeue() {
        if (queue.isEmpty()) {
            return null;
        }
        return queue.removeFirst();
    }

    public int size() {
        return queue.size();
    }
}
