package com.lin.service.impl;

import com.lin.dao.UserFansMapper;
import com.lin.dao.UserLikeVideosMapper;
import com.lin.dao.UserMapper;
import com.lin.dao.UserReportMapper;
import com.lin.model.User;
import com.lin.model.UserFans;
import com.lin.model.UserLikeVideos;
import com.lin.model.UserReport;
import com.lin.service.UserService;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

@Service
@CacheConfig(cacheNames={"UserServiceImpl"})
public class UserServiceImpl
implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserLikeVideosMapper userLikeVideosMapper;
    @Autowired
    private UserReportMapper userReportMapper;
    @Autowired
    private UserFansMapper userFansMapper;
    @Autowired
    private Sid sid;

    @Override
    @Transactional(propagation=Propagation.SUPPORTS)
    @Cacheable(key="targetClass + methodName + #p0")
    public boolean queryUsernameIsExist(String username) {
        User user = new User();
        user.setUsername(username);
        User result = (User)this.userMapper.selectOne(user);
        return result != null;
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    @CacheEvict(allEntries=true)
    public boolean saveUser(User user) {
        UserServiceImpl userServiceImpl = this;
        String userId = userServiceImpl.sid.nextShort();
        user.setId(userId);
        int effectCount = this.userMapper.insert(user);
        return effectCount == 1;
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS)
    @Cacheable(key="targetClass + methodName + #p0 + #p1")
    public User queryUserForLogin(String username, String password) {
        Example userExample = new Example(User.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username", (Object)username);
        criteria.andEqualTo("password", (Object)password);
        return (User)this.userMapper.selectOneByExample(userExample);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    @CacheEvict(allEntries=true)
    public void updateUserInfo(User user) {
        Example userExample = new Example(User.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("id", (Object)user.getId());
        this.userMapper.updateByExampleSelective(user, userExample);
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS)
    @Cacheable(key="targetClass + methodName + #p0")
    public User queryUserInfo(String userId) {
        Example userExample = new Example(User.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("id", (Object)userId);
        return (User)this.userMapper.selectOneByExample(userExample);
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS)
    @Cacheable(key="targetClass + methodName + #p0 + #p1")
    public boolean isUserLikeVideo(String userId, String videoId) {
        if (StringUtils.isBlank((CharSequence)userId) || StringUtils.isBlank((CharSequence)videoId)) {
            return false;
        }
        Example example = new Example(UserLikeVideos.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", (Object)userId);
        criteria.andEqualTo("videoId", (Object)videoId);
        List list = this.userLikeVideosMapper.selectByExample(example);
        return list != null && list.size() > 0;
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    @CacheEvict(allEntries=true)
    public void reportUser(UserReport userReport) {
        UserServiceImpl userServiceImpl = this;
        String reportId = userServiceImpl.sid.nextShort();
        userReport.setId(reportId);
        userReport.setCreateDate(new Date());
        this.userReportMapper.insert(userReport);
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS)
    @Cacheable(key="targetClass + methodName + #p0 + #p1")
    public boolean queryIfFollow(String userId, String fanId) {
        Example example = new Example(UserFans.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", (Object)userId);
        criteria.andEqualTo("fanId", (Object)fanId);
        List list = this.userFansMapper.selectByExample(example);
        return !CollectionUtils.isEmpty((Collection)list);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    @CacheEvict(allEntries=true)
    public void saveUserFanRelation(String userId, String fanId) {
        UserServiceImpl userServiceImpl = this;
        String id = userServiceImpl.sid.nextShort();
        UserFans userFans = new UserFans();
        userFans.setId(id);
        userFans.setUserId(userId);
        userFans.setFanId(fanId);
        this.userFansMapper.insert(userFans);
        this.userMapper.addFansCount(userId);
        this.userMapper.addFollowersCount(fanId);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    @CacheEvict(allEntries=true)
    public void deleteUserFanRelation(String userId, String fanId) {
        Example example = new Example(UserFans.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", (Object)userId);
        criteria.andEqualTo("fanId", (Object)fanId);
        this.userFansMapper.deleteByExample(example);
        this.userMapper.reduceFansCount(userId);
        this.userMapper.reduceFollowersCount(fanId);
    }
}
