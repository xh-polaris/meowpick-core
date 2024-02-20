package com.xhpolaris.meowpick.domain.search.adapter;

import com.xhpolaris.meowpick.domain.SearchComponent;
import com.xhpolaris.meowpick.domain.course.service.CourseServer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SearchProvider implements InitializingBean {
    private final CourseServer courseServer;

    private static final Map<String, SearchComponent<?>> maps = new HashMap<>(8);

    @Override
    public void afterPropertiesSet() {
        maps.put("course", courseServer);
    }

    public SearchComponent<?> get(String category) {
        SearchComponent<?> component = maps.get(category);
        if (component == null) {
            throw new UnsupportedOperationException();
        }

        return component;
    }
}
