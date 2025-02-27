package com.fingerchar.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fingerchar.base.service.IBaseService;
import com.fingerchar.domain.FcPayToken;
import com.fingerchar.utils.ResponseUtil;

@Service
public class FcPayTokenService {

	@Autowired
	private IBaseService baseService;

	public List<FcPayToken> findAll() {
		QueryWrapper<FcPayToken> wrapper = new QueryWrapper<>();
		return this.baseService.findByCondition(FcPayToken.class, wrapper);
	}

	// 封装address和汇率
	public Map<String, BigDecimal> selectAddressAndRate() {
		Map<String, BigDecimal> addressRateMap = new ConcurrentHashMap<>();
		List<FcPayToken> all = this.findAll();
		for (FcPayToken payToken : all) {
			addressRateMap.put(payToken.getAddress().toLowerCase(), payToken.getRate());
		}
		return addressRateMap;
	}

	/**
	 * @param address
	 * @return
	 */
	public Object disable(String address) {
		UpdateWrapper<FcPayToken> wrapper = new UpdateWrapper<>();
		wrapper.eq(FcPayToken.ADDRESS, address);
		wrapper.set(FcPayToken.DELETED, true);
		this.baseService.updateByCondition(FcPayToken.class, wrapper);
		return ResponseUtil.ok();
	}

	/**
	 * @param address
	 * @return
	 */
	public Object enable(String address) {
		UpdateWrapper<FcPayToken> wrapper = new UpdateWrapper<>();
		wrapper.eq(FcPayToken.ADDRESS, address);
		wrapper.set(FcPayToken.DELETED, false);
		this.baseService.updateByCondition(FcPayToken.class, wrapper);
		return ResponseUtil.ok();
	}

	/**
	 * @param payToken
	 * @return
	 */
	public Object saveOrUpdate(FcPayToken payToken) {
		this.baseService.saveOrUpdate(payToken);
		return ResponseUtil.ok();
	}
}
