package ar.com.wolox.woloxtrainingmolina;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class ParseAPIHelper {

    RequestInterceptor requestInterceptor = new RequestInterceptor() {
        @Override
        public void intercept(RequestFacade request) {
            request.addHeader(Config.PARSE_APP_HEADER, Config.PARSE_APP_ID);
            request.addHeader(Config.PARSE_REST_HEADER, Config.PARSE_REST_KEY);
        }
    };

    public RestAdapter getRestAdapter() {

        return new RestAdapter.Builder()
                .setEndpoint(Config.PARSE_API_URL)
                .setRequestInterceptor(requestInterceptor)
                .build();

    }

}
