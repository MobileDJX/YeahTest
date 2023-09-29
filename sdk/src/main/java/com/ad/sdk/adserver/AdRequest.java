package com.ad.sdk.adserver;

import android.content.Context;

import com.ad.sdk.utils.Cdlog;

import io.reactivex.Observable;

class AdRequest {

    private final AdResponse adResObj = new AdResponse();
    private final AdRequestParam mAdReqObj;
    Context adcontext;

    public AdRequest(AdRequestParam adReqObj, Context context) {
        mAdReqObj = adReqObj;
        adcontext = context;
    }

    public Observable<AdResponse> execute() {
        return Observable.create(emitter -> {
            if (mAdReqObj != null) {
                String jsonStr = new ServiceHandler().makeServiceCall();
                adResObj.readAndParseJSON(jsonStr, adcontext);
                emitter.onNext(adResObj);
                emitter.onComplete();
            } else {
                emitter.onError(new Throwable("AdRequest Object Required."));
                Cdlog.d(Cdlog.debugLogTag, "AdRequest Object Required.");
            }
        });
    }
}

