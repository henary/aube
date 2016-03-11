package com.aube.untrans.monitor;

public interface ConfigCenter {
	/**
	 * 注册单系统配置
	 * @param systemid
	 * @param tag
	 * @param trigger
	 */
	void register(String systemid, String tag, ConfigTrigger trigger);
	/**
	 * 注册全局配置
	 * @param tag
	 * @param trigger
	 */
	void registerGlobal(String tag, ConfigTrigger trigger);

	/**
	 * 注册单系统配置（多分区，防止单节点过快更新导致信息丢失）
	 * @param systemid
	 * @param tag
	 * @param partition  最大16个
	 * @param trigger
	 */
	void register(String systemid, String tag, ConfigTrigger trigger, int partition);
	/**
	 * 注册全局配置（多分区，防止单节点过快更新导致信息丢失）
	 * @param tag
	 * @param trigger
	 * @param partition
	 */
	void registerGlobal(String tag, ConfigTrigger trigger, int partition);
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	void refresh(String systemid, String tag);
	void refresh(String systemid, String tag, String data);
	void refreshGlobal(String tag);
	void refreshGlobal(String tag, String data);

	void reloadGlobal(String tag);
	void reloadCurrent(String systemid, String tag);
	ConfigData getConfigData(String systemid, String tag);


}
