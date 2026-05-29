package com.lin.service;

import com.lin.model.User;
import com.lin.utils.PagedResult;


public interface UserService {
    /**
     * 查询用户列表
     * @param user 用户
     * @param page 当前页数
     * @param pageSize 每页条数
     * @return 分页结果
     */
    PagedResult queryUserList(User user, Integer page, Integer pageSize);
}
