package com.farst.common.utils;

import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64; 
import com.farst.common.exception.ServiceException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

public class JwtUtils {

	private static String secret = "moveup";
	private static long expire;
	private static String header;

	/**
	 * 由字符串生成加密key
	 * 
	 * @param secert
	 * @return
	 */
	public static SecretKey generalKey() {
		byte[] encodedKey = Base64.decodeBase64(secret);
		SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
		return key;
	}

	/**
	 * 解密token
	 * 
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public static Claims validateToken(String token) throws Exception {
		Claims claims = Jwts.parser().setSigningKey(generalKey()).parseClaimsJws(token).getBody();
		return claims;
	}

	/**
	 * 使用JWT创建token
	 * 
	 * @param id
	 *            存储id
	 * @param subject
	 *            存储内容
	 * @param ttlMillis
	 *            超时时间
	 * @return
	 * @throws Exception
	 */
	public static String createJWT(String id, String subject, long ttlMillis) {
		// 从枚举类获取HS256加密我们的key
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		// 当前时间
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		SecretKey secretKey = generalKey();
		JwtBuilder builder = Jwts.builder()
				.setHeaderParam("typ", "JWT")
				.setId(id)
				.setSubject(subject)
				.setIssuedAt(now)
				.signWith(signatureAlgorithm, secretKey);
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date expDate = new Date(expMillis);
			builder.setExpiration(expDate);
		}
		return builder.compact();
	}

	/**
	 * 
	 * 解析JWT字符串
	 * 
	 * @param jwt
	 * @param secert
	 * @return
	 * @throws Exception
	 */
	public static Claims parseJWT(String jwt) throws Exception {
		SecretKey secretKey = generalKey();
		Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
		return claims;
	}
	

	/**
	 * 验证JWT
	 * @param token
	 * @return
	 */
	public static Claims validateJWT(String token) throws ServiceException{		
		Claims claims = null;
		try {
			claims = parseJWT(token);
		} catch (ExpiredJwtException e) {
			throw new ServiceException("Token已经过期",e);			
		} catch (MalformedJwtException e) {
			throw new ServiceException("改造签名，或者无效的Token",e);
		} catch (SignatureException e) {
			throw new ServiceException("私钥算法与签名算法不兼容",e);
		} catch (Exception e) {
			throw new ServiceException("请重新登录",e);
		}		
		return claims;
	}

	public static void main(String[] args) {		
		String accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIwMDAxIiwic3ViIjoi5byg5LiJIiwiaWF0IjoxNTQ0MTA1OTgwLCJleHAiOjE1NDQxMDY5ODB9.V96KID25JIYS-QcuuTCKxXK1f1kMFu-3Bc6Bheqs47g";
		//accessToken = jwtUtils.createJWT("0001","张三",1000000);
		System.out.println("create JWT token is:"+accessToken );		
		Claims claims = null;
		try{
			claims = JwtUtils.validateJWT(accessToken);
		} catch (Exception e) {			
			System.out.println(e.getMessage() + ",请重新登陆！");
			return;
		}
		if (claims==null) {
			System.out.println("请重新登陆！");
			return;
		}
		// 剩余过期秒数
		int validMillis = (int) (claims.getExpiration().getTime() - System.currentTimeMillis());
		if (validMillis > 0) {
			// 交給容器管理，可以存放redis，或 cookie
			// Cookie cookie = new Cookie(MD5Util.MD5Encode("MD5SING",
			// "UTF-8").toUpperCase(), accessToken);
			// cookie.setMaxAge(validMillis/1000);
		}
		long expires = (claims.getExpiration().getTime() - System.currentTimeMillis());
		JwtUtils.setExpire(expires/1000);
		System.out.println("expire:" +claims.getExpiration()+" 剩余"+ expires/1000);
		String json = claims.getSubject();
		System.out.println("parse JWT token is:" + json);
	}

	/**
	 * token是否过期
	 * 
	 * @return true：过期
	 */
	public static boolean isTokenExpired(Date expiration) {
		return expiration.before(new Date());
	}

	public static String getSecret() {
		return secret;
	}

	public static void setSecret(String sec) {
		secret = sec;
	}

	public static long getExpire() {
		return expire;
	}

	public static void setExpire(long exp) {
		expire = exp;
	}

	public static String getHeader() {
		return header;
	}

	public static void setHeader(String hea) {
		header = hea;
	}

}
