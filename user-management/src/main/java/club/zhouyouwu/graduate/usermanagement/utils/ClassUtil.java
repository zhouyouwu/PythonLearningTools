package club.zhouyouwu.graduate.usermanagement.utils;

import club.zhouyouwu.graduate.usermanagement.model.entity.User;
import club.zhouyouwu.graduate.usermanagement.model.params.UserInfoParam;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ClassUtil {

    /**
     * 将a中与b相同的属性赋给b
     * @param a
     * @param b
     */
    public static void convAtoB(Object a, Object b) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Field[] fields = a.getClass().getDeclaredFields();
        List<String> names = Arrays.stream(fields).map(o->
                o.getName().substring(0,1).toUpperCase()+o.getName().substring(1)).collect(Collectors.toList());

        for (String name : names) {
            Method getA = a.getClass().getMethod("get" + name);
            Method setB = b.getClass().getMethod("set" + name);

            Object value = getA.invoke(a);
            setB.invoke(b, value);
        }
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        UserInfoParam param = new UserInfoParam();
        param.setPhoneNo("123456789");
        param.setNickname("xxx");

        User user = new User();
        convAtoB(param, user);
        System.out.println(user);
    }
}
