package cn.qijianke.kkt.net;

import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.qijianke.kkt.KKTApplication;
import cn.qijianke.kkt.cache.Session;
import cn.qijianke.kkt.model.req.CalculateParam;
import cn.qijianke.kkt.model.req.DealRecordParam;
import cn.qijianke.kkt.model.req.DepositAmountParam;
import cn.qijianke.kkt.model.req.DepositCommitParam;
import cn.qijianke.kkt.model.req.DepositRecordParam;
import cn.qijianke.kkt.model.req.ExchangeTotalAmountParam;
import cn.qijianke.kkt.model.req.LoadProfitExchangeParam;
import cn.qijianke.kkt.model.req.LoginParam;
import cn.qijianke.kkt.model.req.LogoutParam;
import cn.qijianke.kkt.model.req.ModifyPasswordParam;
import cn.qijianke.kkt.model.req.ModifyUserInfoParam;
import cn.qijianke.kkt.model.req.ParternerParam;
import cn.qijianke.kkt.model.req.PosInfoParam;
import cn.qijianke.kkt.model.req.PosOrderNoParam;
import cn.qijianke.kkt.model.req.PosParam;
import cn.qijianke.kkt.model.req.PosTotalParam;
import cn.qijianke.kkt.model.req.ProductParam;
import cn.qijianke.kkt.model.req.ProductTypeParam;
import cn.qijianke.kkt.model.req.ProfitParam;
import cn.qijianke.kkt.model.req.ProfitTotalParam;
import cn.qijianke.kkt.model.req.RecommandMobilephoneParam;
import cn.qijianke.kkt.model.req.RegisterParam;
import cn.qijianke.kkt.model.req.ResetPasswordParam;
import cn.qijianke.kkt.model.req.SearchExchangeParam;
import cn.qijianke.kkt.model.req.ShareUrlParam;
import cn.qijianke.kkt.model.req.SmsParam;
import cn.qijianke.kkt.model.req.SubmitBaodanParam;
import cn.qijianke.kkt.model.req.SuggestParam;
import cn.qijianke.kkt.model.req.TotalDealAmountParam;
import cn.qijianke.kkt.model.req.UpgradeParam;
import cn.qijianke.kkt.model.req.UserInfoParam;
import cn.qijianke.kkt.model.req.VipInfoParam;
import cn.qijianke.kkt.model.req.VipUpgradeParam;
import cn.qijianke.kkt.model.resp.CalculateResp;
import cn.qijianke.kkt.model.resp.DealQueryRespWrapper;
import cn.qijianke.kkt.model.resp.DepositAmountResp;
import cn.qijianke.kkt.model.resp.DepositCommitResp;
import cn.qijianke.kkt.model.resp.DepositRecordRespWrapper;
import cn.qijianke.kkt.model.resp.ExchangeTotalAmountResp;
import cn.qijianke.kkt.model.resp.GroupDealQueryRespWrapper;
import cn.qijianke.kkt.model.resp.LoadProfitExchangeRespWrapper;
import cn.qijianke.kkt.model.resp.LoginResp;
import cn.qijianke.kkt.model.resp.LogoutResp;
import cn.qijianke.kkt.model.resp.ModifyPasswordResp;
import cn.qijianke.kkt.model.resp.ModifyUserInfoResp;
import cn.qijianke.kkt.model.resp.ParterRespWrapper;
import cn.qijianke.kkt.model.resp.PersonalProfitWrapper;
import cn.qijianke.kkt.model.resp.PosInfoRespWrapper;
import cn.qijianke.kkt.model.resp.PosOrderNoResp;
import cn.qijianke.kkt.model.resp.PosTotalResp;
import cn.qijianke.kkt.model.resp.ProductResp;
import cn.qijianke.kkt.model.resp.ProductRespWrapper;
import cn.qijianke.kkt.model.resp.ProductTypeResp;
import cn.qijianke.kkt.model.resp.ProductTypeRespWrapper;
import cn.qijianke.kkt.model.resp.ProfitTotalResp;
import cn.qijianke.kkt.model.resp.QueryPosRespWrapper;
import cn.qijianke.kkt.model.resp.RecommandMobilephoneResp;
import cn.qijianke.kkt.model.resp.RegisterResp;
import cn.qijianke.kkt.model.resp.ResetPasswordResp;
import cn.qijianke.kkt.model.resp.SearchExchangeRespWrapper;
import cn.qijianke.kkt.model.resp.ShareUrlResp;
import cn.qijianke.kkt.model.resp.SmsResp;
import cn.qijianke.kkt.model.resp.SpreadProfitWrapper;
import cn.qijianke.kkt.model.resp.SubmitBaodanResp;
import cn.qijianke.kkt.model.resp.SuggestResp;
import cn.qijianke.kkt.model.resp.TeamProfitWrapper;
import cn.qijianke.kkt.model.resp.TotalDealAmountResp;
import cn.qijianke.kkt.model.resp.UpgradeResp;
import cn.qijianke.kkt.model.resp.UploadImgResp;
import cn.qijianke.kkt.model.resp.UserInfoResp;
import cn.qijianke.kkt.model.resp.VipInfoResp;
import cn.qijianke.kkt.model.resp.VipUpgradeResp;

