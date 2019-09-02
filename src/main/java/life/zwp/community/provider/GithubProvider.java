package life.zwp.community.provider;

import com.alibaba.fastjson.JSON;
import life.zwp.community.dto.AccessTokenDTO;
import life.zwp.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * github 第三方登录
 */
@Component
public class GithubProvider {

    /**
     * 获取accessToken
     * @param accessTokenDTO
     * @return
     */
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        String accessToken = null;
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
        String url ="https://github.com/login/oauth/access_token";
        Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
//                System.out.println("string:=========="+accessToken);
                String[] split = (string.split("&")[0]).split("=");
                accessToken = split[1];
//                System.out.println("accessToken:=========="+accessToken);
            } catch (IOException e) {
                e.printStackTrace();
            }
        return accessToken;
    }

    /**
     * 根据accessToken获取用户信息
     * @param accessToken
     * @return
     */
    public GithubUser getUserInfo(String accessToken){
        GithubUser githubUser = null;
        OkHttpClient client = new OkHttpClient();
        String url ="https://api.github.com/user?access_token="+accessToken;
        Request request = new Request.Builder()
                    .url(url)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
                githubUser = JSON.parseObject(string, GithubUser.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        return githubUser;
    }
}
