package com.xhpolaris.meowpick.common.utils;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScoreTransfor {

    @Data
    public static class Score {
        private Float               avg;
        private Integer             total = 0;
        private Float               score;
        private Map<Integer, Float> percent;
    }

    public static Score transfor(List<Integer> data) {
        final List<Integer> nums = data.stream().filter(i -> i > 0).toList();
        if (nums.isEmpty()) {
            return new Score();
        }

        Map<Integer, Long> scoreMap = nums.stream().collect(Collectors.groupingBy(p -> p,
                Collectors.counting()));
        Integer sum = nums.stream().reduce(Integer::sum).orElse(0);
        float   avg = 1.0f * sum / nums.size();
        Score   vo  = new Score();

        vo.setScore((float) (2.25 * avg - 1.25));
        Map<Integer, Float> percent = scoreMap.entrySet()
                                              .stream()
                                              .collect(Collectors.toMap(Map.Entry::getKey,
                                                      e -> e.getValue() / nums.size() * 100.0f));

        vo.setAvg(avg);
        vo.setPercent(percent);
        vo.setTotal(nums.size());

        return vo;
    }
}
