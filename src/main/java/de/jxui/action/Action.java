package de.jxui.action;

import de.jxui.events.Event;
import de.jxui.utils.UserState;

public interface Action<T extends Event> {
    boolean run(UserState userState, T event);
}
