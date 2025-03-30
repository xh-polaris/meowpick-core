package com.xhpolaris.meowpick.trigger.http.impl;

import com.xhpolaris.meowpick.domain.model.valobj.VoteStatsVO;
import com.xhpolaris.meowpick.domain.model.valobj.VoteVO;
import com.xhpolaris.meowpick.domain.service.VoteServer;
import com.xhpolaris.meowpick.trigger.http.api.VoteApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VoteController implements VoteApi {
    private final VoteServer voteServer;

    @Override
    public VoteStatsVO add(VoteStatsVO voteStatsVO) {
        return voteServer.addCourse(voteStatsVO);
    }

    @Override
    public VoteStatsVO execute(VoteVO voteVO) {
        return voteServer.voteForCourse(voteVO);
    }
}
