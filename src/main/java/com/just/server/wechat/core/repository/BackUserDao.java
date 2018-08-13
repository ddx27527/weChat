package com.just.server.wechat.core.repository;

import com.just.server.wechat.core.entity.BackUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: wangkx
 * Date: 2018/08/13
 * Time: 14:22
 * Description: No Description
 */
@Repository
public interface BackUserDao extends JpaRepository<BackUser, Long>, JpaSpecificationExecutor<BackUser> {

    /**
     * 根据登录名查找用户
     * @param loginName
     * @return
     */
    BackUser findByLoginName(String loginName);

    /**
     * 根据用户ID查找用户
     * @param userId
     * @return
     */
    BackUser findByUserId(String userId);
}
