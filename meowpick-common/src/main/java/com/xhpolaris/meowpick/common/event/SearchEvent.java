package com.xhpolaris.meowpick.common.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SearchEvent extends ApplicationEvent {
    private final String text;
    private final String uid;
    private final String type;
    public SearchEvent(String uid, String type, String text) {
        super(text);

        this.uid = uid;
        this.type = type;
        this.text = text;
    }
}
