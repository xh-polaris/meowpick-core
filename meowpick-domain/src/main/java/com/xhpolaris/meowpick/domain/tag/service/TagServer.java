package com.xhpolaris.meowpick.domain.tag.service;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.tag.model.entity.TagCmd;
import com.xhpolaris.meowpick.domain.tag.model.valobj.TagVO;
import com.xhpolaris.meowpick.domain.tag.repository.ITagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagServer {
    final ITagRepository tagRepository;

    public TagVO exec(TagCmd.CreateCmd cmd) {
        return null;
    }

    public TagVO remove(String id) {
        return null;
    }

    public TagVO exec(TagCmd.UpdateCmd cmd) {
        return null;
    }

    public PageEntity<TagVO> query(TagCmd.Query query) {
        return null;
    }

    public TagVO findById(String id) {
        return null;
    }
}
