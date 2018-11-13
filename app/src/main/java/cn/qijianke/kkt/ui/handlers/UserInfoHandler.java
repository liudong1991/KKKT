package cn.qijianke.kkt.ui.handlers;

import cn.qijianke.kkt.cache.Session;
import cn.qijianke.kkt.model.req.UserInfoParam;
import cn.qijianke.kkt.net.RequestWrapper;
import cn.qijianke.kkt.utils.RSAUtil;

public class UserInfoHandler {

    public static void refresh(String from) {
        UserInfoParam param = new UserInfoParam();
        param.setFrom(from);
        String userId = Session.getSession().getLoginResp().getUserId();
        String token = Session.getSession().getLoginResp().getToken();
        param.setUserId(userId);
        param.setToken(token);
        param.setRsaValue(RSAUtil.getRSA(userId + token));
        RequestWrapper.getUserInfo(param);
    }

}
