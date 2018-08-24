package com.coe.homs.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.coe.homs.security.CustomAuthenticationProvider;
import com.coe.homs.security.CustomRESTRedirectStrategy;
import com.coe.homs.security.CustomTokenAuthenticationFilter;
import com.coe.homs.serviceapi.ConfigurationService;

@Configuration
@EnableWebSecurity
public class TokenSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final RequestMatcher PUBLIC_URLS = new OrRequestMatcher(new AntPathRequestMatcher("/public/**"));
	private static final RequestMatcher PROTECTED_URLS = new NegatedRequestMatcher(PUBLIC_URLS);

	@Autowired
	@Qualifier("CustomAuthenticationProvider")
	CustomAuthenticationProvider customAuthenticationProvider;
	@Autowired
	@Qualifier("${app.service.ConfigurationService}")
	ConfigurationService configurationService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//System.out.println("@@@@ configuring Token security");
		auth.authenticationProvider(customAuthenticationProvider);
	}

	@Override
	public void configure(final WebSecurity web) {
		web.ignoring().requestMatchers(PUBLIC_URLS);
	}

	@Bean
	FilterRegistrationBean disableAutoRegistration(final CustomTokenAuthenticationFilter filter) {
		final FilterRegistrationBean registration = new FilterRegistrationBean(filter);
		registration.setEnabled(false);
		return registration;
	}

	@Deprecated
	protected void configureWithNoAcl(HttpSecurity http) throws Exception {

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling()
				// this entry point handles when you request a protected page and you are not
				// yet
				// authenticated
				.defaultAuthenticationEntryPointFor(forbiddenEntryPoint(), PROTECTED_URLS).and()
				// .authenticationProvider(provider)
				.addFilterBefore(getCutomRestFilter(), AnonymousAuthenticationFilter.class).authorizeRequests()
				.anyRequest().authenticated().and().csrf().disable().formLogin().disable().httpBasic().disable()
				.logout().disable();

	}

	@Deprecated
	protected void configureWithHarcodedACL(HttpSecurity http) throws Exception {

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling()
				.defaultAuthenticationEntryPointFor(forbiddenEntryPoint(), PROTECTED_URLS).and()
				.addFilterBefore(getCutomRestFilter(), AnonymousAuthenticationFilter.class).authorizeRequests()
				.antMatchers("/admin/**").hasRole("ADMIN").antMatchers("/private/**").hasRole("USER").anyRequest()
				.authenticated().and().csrf().disable().formLogin().disable().httpBasic().disable().logout().disable();

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling()
				.defaultAuthenticationEntryPointFor(forbiddenEntryPoint(), PROTECTED_URLS).and()
				.addFilterBefore(getCutomRestFilter(), AnonymousAuthenticationFilter.class);

		Map<String, String> acl = configurationService.getAcl().getData();

		acl.keySet().forEach(url -> {
			try {
				http.authorizeRequests().antMatchers(url).hasRole(acl.get(url));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		http.authorizeRequests().anyRequest().authenticated();

		http.csrf().disable().formLogin().disable().httpBasic().disable().logout().disable();

	}

	@Bean
	public CustomTokenAuthenticationFilter getCutomRestFilter() throws Exception {
		CustomTokenAuthenticationFilter customFilter = new CustomTokenAuthenticationFilter(PROTECTED_URLS);
		customFilter.setAuthenticationManager(authenticationManager());
		customFilter.setAuthenticationSuccessHandler(successHandler());
		return customFilter;
		// return new COECustomAuthenticationFilter();
	}

	@Bean
	AuthenticationEntryPoint forbiddenEntryPoint() {
		return new HttpStatusEntryPoint(HttpStatus.FORBIDDEN);
	}

	@Bean
	SimpleUrlAuthenticationSuccessHandler successHandler() {
		final SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();
		successHandler.setRedirectStrategy(new CustomRESTRedirectStrategy());
		return successHandler;
	}

}
