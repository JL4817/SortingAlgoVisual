package org.example;

public class SortingController {
    private volatile boolean isPaused = false;
    private volatile boolean signalToStop = false;
    private Thread currentThread = null;
    private final Object sortingLock = new Object();

    // Switches between paused and running states.
    public void togglePause() {
        synchronized (sortingLock) {
            isPaused = !isPaused;
            if (!isPaused) { // If we just paused then wake up sorting thread
                sortingLock.notifyAll();
            }
        }
    }

    // Signals sorting thread to stop
    public void stop() {
        synchronized (sortingLock) {
            signalToStop = true;
            isPaused = false;
            sortingLock.notifyAll();
        }
    }

    // Clears all flags to prepare for next sort
    public void reset() {
        synchronized (sortingLock) {
            signalToStop = false;
            isPaused = false;
        }
    }

    public boolean isPaused() {
        return isPaused;
    }

    //  The sorting thread calls this method to check if it should pause or stop
    public void checkPauseAndStop() throws InterruptedException {
        synchronized (sortingLock) {
            while (isPaused && !signalToStop) { // if paused and no signal to stop
                sortingLock.wait(); // put thread to sleep
            }
            if (signalToStop) {
                throw new InterruptedException("Sorting stopped");
            }
        }
    }

    public void setCurrentThread(Thread thread) {
        this.currentThread = thread;
    }

    public Thread getCurrentThread() {
        return currentThread;
    }
}
