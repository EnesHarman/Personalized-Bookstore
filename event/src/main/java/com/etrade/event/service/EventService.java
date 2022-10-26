package com.etrade.event.service;

import com.etrade.event.core.result.Result;
import com.etrade.event.dto.*;

import javax.servlet.http.HttpServletRequest;

public interface EventService {

    Result sendWlMessageClickEvent(HttpServletRequest request, WlMessageClickEvent wlMessageClickEvent);

    Result productLinkClickEvent(HttpServletRequest request, ProductLinkClickEvent productLinkClickEvent);

    Result productClickEvent(HttpServletRequest request, ProductClickEvent productClickEvent);

    Result messageClickEvent(HttpServletRequest request, MessageClickEvent messageClickEvent);

    Result loginEvent(HttpServletRequest request);
}
