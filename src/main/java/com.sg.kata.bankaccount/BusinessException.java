package org.account.manager.business.execption;

import lombok.Getter;

public class BusinessException extends RuntimeException {
	@Getter
	private BusinessErrorCode errorCode;

	public BusinessException(BusinessErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}
}
