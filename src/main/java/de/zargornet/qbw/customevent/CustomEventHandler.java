package de.zargornet.qbw.customevent;

import com.google.common.reflect.ClassPath;
import de.zargornet.qbw.Qbw;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Handles custom events
 */
public class CustomEventHandler {
    private List<Method> classMethods = new ArrayList<>();

    /**
     * Register all methods with an {@linkplain QbwEvent} annotation
     */
    public CustomEventHandler() {
        try {
            ClassPath.from(Qbw.getInstance().getClass().getClassLoader()).getTopLevelClassesRecursive(Qbw.getInstance().getClass().getPackage().getName() + ".listeners.custom").forEach(classInfo -> {
                try {
                    Class clazz = Class.forName(classInfo.getName());
                    Arrays.asList(clazz.getMethods()).forEach(method -> {
                        if (method.getAnnotation(QbwEvent.class) != null) {
                            classMethods.add(method);
                        }
                    });
                } catch (ClassNotFoundException exe) {
                    exe.printStackTrace();
                }
            });
        } catch (IOException exe) {
            exe.printStackTrace();
        }
    }

    /**
     * Fires an event
     *
     * @param event Event {@link CustomEvent}
     */
    public void fireEvent(CustomEvent event) {
        classMethods.forEach(method -> {
            if (method.isAnnotationPresent(QbwEvent.class)) {
                try {
                    if (method.getParameterTypes()[0].getName().equalsIgnoreCase(event.getClass().getName()))
                        method.invoke(method.getDeclaringClass().newInstance(), event);
                } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public List<Method> getClassMethods() {
        return classMethods;
    }
}
