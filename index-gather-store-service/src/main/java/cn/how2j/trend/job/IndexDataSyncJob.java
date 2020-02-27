package cn.how2j.trend.job;

import cn.how2j.trend.pojo.Index;
import cn.how2j.trend.service.IndexDataService;
import cn.how2j.trend.service.IndexService;
import cn.hutool.core.date.DateUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

/**
 * describe:数据采集定时任务类
 *
 * @author 王立朝
 * @date 2020/02/16
 */
public class IndexDataSyncJob extends QuartzJobBean {
    @Autowired
    IndexService indexService;
    @Autowired
    IndexDataService indexDataService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("定时任务启动" + DateUtil.now());
        List<Index> indexList = indexService.fresh();
        for (Index index : indexList) {
            indexDataService.fresh(index.getCode());
        }
        System.out.println("定时任务结束" + DateUtil.now());
    }
}
