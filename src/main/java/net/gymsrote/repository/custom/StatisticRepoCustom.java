package net.gymsrote.repository.custom;
import java.util.List;

import net.gymsrote.dto.statistic.StatisticDTO;
import net.gymsrote.dto.statistic.TodayStatisticDTO;
import net.gymsrote.entity.EnumEntity.EStatisticType;

public interface StatisticRepoCustom {
    List<StatisticDTO> statistic(
        List<Long> idBuyers,
        List<Long> idAdmins,
        Integer month,
        Integer quarter,
        Integer year,
        EStatisticType type);
    
    TodayStatisticDTO todayStatistic();
}
