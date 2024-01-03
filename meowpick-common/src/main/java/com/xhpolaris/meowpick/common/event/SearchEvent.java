package com.xhpolaris.meowpick.common.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SearchEvent extends ApplicationEvent {
    private String text;
    public SearchEvent(String text) {
        super(text);
    }
}
