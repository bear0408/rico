package com.rico.comm.util;

import com.rico.comm.constant.MateConstant;
import org.slf4j.MDC;


import javax.servlet.http.HttpServletRequest;

/**
 * 链路追踪工具类
 *
 * @author rico
 */
public class TraceUtil {

    /**
     * 从header和参数中获取traceId
     * 从前端传入数据
     *
     * @param request　HttpServletRequest
     * @return traceId
     */
    public static String getTraceId(HttpServletRequest request) {
        String traceId = request.getParameter(MateConstant.MATE_TRACE_ID);
        if (StringUtil.isBlank(traceId)) {
            traceId = request.getHeader(MateConstant.MATE_TRACE_ID);
        }
        return traceId;
    }

    /**
     * 传递traceId至MDC
     * @param traceId　跟踪ID
     */
    public static void mdcTraceId (String traceId) {
        if (StringUtil.isNotBlank(traceId)) {
            MDC.put(MateConstant.LOG_TRACE_ID, traceId);
        }
    }
}
