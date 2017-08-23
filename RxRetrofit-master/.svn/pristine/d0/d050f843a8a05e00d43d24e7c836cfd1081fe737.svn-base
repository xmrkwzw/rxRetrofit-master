package com.xmjj.rxretrofit_master.http;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 功能描述：
 * Created by wzw
 * 2017/8/18
 */

public interface HttpApiService {
	@GET("brand/brandBaseInfo")
	Observable<String> getBaseInfo(@Query("seqNum") String seqNum);

	@GET("brand/getMoralRateData")
	Observable<String> getCivilization(@Query("classId") String classId);
}
