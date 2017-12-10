package com.minyou.manba.network.resultModel;

/**
 * Created by luchunhao on 2017/12/10.
 */
public class UserLoginModel extends BaseResultModel{

    /**
     * userId : 3
     * token : eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxODIxMDEzNTY5MSIsInNjb3BlcyI6WyJST0xFX1BSRU1JVU1fTUVNQkVSIl0sImlzcyI6Imh0dHA6Ly93d3cubXltYW5iYS5jbiIsImlhdCI6MTUxMjkwMDgzNSwiZXhwIjoxNTEyOTg3MjM1fQ.VW7eBV0PMbgQ3hMkCtBwUYRuCf-GC2m8ZsS_xFzF51GumFzTW89ZS6gIVMw0Dyc6C6p8ine5KpouEZTIT-hwPQ
     * refreshToken : eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxODIxMDEzNTY5MSIsInNjb3BlcyI6WyJST0xFX1JFRlJFU0hfVE9LRU4iXSwiaXNzIjoiaHR0cDovL3d3dy5teW1hbmJhLmNuIiwianRpIjoiZTJhNTVmYTctODNkNC00ZDBmLWEyNTktYTA4MWRlMjQ0NmRkIiwiaWF0IjoxNTEyOTAwODM1LCJleHAiOjE1MTI5MDQ0MzV9.ImGBVREEd7FYtEsInQOaeLpyR_88KWZP-H7DqjinFtVlzmbA3NmzudiRvyMwF4FLtiBp8meMOAJcs1eso8TnQg
     */

    private String userId;
    private String token;
    private String refreshToken;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}

