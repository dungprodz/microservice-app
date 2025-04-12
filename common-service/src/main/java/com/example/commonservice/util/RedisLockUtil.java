package com.example.commonservice.util;

import com.example.commonservice.common.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
public class RedisLockUtil {
	private final RedissonClient redissonClient;

	protected <T> T lockTransactionForUpdate(String transactionId, Function<RLock, T> process) {
		Assert.notNull(transactionId, "'transactionId' cannot be null");
		Assert.notNull(process, "'process' cannot be null");

		RLock lock;
		try {
			lock = redissonClient.getFairLock(String.format("%s:%s", "TRANSACTION_LOCK_KEY_PREFIX", transactionId));
			if (!lock.tryLock(1, 1200, TimeUnit.SECONDS)) {
				throw CommonException.badRequest("TRANSACTION_LOCK");
			}
		} catch (InterruptedException e) {
			log.error("Something when wrong with lock", e);
			throw CommonException.badRequest("TRANSACTION_LOCK_ERROR");
		}

		try {
			return process.apply(lock);
		} finally {
			lock.unlock();
		}
	}
}
