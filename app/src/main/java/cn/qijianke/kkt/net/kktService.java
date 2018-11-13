package cn.qijianke.kkt.net;

import java.util.List;

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
import cn.qijianke.kkt.model.resp.ProductTypeResp;
import cn.qijianke.kkt.model.resp.ProfitTotalResp;
import cn.qijianke.kkt.model.resp.QueryPosRespWrapper;
import cn.qijianke.kkt.model.resp.RecommandMobilephoneResp;
import cn.qijianke.kkt.model.resp.RegisterResp;
import cn.qijianke.kkt.model.resp.ResetPasswordResp;
import cn.qijianke.kkt.model.resp.RespDto;
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
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface kktService {

    @POST("/sms/send")
    Call<RespDto<SmsResp>> sendSms(@Body RequestBody route);

    @POST("/userinfo/register")
    Call<RespDto<RegisterResp>> register(@Body RequestBody route);

    @POST("/userinfo/login")
    Call<RespDto<LoginResp>> login(@Body RequestBody route);

    @POST("/userinfo/reset")
    Call<RespDto<ResetPasswordResp>> resetPassword(@Body RequestBody route);

    @POST("/userinfo/logout")
    Call<RespDto<LogoutResp>> logout(@Body RequestBody route);

    @POST("/version/upgrade")
    Call<RespDto<UpgradeResp>> upgrade(@Body RequestBody route);

    @POST("/feedback/add")
    Call<RespDto<SuggestResp>> suggest(@Body RequestBody route);

    @POST("/userinfo/loadUserInfo")
    Call<RespDto<UserInfoResp>> getUserInfo(@Body RequestBody route);

    @POST("/userinfo/update")
    Call<RespDto<ModifyUserInfoResp>> modifyUserInfo(@Body RequestBody route);

    @POST("/userinfo/vipInfo")
    Call<RespDto<VipInfoResp>> getVipInfo(@Body RequestBody route);

    @POST("/posinfo/loadPosInfoList")
    Call<RespDto<PosInfoRespWrapper>> loadPosInfoList(@Body RequestBody route);

    @POST("/posinfo/posBuy")
    Call<RespDto<PosOrderNoResp>> getPosOrderNo(@Body RequestBody route);

    @POST("/profit/profitTotal")
    Call<RespDto<ProfitTotalResp>> getProfitTotal(@Body RequestBody route);

    @POST("/profit/loadProfitPerson")
    Call<RespDto<PersonalProfitWrapper>> getPersonalProfit(@Body RequestBody route);

    @POST("/profit/loadProfitTeam")
    Call<RespDto<TeamProfitWrapper>> getTeamProfit(@Body RequestBody route);

    @POST("/profit/loadProfitRecommend")
    Call<RespDto<SpreadProfitWrapper>> getSpreadProfit(@Body RequestBody route);

    @POST("/merchant/loadMerchant")
    Call<RespDto<ParterRespWrapper>> getPaterners(@Body RequestBody route);

    @POST("/wallet/cashback")
    Call<RespDto<DepositAmountResp>> getDepositAmount(@Body RequestBody route);

    @POST("/upgradePurchase/purchase")
    Call<RespDto<VipUpgradeResp>> vipUpgrade(@Body RequestBody route);

    @POST("/trade/profitAmount")
    Call<RespDto<TotalDealAmountResp>> getTotalDealAmount(@Body RequestBody route);

    @POST("/trade/personal")
    Call<RespDto<DealQueryRespWrapper>> getPersonalDealRecord(@Body RequestBody route);

    @POST("/trade/team")
    Call<RespDto<GroupDealQueryRespWrapper>> getGroupDealRecord(@Body RequestBody route);

    @POST("/merchant/pos")
    Call<RespDto<QueryPosRespWrapper>> getPos(@Body RequestBody route);

    @POST("/merchant/sumpos")
    Call<RespDto<PosTotalResp>> getPosTotalAmount(@Body RequestBody route);

    @POST("/wallet/withdraw")
    Call<RespDto<DepositCommitResp>> depositCommit(@Body RequestBody route);

    @POST("/wallet/presentRecord")
    Call<RespDto<DepositRecordRespWrapper>> depositRecord(@Body RequestBody route);

    @POST("/recommend/share")
    Call<RespDto<ShareUrlResp>> getShareUrl(@Body RequestBody route);

    @POST("/exchange/type")
    Call<RespDto<List<ProductTypeResp>>> getProductType(@Body RequestBody route);

    @POST("/exchange/calc")
    Call<RespDto<CalculateResp>> calculate(@Body RequestBody route);

    @POST("/exchange/check")
    Call<RespDto<List<ProductResp>>> getProducts(@Body RequestBody route);

    @Multipart
    @POST("/exchange/imgUpload")
    Call<RespDto<UploadImgResp>> uploadImg(@Part List<MultipartBody.Part> parts);

    @POST("/exchange/submit")
    Call<RespDto<SubmitBaodanResp>> submitBaodan(@Body RequestBody route);

    @POST("/exchange/amount")
    Call<RespDto<ExchangeTotalAmountResp>> getExchangeTotalAmount(@Body RequestBody route);

    @POST("/exchange/search")
    Call<RespDto<SearchExchangeRespWrapper>> searchExchange(@Body RequestBody route);

    @POST("/recommend/loadReferee")
    Call<RespDto<RecommandMobilephoneResp>> getRecommandMobilephone(@Body RequestBody route);

    @POST("/profit/loadProfitExchange")
    Call<RespDto<LoadProfitExchangeRespWrapper>> loadProfitExchange(@Body RequestBody route);

    @POST("/userinfo/updatepwd")
    Call<RespDto<ModifyPasswordResp>> modifyPassword(@Body RequestBody route);
}
