package com.xfyyim.cn.pay.new_ui;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.qrcode.utils.CommonUtils;
import com.xfyyim.cn.AppConfig;
import com.xfyyim.cn.MyApplication;
import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.QrKey;
import com.xfyyim.cn.helper.AvatarHelper;
import com.xfyyim.cn.helper.DialogHelper;
import com.xfyyim.cn.helper.OtpHelper;
import com.xfyyim.cn.helper.PaySecureHelper;
import com.xfyyim.cn.pay.EventPaymentSuccess;
import com.xfyyim.cn.pay.PaymentActivity;
import com.xfyyim.cn.sp.UserSp;
import com.xfyyim.cn.ui.base.BaseLoginFragment;
import com.xfyyim.cn.ui.base.CoreManager;
import com.xfyyim.cn.util.Base64;
import com.xfyyim.cn.util.DisplayUtil;
import com.xfyyim.cn.util.ScreenUtil;
import com.xfyyim.cn.util.ToastUtil;
import com.xfyyim.cn.util.secure.AES;
import com.xfyyim.cn.util.secure.MAC;
import com.xfyyim.cn.view.CircleImageView;
import com.xuan.xuanhttplibrary.okhttp.HttpUtils;
import com.xuan.xuanhttplibrary.okhttp.callback.BaseCallback;
import com.xuan.xuanhttplibrary.okhttp.result.ObjectResult;
import com.xuan.xuanhttplibrary.okhttp.result.Result;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends BaseLoginFragment {
    private Context mContext;
    private String mLoginUserId;
    private ImageView mPayQrCodeIv;
    private ImageView mPayBarCodeIv;
    // 每间隔一分钟刷新一次付款码
    private CountDownTimer mCodeRefreshCountDownTimer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            refreshPaymentCode();
        }
    };

    public PaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContext = requireContext();
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        initActionBar(view);
        initView(view);
        EventBus.getDefault().register(this);
        return view;
    }

    private void initActionBar(View v) {
        v.findViewById(R.id.iv_title_left).setOnClickListener(view -> requireActivity().finish());
        TextView titleTv = v.findViewById(R.id.tv_title_center);
        titleTv.setText(getString(R.string.payment_pay));
    }

    private void initView(View view) {
        mLoginUserId = CoreManager.getSelf(requireActivity()).getUserId();

        mPayQrCodeIv = view.findViewById(R.id.pm_qr_code_iv);
        mPayBarCodeIv = view.findViewById(R.id.pm_bar_code_iv);
        CircleImageView civ_user = view.findViewById(R.id.civ_user);
        AvatarHelper.getInstance().displayAvatar(mLoginUserId, civ_user);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refreshPaymentCode();
    }

    private String generateReceiptCode() {
        /**
         *  规则
         *  (userId+n+opt)的长度+(userId+n+opt)+opt+(time/opt)
         */
        String barCode;
        int type = 1;// 支付类型  1：账户余额    2：银行卡1  3：银行卡2  4：银行卡3  ....

        int n = 9;
        int userId = Integer.valueOf(CoreManager.getSelf(getActivity()).getUserId());
        String accessToken = CoreManager.getSelfStatus(getActivity()).accessToken;
        long time = System.currentTimeMillis() / 1000;

        // byte[] sha = DigestUtils.sha(accessToken + time + AppConfig.apiKey);
        // int opt = Math.abs(sha[0]);
        Random random = new Random();
        int opt = random.nextInt(100) + 100;

        String userCode = String.valueOf(userId + n + opt);
        int userCodeLen = userCode.length();
        barCode = String.valueOf(userCodeLen) + userCode + String.valueOf(opt);

        long timeCode = (time / opt);
        if (String.valueOf(timeCode).length() < 8) {
            timeCode = (time / (opt - 100));
        }
        barCode += String.valueOf(timeCode);
        Log.e("Payment", "opt-->" + opt);
        Log.e("Payment", "userId-->" + userId);
        Log.e("Payment", "time-->" + time);
        Log.e("Payment", barCode);
        Log.e("Payment", "Len-->" + barCode.length());
        return barCode;
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void helloEventBus(EventPaymentSuccess message) {
        DialogHelper.tip(requireContext(), getString(R.string.receipted, message.getReceiptName()));
        refreshPaymentCode();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCodeRefreshCountDownTimer.cancel();
        EventBus.getDefault().unregister(this);
    }

    private void refreshPaymentCode() {
        mCodeRefreshCountDownTimer.cancel();
        mCodeRefreshCountDownTimer.start();

        checkQrKey(this::updateQrImage, () -> {
            PaySecureHelper.inputPayPassword(mContext, getString(R.string.tip_enable_payment_qr_code), null, password -> requestQrCode(password, this::updateQrImage))
                    .setOnCancelListener(dialog -> {
                        requireActivity().finish();
                    });
        });
    }

    private void checkQrKey(PaymentActivity.Function<String> onSuccess, Runnable noQrCode) {
        String qrKeyBase64 = UserSp.getInstance(mContext).getQrKey();
        if (TextUtils.isEmpty(qrKeyBase64)) {
            noQrCode.run();
            return;
        }
        byte[] qrKey = Base64.decode(qrKeyBase64);
        String salt = String.valueOf(System.currentTimeMillis());
        String content = AppConfig.apiKey + coreManager.getSelf().getUserId() + UserSp.getInstance(mContext).getAccessToken() + salt;
        String mac = MAC.encodeBase64(content, qrKey);
        Map<String, String> p = new HashMap<>();
        p.put("salt", salt);
        p.put("mac", mac);
        HttpUtils.get().url(coreManager.getConfig().PAY_SECURE_VERIFY_QR_KEY)
                .params(p)
                .build()
                .execute(new BaseCallback<Void>(Void.class) {
                    @Override
                    public void onResponse(ObjectResult<Void> result) {
                        if (Result.checkError(result, Result.CODE_QR_KEY_INVALID)) {
                            // qrKey过期，
                            noQrCode.run();
                        } else if (Result.checkSuccess(mContext, result)) {
                            onSuccess.apply(generateQrCode(qrKey));
                        } else {
                            // 接口内部异常，不留在这个页面，
                            requireActivity().finish();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        DialogHelper.dismissProgressDialog();
                        // 断网情况不提示，直接使用本地qrKey生成付款码，不管qrKey是否过期，
                        onSuccess.apply(generateQrCode(qrKey));
                    }
                });
    }

    private void updateQrImage(String code) {
        Bitmap bitmap1 = CommonUtils.createQRCode(code, DisplayUtil.dip2px(MyApplication.getContext(), 160),
                DisplayUtil.dip2px(MyApplication.getContext(), 160));
        Bitmap bitmap2 = CommonUtils.createBarCode(code, ScreenUtil.getScreenWidth(MyApplication.getContext()) - DisplayUtil.dip2px(MyApplication.getContext(), 40),
                DisplayUtil.dip2px(MyApplication.getContext(), 80));
        mPayQrCodeIv.setImageBitmap(bitmap1);
        mPayBarCodeIv.setImageBitmap(bitmap2);
    }

    private void requestQrCode(String password, PaymentActivity.Function<String> callback) {
        DialogHelper.showDefaulteMessageProgressDialog(mContext);
        Map<String, String> params = new HashMap<String, String>();

        PaySecureHelper.generateParam(
                mContext, password, params,
                "",
                t -> {
                    DialogHelper.dismissProgressDialog();
                    ToastUtil.showToast(mContext, mContext.getString(R.string.tip_pay_secure_place_holder, t.getMessage()));
                    requireActivity().finish();
                }, (p, code) -> {
                    HttpUtils.get().url(coreManager.getConfig().PAY_SECURE_GET_QR_KEY)
                            .params(p)
                            .build()
                            .execute(new BaseCallback<QrKey>(QrKey.class) {

                                @Override
                                public void onResponse(ObjectResult<QrKey> result) {
                                    DialogHelper.dismissProgressDialog();
                                    if (Result.checkSuccess(mContext, result)
                                            && result.getData() != null
                                            && result.getData().getData() != null) {
                                        String qrKeyEncrypt = result.getData().getData();
                                        byte[] qrKey = AES.decryptFromBase64(qrKeyEncrypt, code);
                                        String qrCode = generateQrCode(qrKey);
                                        callback.apply(qrCode);
                                    } else {
                                        // 接口内部异常，不留在这个页面，
                                        requireActivity().finish();
                                    }
                                }

                                @Override
                                public void onError(Call call, Exception e) {
                                    DialogHelper.dismissProgressDialog();
                                    ToastUtil.showErrorNet(mContext);
                                    requireActivity().finish();
                                }
                            });
                });
    }

    public String generateQrCode(byte[] qrKey) {
        UserSp.getInstance(mContext).setQrKey(Base64.encode(qrKey));
        return OtpHelper.generate(Integer.valueOf(coreManager.getSelf().getUserId()), qrKey).getQrCodeString();
    }

    public interface Function<T> {
        void apply(T t);
    }

}