public class RequestWrapper {

    public static void sendSms(SmsParam smsParam) {
        Retrofit.getService().sendSms(Retrofit.convert(smsParam)).enqueue(new CallbackWrapper<SmsResp>() {
            @Override
            protected void onSuccess(SmsResp smsResp) {
                EventBus.getDefault().post(smsResp == null ? new SmsResp() : smsResp);
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void register(RegisterParam param) {
        Retrofit.getService().register(Retrofit.convert(param)).enqueue(new CallbackWrapper<RegisterResp>() {
            @Override
            protected void onSuccess(RegisterResp registerResp) {
                EventBus.getDefault().post(registerResp == null ? new RegisterResp() : registerResp);
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void login(LoginParam param) {
        Retrofit.getService().login(Retrofit.convert(param)).enqueue(new CallbackWrapper<LoginResp>() {
            @Override
            protected void onSuccess(LoginResp loginResp) {
                EventBus.getDefault().post(loginResp == null ? new LoginResp() : loginResp);
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void resetPassword(ResetPasswordParam param) {
        Retrofit.getService().resetPassword(Retrofit.convert(param)).enqueue(new CallbackWrapper<ResetPasswordResp>() {
            @Override
            protected void onSuccess(ResetPasswordResp resetPasswordResp) {
                EventBus.getDefault().post(resetPasswordResp == null ? new ResetPasswordResp() : resetPasswordResp);
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void logout(LogoutParam param) {
        Retrofit.getService().logout(Retrofit.convert(param)).enqueue(new CallbackWrapper<LogoutResp>() {
            @Override
            protected void onSuccess(LogoutResp logoutResp) {
                EventBus.getDefault().post(logoutResp == null ? new LogoutResp() : logoutResp);
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void upgrade(final UpgradeParam param) {
        Retrofit.getService().upgrade(Retrofit.convert(param)).enqueue(new CallbackWrapper<UpgradeResp>() {
            @Override
            protected void onSuccess(UpgradeResp upgradeResp) {
                EventBus.getDefault().post(upgradeResp == null ? new UpgradeResp(param.getTarget()) : upgradeResp.setTarget(param.getTarget()));
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void suggest(SuggestParam param) {
        Retrofit.getService().suggest(Retrofit.convert(param)).enqueue(new CallbackWrapper<SuggestResp>() {
            @Override
            protected void onSuccess(SuggestResp suggestResp) {
                EventBus.getDefault().post(suggestResp == null ? new SuggestResp() : suggestResp);
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void getUserInfo(final UserInfoParam param) {
        Retrofit.getService().getUserInfo(Retrofit.convert(param)).enqueue(new CallbackWrapper<UserInfoResp>() {
            @Override
            protected void onSuccess(UserInfoResp userInfoResp) {
                if (userInfoResp != null) {
                    LoginResp loginResp = Session.getSession().getLoginResp();
                    loginResp.setLevel(userInfoResp.getLevel());
                    loginResp.setUserName(userInfoResp.getName());
                    Session.getSession().saveLoginResp2Disk();
                }
                EventBus.getDefault().post(userInfoResp == null ? new UserInfoResp(param.getFrom()) : userInfoResp.setFrom(param.getFrom()));
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void modifyUserInfo(ModifyUserInfoParam param) {
        Retrofit.getService().modifyUserInfo(Retrofit.convert(param)).enqueue(new CallbackWrapper<ModifyUserInfoResp>() {
            @Override
            protected void onSuccess(ModifyUserInfoResp modifyUserInfoResp) {
                EventBus.getDefault().post(modifyUserInfoResp == null ? new ModifyUserInfoResp() : modifyUserInfoResp);
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void getVipInfo(VipInfoParam param) {
        Retrofit.getService().getVipInfo(Retrofit.convert(param)).enqueue(new CallbackWrapper<VipInfoResp>() {
            @Override
            protected void onSuccess(VipInfoResp vipInfoResp) {
                EventBus.getDefault().post(vipInfoResp == null ? new VipInfoResp() : vipInfoResp);
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void loadPosInfoList(final PosInfoParam param) {
        Retrofit.getService().loadPosInfoList(Retrofit.convert(param)).enqueue(new CallbackWrapper<PosInfoRespWrapper>() {
            @Override
            protected void onSuccess(PosInfoRespWrapper posInfoRespWrapper) {
                EventBus.getDefault().post(posInfoRespWrapper == null ? new PosInfoRespWrapper() : posInfoRespWrapper);
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void getPosOrderNo(final PosOrderNoParam param) {
        Retrofit.getService().getPosOrderNo(Retrofit.convert(param)).enqueue(new CallbackWrapper<PosOrderNoResp>() {
            @Override
            protected void onSuccess(PosOrderNoResp posOrderNoResp) {
                EventBus.getDefault().post(posOrderNoResp == null ? new PosOrderNoResp() : posOrderNoResp);
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void getProfitTotal(final ProfitTotalParam param) {
        Retrofit.getService().getProfitTotal(Retrofit.convert(param)).enqueue(new CallbackWrapper<ProfitTotalResp>() {
            @Override
            protected void onSuccess(ProfitTotalResp profitTotalResp) {
                EventBus.getDefault().post(profitTotalResp == null ? new ProfitTotalResp() : profitTotalResp);
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void getPersonalProfit(final ProfitParam param) {
        Retrofit.getService().getPersonalProfit(Retrofit.convert(param)).enqueue(new CallbackWrapper<PersonalProfitWrapper>() {
            @Override
            protected void onSuccess(PersonalProfitWrapper personalProfitWrapper) {
                EventBus.getDefault().post(personalProfitWrapper == null ? new PersonalProfitWrapper() : personalProfitWrapper);
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void getTeamProfit(final ProfitParam param) {
        Retrofit.getService().getTeamProfit(Retrofit.convert(param)).enqueue(new CallbackWrapper<TeamProfitWrapper>() {
            @Override
            protected void onSuccess(TeamProfitWrapper teamProfitWrapper) {
                EventBus.getDefault().post(teamProfitWrapper == null ? new TeamProfitWrapper() : teamProfitWrapper);
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void getSpreadProfit(final ProfitParam param) {
        Retrofit.getService().getSpreadProfit(Retrofit.convert(param)).enqueue(new CallbackWrapper<SpreadProfitWrapper>() {
            @Override
            protected void onSuccess(SpreadProfitWrapper spreadProfitWrapper) {
                EventBus.getDefault().post(spreadProfitWrapper == null ? new SpreadProfitWrapper() : spreadProfitWrapper);
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void getPaterners(final ParternerParam param) {
        Retrofit.getService().getPaterners(Retrofit.convert(param)).enqueue(new CallbackWrapper<ParterRespWrapper>() {
            @Override
            protected void onSuccess(ParterRespWrapper parterRespWrapper) {
                EventBus.getDefault().post(parterRespWrapper == null ? new ParterRespWrapper() : parterRespWrapper);
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void getDepositAmount(final DepositAmountParam param) {
        Retrofit.getService().getDepositAmount(Retrofit.convert(param)).enqueue(new CallbackWrapper<DepositAmountResp>() {
            @Override
            protected void onSuccess(DepositAmountResp depositAmountResp) {
                EventBus.getDefault().post(depositAmountResp == null ? new DepositAmountResp() : depositAmountResp);
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }


    public static void vipUpgrade(final VipUpgradeParam param) {
        Retrofit.getService().vipUpgrade(Retrofit.convert(param)).enqueue(new CallbackWrapper<VipUpgradeResp>() {
            @Override
            protected void onSuccess(VipUpgradeResp vipUpgradeResp) {
                EventBus.getDefault().post(vipUpgradeResp == null ? new VipUpgradeResp() : vipUpgradeResp);
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void getTotalDealAmount(final TotalDealAmountParam param) {
        Retrofit.getService().getTotalDealAmount(Retrofit.convert(param)).enqueue(new CallbackWrapper<TotalDealAmountResp>() {
            @Override
            protected void onSuccess(TotalDealAmountResp totalDealAmountResp) {
                EventBus.getDefault().post(totalDealAmountResp == null ? new TotalDealAmountResp() : totalDealAmountResp);
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void getPersonalDealRecord(final DealRecordParam param) {
        Retrofit.getService().getPersonalDealRecord(Retrofit.convert(param)).enqueue(new CallbackWrapper<DealQueryRespWrapper>() {
            @Override
            protected void onSuccess(DealQueryRespWrapper dealQueryRespWrapper) {
                EventBus.getDefault().post(dealQueryRespWrapper == null ? new DealQueryRespWrapper() : dealQueryRespWrapper);
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void getGroupDealRecord(final DealRecordParam param) {
        Retrofit.getService().getGroupDealRecord(Retrofit.convert(param)).enqueue(new CallbackWrapper<GroupDealQueryRespWrapper>() {
            @Override
            protected void onSuccess(GroupDealQueryRespWrapper groupDealQueryRespWrapper) {
                EventBus.getDefault().post(groupDealQueryRespWrapper == null ? new GroupDealQueryRespWrapper() : groupDealQueryRespWrapper);
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void getPos(final PosParam param) {
        Retrofit.getService().getPos(Retrofit.convert(param)).enqueue(new CallbackWrapper<QueryPosRespWrapper>() {
            @Override
            protected void onSuccess(QueryPosRespWrapper queryPosRespWrapper) {
                EventBus.getDefault().post(queryPosRespWrapper == null ? new GroupDealQueryRespWrapper() : queryPosRespWrapper);
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void getPosTotalAmount(final PosTotalParam param) {
        Retrofit.getService().getPosTotalAmount(Retrofit.convert(param)).enqueue(new CallbackWrapper<PosTotalResp>() {
            @Override
            protected void onSuccess(PosTotalResp posTotalResp) {
                EventBus.getDefault().post(posTotalResp == null ? new PosTotalResp() : posTotalResp);
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void depositCommit(final DepositCommitParam param) {
        Retrofit.getService().depositCommit(Retrofit.convert(param)).enqueue(new CallbackWrapper<DepositCommitResp>() {
            @Override
            protected void onSuccess(DepositCommitResp depositCommitResp) {
                EventBus.getDefault().post(depositCommitResp == null ? new DepositCommitResp() : depositCommitResp);
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void depositRecord(final DepositRecordParam param) {
        Retrofit.getService().depositRecord(Retrofit.convert(param)).enqueue(new CallbackWrapper<DepositRecordRespWrapper>() {
            @Override
            protected void onSuccess(DepositRecordRespWrapper depositRecordRespWrapper) {
                EventBus.getDefault().post(depositRecordRespWrapper == null ? new DepositRecordRespWrapper() : depositRecordRespWrapper);
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void getShareUrl(final ShareUrlParam param) {
        Retrofit.getService().getShareUrl(Retrofit.convert(param)).enqueue(new CallbackWrapper<ShareUrlResp>() {
            @Override
            protected void onSuccess(ShareUrlResp shareUrlResp) {
                EventBus.getDefault().post(shareUrlResp == null ? new ShareUrlResp() : shareUrlResp);
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void getProductType(final ProductTypeParam param) {
        Retrofit.getService().getProductType(Retrofit.convert(param)).enqueue(new CallbackWrapper<List<ProductTypeResp>>() {
            @Override
            protected void onSuccess(List<ProductTypeResp> list) {
                EventBus.getDefault().post(new ProductTypeRespWrapper(list));
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void calculate(final CalculateParam param) {
        Retrofit.getService().calculate(Retrofit.convert(param)).enqueue(new CallbackWrapper<CalculateResp>() {
            @Override
            protected void onSuccess(CalculateResp calculateResp) {
                EventBus.getDefault().post(calculateResp == null ? new CalculateResp() : calculateResp);
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void getProducts(final ProductParam param) {
        Retrofit.getService().getProducts(Retrofit.convert(param)).enqueue(new CallbackWrapper<List<ProductResp>>() {
            @Override
            protected void onSuccess(List<ProductResp> list) {
                EventBus.getDefault().post(new ProductRespWrapper(list));
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void uploadImg(final String param) {
        Retrofit.getService().uploadImg(Retrofit.convertImgUpload(param)).enqueue(new CallbackWrapper<UploadImgResp>() {
            @Override
            protected void onSuccess(UploadImgResp uploadImgResp) {
                EventBus.getDefault().post(uploadImgResp == null ? new UploadImgResp(param) : uploadImgResp.setImgPath(param));
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void submitBaodan(final SubmitBaodanParam param) {
        Retrofit.getService().submitBaodan(Retrofit.convert(param)).enqueue(new CallbackWrapper<SubmitBaodanResp>() {
            @Override
            protected void onSuccess(SubmitBaodanResp submitBaodanResp) {
                EventBus.getDefault().post(submitBaodanResp == null ? new SubmitBaodanResp() : submitBaodanResp);
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void getExchangeTotalAmount(final ExchangeTotalAmountParam param) {
        Retrofit.getService().getExchangeTotalAmount(Retrofit.convert(param)).enqueue(new CallbackWrapper<ExchangeTotalAmountResp>() {
            @Override
            protected void onSuccess(ExchangeTotalAmountResp exchangeTotalAmountResp) {
                EventBus.getDefault().post(exchangeTotalAmountResp == null ? new ExchangeTotalAmountResp() : exchangeTotalAmountResp);
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void searchExchange(final SearchExchangeParam param) {
        Retrofit.getService().searchExchange(Retrofit.convert(param)).enqueue(new CallbackWrapper<SearchExchangeRespWrapper>() {
            @Override
            protected void onSuccess(SearchExchangeRespWrapper searchExchangeRespWrapper) {
                EventBus.getDefault().post(searchExchangeRespWrapper == null ? new SearchExchangeRespWrapper(param.getTag()) : searchExchangeRespWrapper.setTag(param.getTag()));
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void getRecommandMobilephone(final RecommandMobilephoneParam param) {
        Retrofit.getService().getRecommandMobilephone(Retrofit.convert(param)).enqueue(new CallbackWrapper<RecommandMobilephoneResp>() {
            @Override
            protected void onSuccess(RecommandMobilephoneResp recommandMobilephoneResp) {
                EventBus.getDefault().post(recommandMobilephoneResp == null ? new RecommandMobilephoneResp() : recommandMobilephoneResp);
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void loadProfitExchange(final LoadProfitExchangeParam param) {
        Retrofit.getService().loadProfitExchange(Retrofit.convert(param)).enqueue(new CallbackWrapper<LoadProfitExchangeRespWrapper>() {
            @Override
            protected void onSuccess(LoadProfitExchangeRespWrapper loadProfitExchangeRespWrapper) {
                EventBus.getDefault().post(loadProfitExchangeRespWrapper == null ? new LoadProfitExchangeRespWrapper() : loadProfitExchangeRespWrapper);
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void modifyPassword(final ModifyPasswordParam param) {
        Retrofit.getService().modifyPassword(Retrofit.convert(param)).enqueue(new CallbackWrapper<ModifyPasswordResp>() {
            @Override
            protected void onSuccess(ModifyPasswordResp modifyPasswordResp) {
                EventBus.getDefault().post(modifyPasswordResp == null ? new ModifyPasswordResp() : modifyPasswordResp);
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }
}
