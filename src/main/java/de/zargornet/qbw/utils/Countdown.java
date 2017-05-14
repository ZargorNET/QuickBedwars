package de.zargornet.qbw.utils;

import de.zargornet.qbw.Qbw;
import org.bukkit.Bukkit;

/**
 * Created by Zargor on 26.12.2016.
 */
public abstract class Countdown {
    private long timeleft;
    private int ID = 0;

    /***
     *  Inits the countdown
     * @param secs Seconds until the timer ends
     */
    public Countdown(long secs) {
        this.timeleft = secs;
    }

    /***
     * Starts the countdown asynchronusly
     * @return this.
     */
    public synchronized final int startAsync() {
        if (ID == 0) {
            ID = Bukkit.getScheduler().runTaskTimerAsynchronously(Qbw.getInstance(), () -> {
                timeleft--;
                if (timeleft <= 0) {
                    onFinish();
                    Bukkit.getScheduler().cancelTask(ID);
                } else {
                    onTick(timeleft);
                }
            }, 0L, 20L).getTaskId();
        }
        return ID;
    }

    /***
     * Cancel running countdown
     */
    public final void cancel() {
        if (Bukkit.getScheduler().isCurrentlyRunning(ID)) {
            Bukkit.getScheduler().cancelTask(ID);
        }
    }

    /***
     * Called when the countdown lost one second
     * @param left Seconds until the timer stops
     */
    public abstract void onTick(long left);

    /***
     * Called when the timer finished
     */
    public abstract void onFinish();

    public int getID() {
        return ID;
    }
}
