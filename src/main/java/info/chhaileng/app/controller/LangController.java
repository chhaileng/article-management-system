package info.chhaileng.app.controller;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class LangController extends WebMvcConfigurerAdapter {
	@Bean
	public LocaleResolver localeResolver(){
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.US);
		return slr;
	}
	@Bean
	public LocaleChangeInterceptor localChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localChangeInterceptor());
	}

//	@RequestMapping(value = { "/home/set" }, method = RequestMethod.GET)
//	public String language(@RequestParam("lang") String lang) {
//		System.out.println("Langeuage set to : " + lang);
//		// Save cookie to browser
////		Cookie language = new Cookie("lang", lang);
////		int expMinute = 30 * 60; // minutes * seconds
////		language.setMaxAge(expMinute); 
//		return "redirect:/";
//	}
	
}