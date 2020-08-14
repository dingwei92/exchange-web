package com.topsci.qh.webmanagement.Resources.Interface;


import com.topsci.qh.webmanagement.Resources.PageInfo;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lzw.
 * 16-7-26
 */
public interface IBasicController {

    ModelAndView list(HttpServletRequest request,PageInfo pageInfo);
    ModelAndView edit(HttpServletRequest request);
}
