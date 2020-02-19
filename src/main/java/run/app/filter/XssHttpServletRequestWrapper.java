package run.app.filter;

import org.apache.commons.lang3.StringUtils;
import run.app.util.XssFilterUtil;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2020 2020/1/19 13:13
 * Description: xss过滤器
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    HttpServletRequest orgRequest = null;

    private boolean isIncludeRichText = false;

    public XssHttpServletRequestWrapper(HttpServletRequest request, boolean isIncludeRichText) {
        super(request);
        orgRequest = request;
        this.isIncludeRichText = isIncludeRichText;
    }

    /**
     * 覆盖getParameter方法，将参数名和参数值都做xss过滤.
     * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取
     * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
     */
    @Override
    public String getParameter(String name) {
        if (("content".equals(name) || name.endsWith("WithHtml")) && !isIncludeRichText) {
            return super.getParameter(name);
        }
        name = XssFilterUtil.clean(name);
        String value = super.getParameter(name);
        if (StringUtils.isNotBlank(value)) {
            value = XssFilterUtil.clean(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] arr = super.getParameterValues(name);
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                arr[i] = XssFilterUtil.clean(arr[i]);
            }
        }
        return arr;
    }


    /**
     * 覆盖getHeader方法，将参数名和参数值都做xss过滤。<br/>
     * 如果需要获得原始的值，则通过super.getHeaders(name)来获取<br/>
     * getHeaderNames 也可能需要覆盖
     */
    @Override
    public String getHeader(String name) {
        name = XssFilterUtil.clean(name);
        String value = super.getHeader(name);
        if (StringUtils.isNotBlank(value)) {
            value = XssFilterUtil.clean(value);
        }
        return value;
    }

    /**
     * 获取最原始的request
     *
     * @return
     */
    public HttpServletRequest getOrgRequest() {
        return orgRequest;
    }

    /**
     * 获取最原始的request的静态方法
     *
     * @return
     */
    public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
        if (req instanceof XssHttpServletRequestWrapper) {
            return ((XssHttpServletRequestWrapper) req).getOrgRequest();
        }
        return req;
    }

    /**
    * 功能描述: 重写经过@RequestBody的方法
    * @Author: WHOAMI
    * @Date: 2020/1/19 13:44
     */
//    @Override
//    public ServletInputStream getInputStream() throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(orgRequest.getInputStream()));
//        String line = br.readLine();
//        String result = "";
//        if (line != null) {
//          //对参数进行处理
//        }
//
//         return new WrappedServletInputStream(new ByteArrayInputStream(result.getBytes()));
//    }


}
