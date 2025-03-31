package com.xhpolaris.meowpick.trigger.http.impl;

import com.xhpolaris.meowpick.common.PageEntity;
import com.xhpolaris.meowpick.domain.model.valobj.VoteStatsCmd;
import com.xhpolaris.meowpick.domain.model.valobj.VoteStatsVO;
import com.xhpolaris.meowpick.domain.model.valobj.VoteVO;
import com.xhpolaris.meowpick.domain.service.VoteServer;
import com.xhpolaris.meowpick.trigger.http.api.VoteApi;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VoteController implements VoteApi {
    private final VoteServer voteServer;

    @Override
    public VoteStatsVO add(VoteStatsCmd.CreateCmd cmd) {
        return voteServer.addCourse(cmd);
    }

    @Override
    public VoteStatsVO execute(VoteVO voteVO) {
        return voteServer.voteForCourse(voteVO);
    }

    @Override
    public VoteStatsVO del(String id) {
        return voteServer.removeVote(id);
    }

    @Override
    public VoteStatsVO update(VoteStatsCmd.UpdateCmd cmd) {
        return voteServer.updateVote(cmd);
    }

    @Override
    public PageEntity<VoteStatsVO> query(VoteStatsCmd.Query query) {
        PageEntity<VoteStatsVO> data = voteServer.query(query);
        if (CollectionUtils.isEmpty(data.getRows())) {
            return new PageEntity<>();
        }
        return data;
    }
}
