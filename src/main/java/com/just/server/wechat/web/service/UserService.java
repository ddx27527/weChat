package com.just.server.wechat.web.service;

import com.just.server.wechat.core.entity.BackUser;
import com.just.server.wechat.core.repository.BackUserDao;
import com.just.server.wechat.core.service.BaseService;
import com.just.server.wechat.web.model.BackUserModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: wangkx
 * Date: 2018/08/13
 * Time: 14:21
 * Description: No Description
 */
@Service
public class UserService extends BaseService {

    @Autowired
    private BackUserDao backUserDao;

    public Object getBackUser(BackUserModel model) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT ba.userId, ");
        sb.append(" ba.loginName, ");
        sb.append(" ba.name, ");
        sb.append(" ba.lastLoginDate, ");
        sb.append(" ba.createDate, ");
        sb.append(" ba.`status` ");
        sb.append(" FROM back_user ba ");
        sb.append(" WHERE 1=1 ");
        if (model.getEntity() != null){
            BackUser backUser  = model.getEntity();
            if(StringUtils.isNotBlank(backUser.getName())){
                sb.append(" and name like '%" + backUser.getName().trim()+"%'");
            }
            if(StringUtils.isNotBlank(backUser.getLoginName())){
                sb.append(" and loginName like '%" + backUser.getLoginName()+"%'");
            }
            if(backUser.getStatus() != null){
                sb.append(" and status = " + backUser.getStatus());
            }
        }
        sb.append(" order by ba.createDate desc ");
        return sqlHelper.queryForPage(sb.toString(),model.getPageModel());
    }
}
