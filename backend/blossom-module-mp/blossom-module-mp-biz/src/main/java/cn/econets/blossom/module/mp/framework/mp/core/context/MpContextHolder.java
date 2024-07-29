/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package cn.econets.blossom.module.mp.framework.mp.core.context;

import cn.econets.blossom.module.mp.controller.admin.open.vo.MpOpenHandleMessageReqVO;
import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.experimental.UtilityClass;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;

/**
 * WeChat context Context
 *
 * Purpose：Solve the problem of multiple WeChat public accounts，In {@link WxMpMessageHandler} In the implementation class，Can be passed {@link #getAppId()} Get the current appId
 *
 * @see cn.econets.blossom.module.mp.controller.admin.open.MpOpenController#handleMessage(String, String, MpOpenHandleMessageReqVO)
 *
 *
 */
public class MpContextHolder {

    /**
     * WeChat public account appId Context
     */
	private static final ThreadLocal<String> APPID = new TransmittableThreadLocal<>();

	public static void setAppId(String appId) {
		APPID.set(appId);
	}

	public static String getAppId() {
		return APPID.get();
	}

	public static void clear() {
		APPID.remove();
	}

}
