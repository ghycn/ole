package com.etiansoft.ole.index;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.etiansoft.ole.util.ExceptionUtil;

@Controller
public class GlobalController {

	@RequestMapping(value = "/script/language.js", method = RequestMethod.GET)
	public String language(ModelMap model) throws IOException {
		Properties pro = new Properties();
		InputStream fis = GlobalController.class.getClassLoader().getResourceAsStream("../languages/messages.properties");
		pro.load(fis);
		model.put("entrySet", pro.entrySet());
		return "/language";
	}

	@RequestMapping(value = "/500", method = RequestMethod.GET)
	public String common_500(HttpServletRequest request, ModelMap model) {
		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		if (throwable != null) {
			model.put("message", throwable.getMessage());
			model.put("stackTrace", ExceptionUtil.toString(throwable));
		}
		return "/common/500";
	}

	@RequestMapping(value = "/404", method = RequestMethod.GET)
	public String common_404() {
		return "/common/404";
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String common_403() {
		return "/common/403";
	}
}