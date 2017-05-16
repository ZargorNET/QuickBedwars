package de.zargornet.qbw.game.arena;

import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.customevent.events.arena.ArenaEndsEvent;
import de.zargornet.qbw.customevent.events.arena.ArenaStartsEvent;
import de.zargornet.qbw.utils.Countdown;

/**
 * Class for starting an countdown in an arena
 */
public class QbwArenaCountdown {
    public static void startCountdown(QbwArena arena, QbwCounterType type) {
        if (arena.getCounters().containsKey(type))
            return;
        int id;
        switch (type) {
            case MAPVOTE:
                arena.setState(QbwArenaState.MAPVOTING);
                id = new Countdown(120) {
                    @Override
                    public void onTick(long left) {
                        String msg = "§3Mapvote ends in §e" + left + " seconds§3!";
                        if (!arena.getPlayers().isEmpty())
                            arena.getPlayers().forEach(qbwPlayer -> Qbw.getInstance().getNmsUtil().sendActionBar(qbwPlayer.getPlayer(), msg));
                    }

                    @Override
                    public void onFinish() {
                        QbwArenaUtil.broadcastToArena(arena, Qbw.getInstance().getPrefix() + "§3Mapvote ended!");
                        startCountdown(arena, QbwCounterType.TEAMSELECT);
                        arena.getCounters().remove(type);
                    }
                }.startAsync();
                arena.getCounters().put(type, id);
                break;

            case TEAMSELECT:
                arena.setState(QbwArenaState.TEAMSELECT);
                id = new Countdown(60) {
                    @Override
                    public void onTick(long left) {
                        String msg = "§3Teamselect ends in §e" + left + " seconds§3!";
                        if (!arena.getPlayers().isEmpty())
                            arena.getPlayers().forEach(qbwPlayer -> Qbw.getInstance().getNmsUtil().sendActionBar(qbwPlayer.getPlayer(), msg));
                    }

                    @Override
                    public void onFinish() {
                        QbwArenaUtil.broadcastToArena(arena, Qbw.getInstance().getPrefix() + "§3Teamselect ended!");
                        startCountdown(arena, QbwCounterType.STARTING);
                        arena.getCounters().remove(type);
                    }
                }.startAsync();
                arena.getCounters().put(type, id);
                break;
            case STARTING:
                arena.setState(QbwArenaState.LOBBY);
                id = new Countdown(30) {
                    @Override
                    public void onTick(long left) {
                        String msg = "§3Game starts in §e" + left + " seconds§3!";
                        if (!arena.getPlayers().isEmpty())
                            arena.getPlayers().forEach(qbwPlayer -> Qbw.getInstance().getNmsUtil().sendActionBar(qbwPlayer.getPlayer(), msg));
                        if (!(left == 30 || left == 15 || left <= 10))
                            return;
                        QbwArenaUtil.broadcastToArena(arena, Qbw.getInstance().getPrefix() + msg);
                    }

                    @Override
                    public void onFinish() {
                        Qbw.getInstance().getCustomEventHandler().fireEvent(new ArenaStartsEvent(arena));
                        arena.getCounters().remove(type);
                    }
                }.startAsync();
                arena.getCounters().put(type, id);
                break;
            case TIME_OUT:
                arena.setState(QbwArenaState.RUNNING);
                id = new Countdown(3600) {
                    @Override
                    public void onTick(long left) {
                        //TODO View in scoreboard
                    }

                    @Override
                    public void onFinish() {
                        QbwArenaUtil.broadcastToArena(arena, Qbw.getInstance().getPrefix() + "§3Time limit has been reached.");
                        Qbw.getInstance().getCustomEventHandler().fireEvent(new ArenaEndsEvent(arena, ArenaEndsEvent.EndReason.TIME_OUT));
                        arena.getCounters().remove(type);
                    }
                }.startAsync();
                arena.getCounters().put(type, id);
                break;

        }
    }
}
