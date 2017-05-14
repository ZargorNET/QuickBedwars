package de.zargornet.qbw.commands;

import com.google.common.reflect.ClassPath;
import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.utils.Messages;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


/**
 * Handles the '/qbw' command
 */
public class QbwCommandHandler implements CommandExecutor {
    private List<Class> classes = new ArrayList<>();

    /**
     * Adds all methods which have the annotation {@linkplain QbwCommandPath} to a list
     */
    public QbwCommandHandler() {
        try {
            for (ClassPath.ClassInfo classInfo : ClassPath.from(Qbw.getInstance().getClass().getClassLoader()).getTopLevelClassesRecursive(this.getClass().getPackage().getName())) {
                Class clazz = Class.forName(classInfo.getName());
                if (Arrays.stream(clazz.getInterfaces()).anyMatch(i -> i == IQbwCommand.class)) {
                    if (clazz.isAnnotationPresent(QbwCommandPath.class)) {
                        Optional<Method> method = Arrays.stream(clazz.getMethods()).filter(m -> m.getName().equalsIgnoreCase("onCommand")).findFirst();
                        method.ifPresent(m -> classes.add(clazz));
                    }
                }
            }
        } catch (ClassNotFoundException | IOException exe) {
            exe.printStackTrace();
        }
    }

    /**
     * Gets the command from Bukkit and execute the <b>fireCommand</b> method if args bigger than 0
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            String s = "§7§m----§r §eQuickBedwars §7§m----§r \n";
            s += "§7- §e/QBW §7Shows this message \n";
            s += "§7- §e/QBW join <Arena> §7Join an arena \n";
            s += "§7- §e/QBW leave §7Leaves an arena \n";
            s += "§7- §e/QBW stats §7Show your stats \n";
            s += "§7- §e/QBW help §7 Shows the help message \n";
            s += " \n";
            s += "§7Plugin by ";

            if (sender instanceof Player) {
                TextComponent component = new TextComponent(s);
                TextComponent zargor = new TextComponent("§e§nZargor");
                zargor.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://dev.bukkit.org/projects/qbw"));
                zargor.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Go to project page").create()));
                component.addExtra(zargor);
                Player p = (Player) sender;
                p.spigot().sendMessage(component);
            } else {
                s += "§e§nZargor";
                sender.sendMessage(s);
            }
        } else {
            fireCommand(sender, command, args);
        }
        return true;
    }

    /**
     * Fire all methods who has {@link QbwCommandPath} annotation & has the path value equals to the args[0] from onCommand
     *
     * @param sender CommandSender
     * @param cmd    Command
     * @param args   Args
     */
    //
    private void fireCommand(CommandSender sender, Command cmd, String[] args) {
        final boolean[] commandFound = {false};
        for (Class clazz : classes) {
            try {
                QbwCommandPath commandPath = (QbwCommandPath) clazz.getAnnotation(QbwCommandPath.class);
                Optional<Method> methodOptional = Arrays.stream(clazz.getMethods()).filter(m -> m.getName().equalsIgnoreCase("onCommand")).findFirst();
                if (!methodOptional.isPresent()) {
                    break;
                }
                Method method = methodOptional.get();
                if (args[0].equalsIgnoreCase(commandPath.path())) {
                    if (sender instanceof Player) {
                        Player p = (Player) sender;
                        if (!(p.hasPermission(commandPath.permission()) || (commandPath.masterPermission() && p.hasPermission("qbw.*")))) {
                            commandFound[0] = true;
                            p.sendMessage(Qbw.getInstance().getPrefix() + Messages.NO_PERMISSIONS);
                            break;
                        }
                    }
                    Object[] cmdArgs = {sender, cmd, Arrays.copyOfRange(args, 1, args.length)};
                    if (commandPath.async()) {
                        Bukkit.getScheduler().runTaskAsynchronously(Qbw.getInstance(), () -> {
                            try {
                                method.invoke(clazz.newInstance(), cmdArgs);
                            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                                e.printStackTrace();
                            }
                        });
                    } else {
                        method.invoke(clazz.newInstance(), cmdArgs);
                    }
                    commandFound[0] = true;
                }
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        if (!commandFound[0]) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + Messages.COMMAND_NOT_FOUND.toString());
        }
    }
}
