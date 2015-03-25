package ar.com.wolox.woloxtrainingmolina.api;

import ar.com.wolox.woloxtrainingmolina.Config;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class ParseAPIHelper {

    private String mSessionToken;

    RequestInterceptor requestInterceptor = new RequestInterceptor() {
        @Override
        public void intercept(RequestFacade request) {
            request.addHeader(Config.PARSE_APP_HEADER, Config.PARSE_APP_ID);
            request.addHeader(Config.PARSE_REST_HEADER, Config.PARSE_REST_KEY);
            if (mSessionToken != null) request.addHeader(Config.PARSE_TOKEN_HEADER, mSessionToken);
        }
    };

    ParseAPIHelper(String sessionToken) {
        this.mSessionToken = sessionToken;
    }

    public RestAdapter getRestAdapter() {

        return new RestAdapter.Builder()
                .setEndpoint(Config.PARSE_API_URL)
                .setRequestInterceptor(requestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .build();
    }

}
