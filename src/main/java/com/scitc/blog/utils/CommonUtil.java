package com.scitc.blog.utils;

import com.scitc.blog.model.UserInfo;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

public class CommonUtil {
    public static String getStingTags(String[] tags) {
        String result = "";
        for (int i = 0; i < tags.length; i++) {
            if (i == tags.length - 1) {
                result += tags[i];
            } else {
                result += tags[i] + ",";
            }
        }
        return result;
    }

    public static String getContextPath(HttpServletRequest request) {
        return request.getContextPath();
    }

    /**
     * @param pageIndex 页码
     * @param pageSize  每页记录数
     * @return
     */
    public static int getRowIndex(int pageIndex, int pageSize) {
        return (pageIndex > 0) ? (pageIndex - 1) * pageSize : 0;
    }


    //生成随机数字和字母,
    public static String getStringRandom(int length) {
        String val = "";
        Random random = new Random();
        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }


    public static UserInfo getUserInfo(HttpServletRequest request) {
        String token = request.getHeader("accessToken");
        String StringUserInfo = JwtUtils.getUserInfo(token);

        JSONObject jsonObject = JSONObject.fromObject(StringUserInfo);
        UserInfo getUser = (UserInfo) JSONObject.toBean(jsonObject, UserInfo.class);
        return getUser;
    }

}
