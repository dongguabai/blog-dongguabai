package blog.dongguabai.dongguabai.security.core;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
public class X5RequestSecret {

    /**
     * app id
     */
    @EqualsAndHashCode.Include
    String appId;

    /**
     * appId验签使用的的key
     */
    String appKey;

    /**
     * appId 允许的方法
     */
    Set<String> allowMethods = new HashSet();

}
