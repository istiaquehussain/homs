package com.coe.homs.service;

import java.util.Date;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.coe.homs.serviceapi.TokenService;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.compression.GzipCompressionCodec;

@Component("JwtTokenService")
public class JwtTokenService implements Clock, TokenService {
	@Value("${app.security.jwt.secret:defultsecret}")
	String secretKey;
	@Value("${app.security.jwt.issuer:defultissuer}")
	String issuer;
	@Value("${app.security.jwt.expiration-sec:300}")
	int expirationSec;
	@Value("${app.security.jwt.clock-skew-sec:300}")
	int clockSkewSec;

	@Override
	public String toString() {
		return "JWTokenService [secretKey=" + secretKey + ", issuer=" + issuer + ", expirationSec=" + expirationSec
				+ ", clockSkewSec=" + clockSkewSec + "]";
	}

	@Override
	public String genrateParmanentToken(Map<String, String> attributes) {
		return genrateToken(attributes, -1);
	}

	@Override
	public String genrateExpiryToken(Map<String, String> attributes) {

		// System.out.println("@@@@@@@@ JWTokenService.genrateExpiryToken intitilize ->
		// "+toString());
		return genrateToken(attributes, expirationSec);
	}

	@Override
	public Map<String, String> varifyToken(String token) {
		final JwtParser parser = Jwts.parser().requireIssuer(issuer).setClock(this)
				.setAllowedClockSkewSeconds(clockSkewSec).setSigningKey(secretKey);
		return parseClaims(() -> parser.parseClaimsJws(token).getBody());
	}

	@Override
	public void invalidateToken(String token) {
		// TODO Auto-generated method stub

	}

	private String genrateToken(Map<String, String> attributes, int expiresInSec) {
		System.out.println("Ingenerated Token "+toString());
		Claims claims = getClaim(expiresInSec);
		claims.putAll(attributes);

		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, secretKey)
				.compressWith(new GzipCompressionCodec()).compact();
	}

	private Claims getClaim(int expiresInSec) {
		if (expiresInSec == -1)
			return Jwts.claims().setIssuer(issuer).setIssuedAt(DateTime.now().toDate());
		else {
			return Jwts.claims().setIssuer(issuer).setIssuedAt(DateTime.now().toDate())
					.setExpiration(DateTime.now().plus(expiresInSec).toDate());

		}
	}

	private static Map<String, String> parseClaims(final Supplier<Claims> toClaims) {
		try {
			final Claims claims = toClaims.get();
			final ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
			for (final Map.Entry<String, Object> e : claims.entrySet()) {
				builder.put(e.getKey(), String.valueOf(e.getValue()));
			}
			return builder.build();
		} catch (final IllegalArgumentException | JwtException e) {
			return ImmutableMap.of();
		}
	}

	@Override
	public Date now() {
		return DateTime.now().toDate();
	}

}
