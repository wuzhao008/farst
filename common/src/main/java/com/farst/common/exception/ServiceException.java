/**
 * Project: sp-common
 * 
 * File Created at 2018-10-11
 * $Id$
 * 
 * Copyright 2018 uup.com Croporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * uup Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with uup.com.
 */
package com.farst.common.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务异常
 * 
 * @author MichaelWoo
 * @version $Id: ServiceException.java 2018-10-18 03:47:32 $
 */
public class ServiceException extends Exception {

	/**
     * 
     */
	private static final long serialVersionUID = 6026433002411902408L;
	public static final String KEY_MSG_PARAMS = "MSGPARAMS";

	private String errorCode = "10000";
	private String errorMessage = null;

	private Map errorParams = null;
	private Throwable clauses = null;

	public ServiceException(String errorCode, String errorMessage,
			Throwable clauses, Map errorParams) {
		super(errorMessage, (clauses != null) ? clauses : null);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.clauses = clauses;
		this.errorParams = errorParams;
	}

	@SuppressWarnings("unchecked")
	public ServiceException(Throwable clauses, String errorCode,
			String errorMessage, Object[] msgParams) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.clauses = clauses;
		if (errorParams == null) {
			errorParams = new HashMap();
		}
		errorParams.put(KEY_MSG_PARAMS, msgParams);
	}

	public ServiceException(String errorCode, String errorMessage,
			Throwable clauses) {
		super(errorMessage, (clauses != null) ? clauses : null);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.clauses = clauses;
	}

	public ServiceException(String errorMessage, Throwable clauses) {
		//this(ErrorCode.SYSTEM_ERROR, errorMessage, clauses, null);
		this(ErrorCode.SYSTEM_ERROR, errorMessage +clauses.getMessage(), clauses, null);
	}

	public ServiceException(String errorCode, String errorMessage) {
		this(errorCode, errorMessage, null, null);
	}

	public ServiceException(String errorCode, Object[] msgParams) {
		this(null, errorCode, null, msgParams);
	}

	public ServiceException(String errorCode, Map errorParams,
			String errorMessage) {
		this(errorCode, errorMessage, null, errorParams);
	}

	public ServiceException(String errorMessage) {
		this(ErrorCode.SYSTEM_ERROR, errorMessage, null, null);
	}

	public ServiceException(Throwable clauses) {
		this(ErrorCode.SYSTEM_ERROR, null, clauses, null);
	}

	public Map getErrorParams() {
		return errorParams;
	}

	public void setErrorParams(Map errorParams) {
		this.errorParams = errorParams;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode
	 *            the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the clauses
	 */
	public Throwable getClauses() {
		return clauses;
	}

	/**
	 * @param clauses
	 *            the clauses to set
	 */
	public void setClauses(Throwable clauses) {
		this.clauses = clauses;
	}
}
