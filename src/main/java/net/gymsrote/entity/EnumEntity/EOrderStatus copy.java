package net.gymsrote.entity.EnumEntity;

public enum EOrderStatus {// expiration time for each status
	WAIT_FOR_PAYMENT, // can cancel
	WAIT_FOR_CONFIRM, // can cancel - can refund
	WAIT_FOR_SEND, // can cancel - can refund
	DELIVERING, DELIVERED, // review order to complete order
	COMPLETED, CANCELLED,
}
