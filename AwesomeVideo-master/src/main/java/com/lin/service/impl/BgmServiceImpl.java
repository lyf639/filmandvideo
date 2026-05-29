package com.lin.service.impl;

import com.lin.dao.BgmMapper;
import com.lin.model.Bgm;
import com.lin.service.BgmService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
@CacheConfig(cacheNames={"BgmServiceImpl"})
public class BgmServiceImpl
implements BgmService {
    @Autowired
    private BgmMapper bgmMapper;

    @Override
    @Cacheable(key="targetClass + methodName")
    public List<Bgm> queryBgmList() {
        return this.bgmMapper.selectAll();
    }

    @Override
    @Cacheable(key="targetClass + methodName + #p0")
    public Bgm queryBgmById(String bgmId) {
        Example bgmExample = new Example(Bgm.class);
        Example.Criteria criteria = bgmExample.createCriteria();
        criteria.andEqualTo("id", (Object)bgmId);
        return (Bgm)this.bgmMapper.selectOneByExample(bgmExample);
    }
}
