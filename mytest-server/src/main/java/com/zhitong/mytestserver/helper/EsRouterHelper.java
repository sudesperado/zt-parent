package com.zhitong.mytestserver.helper;

/**
 * es 路由
 */
public class EsRouterHelper {

    /**
     * 生成领域的唯一index名称
     *
     * @param index
     * @param tenantId
     * @return
     */
    public static String generateIndex(String index, Long tenantId) {
        return String.format("tax_modal_%s_%s", index.toLowerCase(), tenantId);
    }

    /**
     * 生成index 别名
     *
     * @param domainId 领域模型ID
     * @return
     */
    public static String generateAlias(Long domainId) {
        return String.format("%s_%s", "model", domainId);
    }


}
